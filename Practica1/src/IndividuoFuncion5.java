
public class IndividuoFuncion5 extends Individuo<Double>{
	private int d;
	private int m = 10;
	
	public IndividuoFuncion5(double valorError, int d) {
		this.isMin = true;
		this.d = d;
		this.tamGenes = new int[1];
		this.min = new double[1];
		this.max = new double[1];
		this.valorError = valorError;
		this.min[0] = 0;
		this.max[0] = Math.PI;
		this.tamGenes[0] = 1;
		this.tamTotal = tamGenes[0] * this.d;
		this.cromosoma = new Double[this.d];
		for(int i = 0; i < this.d; i++) this.cromosoma[i] = this.rand.nextDouble(this.min[0], this.max[0]);}

	public int tamGen(double valorError, double min, double max) {
		return 1;
	}
	
	
	public double getFenotipo(int n) {
		
		return this.cromosoma[n];
	}
	
	public double getValor() {
		double x;
		double sum = 0;
		for(int i = 1; i <= this.d; i++) {
			x = this.getFenotipo(i-1);
			sum += Math.sin(x) * Math.pow(Math.sin(i * Math.pow(x, 2) / Math.PI), 2*this.m);
		}
		return -sum;
	}
	
	public double getFitness() {
		return this.getValor();
	}
	
	@Override
	public void mutacion(double probMutacion) {
		// TODO Auto-generated method stub
		for(int i = 0; i < this.tamTotal; i++) {
			if(this.rand.nextDouble() < probMutacion)
				this.cromosoma[i] = this.rand.nextDouble(this.min[0], this.max[0]);
		}
	}
}
