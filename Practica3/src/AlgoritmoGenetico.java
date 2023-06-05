import java.util.ArrayList;
import java.util.Random;
import Cruces.Cruce;
import Individuos.Individuo;
import Mutaciones.Mutacion;
import Mutaciones.MutacionFactory;
import Selecciones.Seleccion;
import Selecciones.SeleccionFactory;

public class AlgoritmoGenetico{
	private int tamPoblacion;
	private Individuo[] poblacion, elite;
	private double[] fitness;
	private double[] fitnessEsc;
	private double[] fitnessPenal;
	private double probCruce;
	private double probMutacion;
	private double maxMin;
	private int maxProfundidad;
	private Individuo elMejor;
	private double elMejorFitness;
	private int pos_mejor;
	private Seleccion sel;
	private Cruce cruc;
	private Mutacion mut;
	private double aptitudMedia, mediaPenalizada, presionSelectiva, mediaNodos;
	private int typeSel;
	private int typeCreacion;
	private double elit;
	private boolean controlBloating;
	
	public AlgoritmoGenetico(int tamPob, double probCruce, double probMutacion, int sel, int typeCreacion, int mut, 
			double elit, int maxProfundidad, boolean controlBloating) {
		
		this.probCruce = probCruce;
		this.controlBloating = controlBloating;
		this.probMutacion = probMutacion;
		this.tamPoblacion = tamPob;
		this.fitness = new double[this.tamPoblacion];
		this.fitnessEsc = new double[this.tamPoblacion];
		this.fitnessPenal = new double[this.tamPoblacion];
		this.typeSel = sel;
		this.elit = elit;
		this.typeCreacion = typeCreacion;
		this.maxProfundidad = maxProfundidad;
		this.initPob();
		this.cruc = new Cruces.Cruce();
		this.mut = MutacionFactory.getMutacionType(mut, this.maxProfundidad);
		
	}
	
	public void run() {
		double x;
		int sel1 = 0, sel2 = 0;
		boolean par = false;
		Random rand = new Random();
		int numElit = 0;
		int[] posElit = null;
		if(elit > 0.0) {
			numElit = (int) (this.tamPoblacion * this.elit);
			if(numElit > 0)
				posElit = this.selElite(numElit);
		}
		this.sel = SeleccionFactory.getSeleccioAlg(this.typeSel, this.fitnessEsc, this.tamPoblacion, this.maxMin);
		int[] seleccion = this.sel.getSeleccion();
		Individuo[] nuevaPob = new Individuo[this.tamPoblacion];
		for(int i = 0; i < this.tamPoblacion; i++) {
			nuevaPob[i] = new Individuo();
			nuevaPob[i].setArbol(this.poblacion[seleccion[i]].getArbol());
		}
		this.poblacion = nuevaPob.clone();
		
		//Vemos si cruzamos, si no hay un individuo seleccionado, seleccionamos 1
		for(int i = 0; i < this.tamPoblacion; i++) {
			x = rand.nextDouble();
			if(x < this.probCruce && !par) {
				sel1 = i;
				par = true;
			}
			else if(x < this.probCruce && par) {
				sel2 = i;
				this.reproduccion(sel1, sel2);
				par = false;
			}
		}
		for(int i = 0; i < this.tamPoblacion; i++) {
			if(rand.nextDouble() < this.probMutacion) {
				this.mut.mutar(this.poblacion[i].getArbol());
			}
		}
		if(elit > 0.0 && numElit > 0) {
			for(int i = 0; i < numElit; i++) {
				this.poblacion[posElit[i]].setArbol((this.elite[i].getArbol()));
			}
		}
	}
	
	public double getMejorAc() {
		return this.fitness[this.pos_mejor];
	}
	
	public double getMejor() {
		return this.elMejorFitness;
	}
	
	public ArrayList<String> getFuncionMejor(){
		return this.elMejor.getArbol().toArray();
	}
	public double getMedia() {
		return this.aptitudMedia;
	}
	
	public double getPresionSelectiva() {
		return this.presionSelectiva;
	}
	
	public void analizaGeneracion() {
		this.pos_mejor = 0;
		double sumFitness = 0; 
		int sumNodos = 0;
		
		for(int i = 0; i < this.tamPoblacion; i++) {
			this.fitness[i] = this.poblacion[i].getFitness();
			sumNodos += this.poblacion[i].getArbol().getTotalNodos();
			sumFitness += this.fitness[i];
		}
		this.aptitudMedia = sumFitness/this.tamPoblacion;
		this.mediaNodos = sumNodos/this.tamPoblacion;
		
		for(int i = 0; i < this.tamPoblacion; i++) {
			if(this.elMejorFitness > this.fitness[i]) {
				this.pos_mejor = i;
				this.elMejor.setArbol(this.poblacion[i].getArbol());
				this.elMejorFitness = this.fitness[i];
			}
			else if(this.fitness[this.pos_mejor] > this.fitness[i]) {
				this.pos_mejor = i;
			}
		}
		this.presionSelectiva = this.fitness[this.pos_mejor]/this.aptitudMedia;
		if(this.controlBloating) {
			int sum = 0;
			this.penalizacionBienFundamentada();
			for(int i = 0; i < this.tamPoblacion; i++) {
				sum += this.fitnessPenal[i];
			}
			this.mediaPenalizada = sum / this.tamPoblacion;
		}
		
		else {
			this.fitnessPenal = this.fitness.clone();
			this.mediaPenalizada = this.aptitudMedia;
		}
		
		this.EscaladoFitness();
	}
	
	private void reproduccion(int pos1, int pos2) {
		this.cruc.cruzar(this.poblacion[pos1].getArbol(), this.poblacion[pos2].getArbol());
	}
	
	private void initPob() {
		Individuo.generaDataSet();
		this.poblacion = new Individuo[this.tamPoblacion];
		this.elMejor = new Individuo(this.typeCreacion, this.maxProfundidad);
		for(int i = 0; i < this.tamPoblacion; i++) {
			this.poblacion[i] = new Individuo(this.typeCreacion, this.maxProfundidad);
			this.fitness[i] = this.poblacion[i].getFitness();
		}
		this.elMejorFitness = Double.MAX_VALUE;
	}
	
	private void EscaladoFitness() {
		double a, b;
		a = this.aptitudMedia/(this.mediaPenalizada - this.fitnessPenal[this.pos_mejor]);
		b = (1-a)*this.mediaPenalizada;
		this.maxMin = a*this.fitnessPenal[0]+b;
		for(int i = 0; i < this.tamPoblacion; i++) {
			this.fitnessEsc[i] = a*this.fitnessPenal[i]+b;
			if(this.fitnessEsc[i] > this.maxMin)
				this.maxMin = this.fitnessEsc[i];
		}
	}
	
	private int[] selElite(int numElit) {
		this.elite = new Individuo[numElit];
		int temp;
		int[]posElit = new int[numElit];
		for(int i = 0; i < numElit; i++) {
			this.elite[i] = new Individuo(this.typeCreacion, this.maxProfundidad);
			this.elite[i].setArbol(this.poblacion[i].getArbol());
			posElit[i] = i;
		}
		for(int i = numElit; i < this.tamPoblacion; i++) {
			temp = 0;
			for(int j = 0; j < numElit; j++) {
				if(this.elite[temp].getFitness() < this.elite[j].getFitness())
					temp = j;
			}
			if(this.elite[temp].getFitness() > this.fitness[i]) {
				this.elite[temp].setArbol(this.poblacion[i].getArbol());
				posElit[temp] = i;
			}
		}
		return posElit;
	}
	
	private void penalizacionBienFundamentada() {
		double coVar = 0, Var = 0, k;
		int nodosInd;
		for(int i = 0; i < this.tamPoblacion; i++) {
			nodosInd = this.poblacion[i].getArbol().getTotalNodos();
			coVar += (nodosInd - this.mediaNodos)*(this.fitness[i] - this.aptitudMedia);
			Var += Math.pow((nodosInd - this.mediaNodos), 2);
		}
		coVar = coVar/this.tamPoblacion;
		Var = Var/this.tamPoblacion;
		if(Var > 0)
			k = coVar/Var;
		else
			k = 0;
		
		for(int i = 0; i < this.tamPoblacion; i++) {
			nodosInd = this.poblacion[i].getArbol().getTotalNodos();
			this.fitnessPenal[i] = this.fitness[i] + k * nodosInd;
		}
	}
	
	public double[] getValorFunc() {
		return this.elMejor.getValorFunc();
	}
}
