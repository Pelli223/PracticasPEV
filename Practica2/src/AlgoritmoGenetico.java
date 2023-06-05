import java.util.Random;
import Cruce.Cruce;
import Cruce.CruceFactory;
import Individuos.Individuo;
import Individuos.IndividuoRecorrido;
import Mutaciones.Mutacion;
import Mutaciones.MutacionFactory;
import Seleccion.Seleccion;
import Seleccion.SeleccionFactory;

public class AlgoritmoGenetico{
	private int tamPoblacion;
	private Individuo<Integer>[] poblacion, elite;
	private int[] fitness;
	private double[] fitnessEsc;
	private double probCruce;
	private double probMutacion;
	private double maxMin;
	private Individuo<Integer> elMejor;
	private int pos_mejor;
	private Seleccion sel;
	private Cruce<Integer> cruc;
	private Mutacion<Integer> mut;
	private double aptitudMedia, presionSelectiva;
	private int typeSel;
	double elit;
	
	public AlgoritmoGenetico(int tamPob, double probCruce, double probMutacion, int sel, int cruc, int mut, double elit) {
		double mejor;
		this.probCruce = probCruce;
		this.probMutacion = probMutacion;
		this.tamPoblacion = tamPob;
		this.fitness = new int[this.tamPoblacion];
		this.fitnessEsc = new double[this.tamPoblacion];
		this.typeSel = sel;
		this.elit = elit;
		this.initPob();
		this.cruc = CruceFactory.getCruceType(cruc, 27);
		this.mut = MutacionFactory.getMutacionType(mut, this.poblacion[0].getTamCromosoma());
		mejor = this.poblacion[0].getFitness();
		this.fitness[0] = this.poblacion[0].getFitness();
		for(int i = 1; i < this.tamPoblacion; i++) {
			this.fitness[i] = this.poblacion[i].getFitness();
			if(mejor > this.fitness[i])
				mejor = this.fitness[i];
		}
			
	}
	
	public void run() {
		double x;
		int sel1 = 0, sel2 = 0;
		boolean par = false;
		Random rand = new Random();
		Integer[] cMut;
		int numElit = 0;;
		int[] posElit = null;
		if(elit > 0.0) {
			numElit = (int) (this.tamPoblacion * this.elit);
			if(numElit > 0)
				posElit = this.selElite(numElit);
		}
		this.sel = SeleccionFactory.getSeleccioAlg(this.typeSel, this.fitnessEsc, this.tamPoblacion, this.maxMin);
		int[] seleccion = this.sel.getSeleccion();
		Individuo<Integer>[] nuevaPob = new IndividuoRecorrido[this.tamPoblacion];
		for(int i = 0; i < this.tamPoblacion; i++) {
			nuevaPob[i] = new IndividuoRecorrido();
			nuevaPob[i].setCromosoma(this.poblacion[seleccion[i]].getCromosoma());
		}
		this.poblacion = nuevaPob;
		
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
				cMut = this.poblacion[i].getCromosoma();
				this.mut.mutar(cMut);
				this.poblacion[i].setCromosoma(cMut);
			}
		}
		if(elit > 0.0 && numElit > 0) {
			for(int i = 0; i < numElit; i++) {
				this.poblacion[posElit[i]].setCromosoma(this.elite[i].getCromosoma());
			}
		}
	}
	
	public double getMejorAc() {
		return this.poblacion[this.pos_mejor].getFitness();
	}
	
	public double getMejor() {
		return this.elMejor.getFitness();
	}
	
	public int getFenotipoMejor(int n) {
		return this.elMejor.getFenotipo(n);
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
			if(this.elMejor.getFitness() > this.poblacion[i].getFitness()) {
				this.pos_mejor = i;
				this.elMejor.setCromosoma(this.poblacion[i].getCromosoma());
			}
			else if(this.poblacion[this.pos_mejor].getFitness() > this.poblacion[i].getFitness()) {
				this.pos_mejor = i;
			}
			this.fitness[i] = this.poblacion[i].getFitness();
			sumFitness += this.fitness[i];
		}
		this.aptitudMedia = sumFitness/this.tamPoblacion;
		this.presionSelectiva = this.poblacion[this.pos_mejor].getFitness()/this.aptitudMedia;
		this.EscaladoFitness();
	}
	
	private void reproduccion(int pos1, int pos2) {
		Integer[]c1 = this.poblacion[pos1].getCromosoma();
		Integer[]c2 = this.poblacion[pos2].getCromosoma();
		this.cruc.cruzar(c1, c2);
		this.poblacion[pos1].setCromosoma(c1);
		this.poblacion[pos2].setCromosoma(c2);
	}
	
	private void initPob() {
		this.poblacion = new IndividuoRecorrido[this.tamPoblacion];
		this.elMejor = new IndividuoRecorrido();
		for(int i = 0; i < this.tamPoblacion; i++) {
			this.poblacion[i] = new IndividuoRecorrido();
		}
		this.elMejor.setCromosoma(this.poblacion[0].getCromosoma());
	}
	
	private void EscaladoFitness() {
		double a, b;
		a = this.aptitudMedia/(this.aptitudMedia - this.poblacion[this.pos_mejor].getFitness());
		b = (1-a)*this.aptitudMedia;
		this.maxMin = a*this.poblacion[0].getFitness()+b;
		for(int i = 0; i < this.tamPoblacion; i++) {
			this.fitnessEsc[i] = a*this.poblacion[i].getFitness()+b;
			if(this.fitnessEsc[i] > this.maxMin)
				this.maxMin = this.fitnessEsc[i];
		}
	}
	
	private int[] selElite(int numElit) {
		this.elite = new IndividuoRecorrido[numElit];
		int temp;
		int[]posElit = new int[numElit];
		for(int i = 0; i < numElit; i++) {
			this.elite[i] = new IndividuoRecorrido();
			this.elite[i].setCromosoma(this.poblacion[i].getCromosoma());
			posElit[i] = i;
		}
		for(int i = numElit; i < this.tamPoblacion; i++) {
			temp = 0;
			for(int j = 1; j < numElit; j++) {
				if(this.elite[temp].getFitness() < this.elite[j].getFitness())
					temp = j;
			}
			if(this.elite[temp].getFitness() > this.poblacion[i].getFitness()) {
				this.elite[temp].setCromosoma(this.poblacion[i].getCromosoma());
				posElit[temp] = i;
			}
		}
		return posElit;
	}
}
