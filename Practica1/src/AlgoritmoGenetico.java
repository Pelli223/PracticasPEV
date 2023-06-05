import java.util.Random;

public class AlgoritmoGenetico <T>{
	private int tamPoblacion;
	private Individuo<T>[] poblacion, elite;
	private double[] fitness;
	private double probCruce;
	private double probMutacion;
	private double maxMin;
	private Individuo<T> elMejor;
	private int pos_mejor;
	private Seleccion sel;
	private Cruce<T> cruc;
	private double aptitudMedia;
	private boolean min;
	private int typeSel;
	private int ind;
	private double valErr;
	private int d;
	double elit;
	
	public AlgoritmoGenetico(int tamPob, int ind, double valErr, int d, int maxGen, double probCruce, double probMutacion, int sel, int cruc, boolean min, double elit, boolean func5) {
		double mejor;
		this.min = min;
		this.probCruce = probCruce;
		this.probMutacion = probMutacion;
		this.tamPoblacion = tamPob;
		this.fitness = new double[this.tamPoblacion];
		this.typeSel = sel;
		this.ind = ind;
		this.valErr = valErr;
		this.d = d;
		this.elit = elit;
		this.initPob();;
		this.cruc = (Cruce<T>) CruceFactory.getCruceType(cruc, func5, this.poblacion[0].getTamCromosoma());
		mejor = this.poblacion[0].getFitness();
		this.fitness[0] = this.poblacion[0].getFitness();
		for(int i = 1; i < this.tamPoblacion; i++) {
			this.fitness[i] = this.poblacion[i].getFitness();
			if(min) {
				if(mejor > this.fitness[i])
					mejor = this.fitness[i];
			}
			else
				if(mejor < this.fitness[i])
					mejor = this.fitness[i];
		}
			
	}
	
	public void run() {
		double x;
		int sel1 = 0, sel2 = 0;
		boolean par = false;
		Random rand = new Random();
		int numElit = 0;;
		int[] posElit = null;
		if(elit > 0.0) {
			numElit = (int) (this.tamPoblacion * this.elit);
			posElit = this.selElite(numElit);
		}
		this.sel = SeleccionFactory.getSeleccioAlg(this.typeSel, this.fitness, this.tamPoblacion, this.min, this.maxMin);
		int[] seleccion = this.sel.getSeleccion();
		Individuo<T>[] nuevaPob = new Individuo[this.tamPoblacion];
		for(int i = 0; i < this.tamPoblacion; i++) {
			nuevaPob[i] = (Individuo<T>) IndividuoFactory.getIndivType(this.ind, this.valErr, this.d);
			nuevaPob[i].setCromosoma(this.poblacion[seleccion[i]].getCromosoma());
		}
		this.poblacion = nuevaPob;
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
			this.poblacion[i].mutacion(this.probMutacion);
			//System.out.println(this.poblacion[i].getFitness());
		}
		if(elit > 0.0) {
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
	
	public double getFenotipoMejor(int n) {
		return this.elMejor.getFenotipo(n);
	}
	
	public double getMedia() {
		return this.aptitudMedia;
	}
	
	public void analizaGeneracion() {
		this.pos_mejor = 0;
		double sumFitness = 0; 
		this.maxMin = this.poblacion[0].getFitness();
		for(int i = 0; i < this.tamPoblacion; i++) {
			if(this.min) {
				if(this.elMejor.getFitness() > this.poblacion[i].getFitness()) {
					this.pos_mejor = i;
					this.elMejor.setCromosoma(this.poblacion[i].getCromosoma());
				}
				else if(this.poblacion[this.pos_mejor].getFitness() > this.poblacion[i].getFitness()) {
					this.pos_mejor = i;
				}
				if(this.poblacion[i].getFitness() > this.maxMin)
					this.maxMin = this.poblacion[i].getFitness();
			}
			else {
				if(this.elMejor.getFitness() < this.poblacion[i].getFitness()) {
					this.pos_mejor = i;
					this.elMejor.setCromosoma(this.poblacion[i].getCromosoma());
				}
				else if(this.poblacion[this.pos_mejor].getFitness() < this.poblacion[i].getFitness()) {
					this.pos_mejor = i;
				}
				if(this.poblacion[i].getFitness() < this.maxMin)
					this.maxMin = this.poblacion[i].getFitness();
			}
			this.fitness[i] = this.poblacion[i].getFitness();
			sumFitness += this.fitness[i];
		}
		this.aptitudMedia = sumFitness/this.tamPoblacion;
	}
	
	private void reproduccion(int pos1, int pos2) {
		T[]c1 = this.poblacion[pos1].getCromosoma();
		T[]c2 = this.poblacion[pos2].getCromosoma();
		this.cruc.cruzar(c1, c2);
		this.poblacion[pos1].setCromosoma(c1);
		this.poblacion[pos2].setCromosoma(c2);
	}
	
	private void initPob() {
		this.poblacion = new Individuo[this.tamPoblacion];
		this.elMejor = (Individuo<T>) IndividuoFactory.getIndivType(this.ind, this.valErr, this.d);
		for(int i = 0; i < this.tamPoblacion; i++) {
			this.poblacion[i] = (Individuo<T>) IndividuoFactory.getIndivType(this.ind, this.valErr, this.d);
		}
		this.elMejor.setCromosoma(this.poblacion[0].getCromosoma());
	}
	private int[] selElite(int numElit) {
		this.elite = new Individuo[numElit];
		int temp;
		int[]posElit = new int[numElit];
		for(int i = 0; i < numElit; i++) {
			this.elite[i] = (Individuo<T>) IndividuoFactory.getIndivType(this.ind, this.valErr, this.d);
			this.elite[i].setCromosoma(this.poblacion[i].getCromosoma());
			posElit[i] = i;
		}
		for(int i = numElit; i < this.tamPoblacion; i++) {
			temp = 0;
			for(int j = 1; j < numElit; j++) {
				if(!this.min) {
					if(this.elite[temp].getFitness() < this.elite[j].getFitness())
						temp = j;
				}
				else {
					if(this.elite[temp].getFitness() > this.elite[j].getFitness())
						temp = j;
				}
			}
			if(!this.min){
				if (this.elite[temp].getFitness() < this.poblacion[i].getFitness()) {
					this.elite[temp].setCromosoma(this.poblacion[i].getCromosoma());
					posElit[temp] = i;
				}
			}
			else {
				if(this.elite[temp].getFitness() > this.poblacion[i].getFitness()) {
					this.elite[temp].setCromosoma(this.poblacion[i].getCromosoma());
					posElit[temp] = i;
				}
			}
		}
		return posElit;
	}
}
