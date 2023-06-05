package Individuos;
import java.util.Random;

public abstract class Individuo <T>{
	T[] cromosoma;
	int tamTotal;
	Random rand = new Random();
	boolean isMin;
	
	public abstract int getFitness();
	public abstract int getFenotipo(int n);
	
	public boolean isMin() {
		return this.isMin;
	}
	
	public T[]getCromosoma(){
		return this.cromosoma.clone();
	}
	
	public void setCromosoma(T[] crom) {
		for(int i = 0; i < this.tamTotal; i++)
			this.cromosoma[i] = crom[i];
	}
	
	public int getTamCromosoma() {
		return this.tamTotal;
	}
	
}
