
public class IndividuoFuncion1 extends Individuo <Boolean>{
	public IndividuoFuncion1(double valorError) {
		this.isMin = false;
		this.tamGenes = new int[2];
		this.min = new double[2];
		this.max = new double[2];
		this.valorError = valorError;
		this.min[0] = -3.000;
		this.min[1] = 4.100;
		this.max[0] = 12.100;
		this.max[1] = 5.800;
		this.tamGenes[0] = this.tamGen(this.valorError, min[0], max[0]);
		this.tamGenes[1] = this.tamGen(this.valorError, min[1], max[1]);
		this.tamTotal = tamGenes[0] + tamGenes[1];
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
		Boolean[] v = new Boolean[this.tamGenes[n]];
		if (n == 0) {
			for(int i = 0; i < this.tamGenes[n]; i++)
				v[i] = this.cromosoma[i];
		}
		else {
			for(int i = 0; i < this.tamGenes[n]; i++)
				v[i] = this.cromosoma[this.tamGenes[n-1]+i];
		}
		return this.min[n] + this.bin2dec(v) * ((this.max[n] - this.min[n]) / (Math.pow(2, this.tamGenes[n])-1));
	}
	
	public double getValor() {
		double x1 = this.getFenotipo(0), x2 = this.getFenotipo(1);
		return (21.5 + x1 * Math.sin(4 * Math.PI * x1) + x2 * Math.sin(20 * Math.PI * x2));
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