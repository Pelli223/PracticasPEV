import java.util.Random;

public abstract class Individuo <T>{
	T[] cromosoma;
	int[] tamGenes;
	double[] max;
	double[] min;
	double valorError;
	int tamTotal;
	Random rand = new Random();
	boolean isMin;
	
	public abstract double getFitness();
	public abstract double getFenotipo(int n);
	public abstract void mutacion(double probMutacion);
	
	public boolean isMin() {
		return this.isMin;
	}
	
	public T[]getCromosoma(){
		return this.cromosoma;
	}
	
	public void setCromosoma(T[] crom) {
		for(int i = 0; i < this.tamTotal; i++)
			this.cromosoma[i] = crom[i];
	}
	
	public int getTamCromosoma() {
		return this.tamTotal;
	}
	
}
