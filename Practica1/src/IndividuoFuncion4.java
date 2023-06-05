
public class IndividuoFuncion4 extends Individuo<Boolean>{
	private int d;
	private int m = 10;
	
	public IndividuoFuncion4(double valorError, int d) {
		this.isMin = true;
		this.d = d;
		this.tamGenes = new int[1];
		this.min = new double[1];
		this.max = new double[1];
		this.valorError = valorError;
		this.min[0] = 0;
		this.max[0] = Math.PI;
		this.tamGenes[0] = this.tamGen(this.valorError, min[0], max[0]);
		this.tamTotal = tamGenes[0] * this.d;
		this.cromosoma = new Boolean[tamTotal];
		for(int i = 0; i < tamTotal; i++) this.cromosoma[i] = this.rand.nextBoolean();}

	public int tamGen(double valorError, double min, double max) {
		return (int) (Math.log10(((max - min) / this.valorError) + 1) / Math.log10(2));
	}
	
	private long bin2dec(Boolean[] gen) {
		long result = 0;
		for (boolean bit : gen) {
			result = result * 2 + (bit ? 1 : 0);
		}
		return result;
	}
	
	public double getFenotipo(int n) {
		Boolean[] v = new Boolean[this.tamGenes[0]];
		if(n == 0)
			for(int i = 0; i < this.tamGenes[0]; i++)
				v[i] = this.cromosoma[i];
		else
			for(int i = 0; i < this.tamGenes[0]; i++)
				v[i] = this.cromosoma[(this.tamGenes[0] * n)+i];
		
		return this.min[0] + this.bin2dec(v) * ((this.max[0] - this.min[0]) / (Math.pow(2, this.tamGenes[0])-1));
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
				this.cromosoma[i] = this.rand.nextBoolean();
		}
	}
}
