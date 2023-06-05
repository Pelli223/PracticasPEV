import java.util.ArrayList;
import java.util.Random;
import Cruce.CruceFactory;
import Cruce.Cruce;
import Individuo.Individuo;
import Mutaciones.MutacionBasica;
import Selecciones.Seleccion;
import Selecciones.SeleccionFactory;

public class AlgoritmoGenetico{
	private int tamPoblacion;
	private Individuo[] poblacion, elite;
	private double[] fitness;
	private double[] fitnessEsc;
	private double probCruce;
	private double maxMin;
	private int maxWraps;
	private Individuo elMejor;
	private double elMejorFitness;
	private int pos_mejor;
	private Seleccion sel;
	private Cruce cruc;
	private MutacionBasica mut;
	private double aptitudMedia, presionSelectiva;
	private int typeSel;
	private double elit;
	
	public AlgoritmoGenetico(int tamPob, double probCruce, double probMutacion, int sel, int cruc, int mut, 
			double elit, int maxWraps) {
		
		this.probCruce = probCruce;
		this.tamPoblacion = tamPob;
		this.fitness = new double[this.tamPoblacion];
		this.fitnessEsc = new double[this.tamPoblacion];
		this.typeSel = sel;
		this.elit = elit;
		this.maxWraps = maxWraps;
		this.initPob();
		this.cruc = CruceFactory.getCruceType(cruc, Individuo.getTamCromosoma());
		this.mut = new MutacionBasica(Individuo.getTamCromosoma(), probMutacion);
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
			nuevaPob[i] = new Individuo(this.maxWraps, true);
			nuevaPob[i].setCromosoma(this.poblacion[seleccion[i]].getCromosoma());
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
		for(int i = 0; i < this.tamPoblacion; i++) 
				this.mut.mutacion(this.poblacion[i].getCromosoma());
		
		if(elit > 0.0 && numElit > 0) {
			for(int i = 0; i < numElit; i++) {
				this.poblacion[posElit[i]].setCromosoma((this.elite[i].getCromosoma()));
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
		return this.elMejor.decoFormula();
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
		
		for(int i = 0; i < this.tamPoblacion; i++) {
			this.fitness[i] = this.poblacion[i].getFitness();
			sumFitness += this.fitness[i];
		}
		this.aptitudMedia = sumFitness/this.tamPoblacion;
		for(int i = 0; i < this.tamPoblacion; i++) {
			if(this.elMejorFitness > this.fitness[i]) {
				this.pos_mejor = i;
				this.elMejor.setCromosoma(this.poblacion[i].getCromosoma());
				this.elMejorFitness = this.fitness[i];
			}
			else if(this.fitness[this.pos_mejor] > this.fitness[i]) {
				this.pos_mejor = i;
			}
		}
		this.presionSelectiva = this.fitness[this.pos_mejor]/this.aptitudMedia;
		this.EscaladoFitness();
	}
	
	private void reproduccion(int pos1, int pos2) {
		this.cruc.cruzar(this.poblacion[pos1].getCromosoma(), this.poblacion[pos2].getCromosoma());
	}
	
	private void initPob() {
		Individuo.generaDataSet();
		this.poblacion = new Individuo[this.tamPoblacion];
		this.elMejor = new Individuo(this.maxWraps, true);
		for(int i = 0; i < this.tamPoblacion; i++) {
			this.poblacion[i] = new Individuo(this.maxWraps);
			this.fitness[i] = this.poblacion[i].getFitness();
		}
		this.elMejorFitness = Double.MAX_VALUE;
	}
	
	private void EscaladoFitness() {
		double a, b;
		a = this.aptitudMedia/(this.aptitudMedia - this.fitness[this.pos_mejor]);
		b = (1-a)*this.aptitudMedia;
		this.maxMin = a*this.fitness[0]+b;
		for(int i = 0; i < this.tamPoblacion; i++) {
			this.fitnessEsc[i] = a*this.fitness[i]+b;
			if(this.fitnessEsc[i] > this.maxMin)
				this.maxMin = this.fitnessEsc[i];
		}
	}
	
	private int[] selElite(int numElit) {
		this.elite = new Individuo[numElit];
		int temp;
		int[]posElit = new int[numElit];
		for(int i = 0; i < numElit; i++) {
			this.elite[i] = new Individuo(this.maxWraps, true);
			this.elite[i].setCromosoma(this.poblacion[i].getCromosoma());
			posElit[i] = i;
		}
		for(int i = numElit; i < this.tamPoblacion; i++) {
			temp = 0;
			for(int j = 0; j < numElit; j++) {
				if(this.elite[temp].getFitness() < this.elite[j].getFitness())
					temp = j;
			}
			if(this.elite[temp].getFitness() > this.fitness[i]) {
				this.elite[temp].setCromosoma(this.poblacion[i].getCromosoma());
				posElit[temp] = i;
			}
		}
		return posElit;
	}
	
	public double[] getValorFunc() {
		return this.elMejor.getValorFunc();
	}
}
