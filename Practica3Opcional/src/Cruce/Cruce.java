package Cruce;

public abstract class Cruce{
	int tamCromosoma;
	public Cruce (int tamCromosoma) {
		this.tamCromosoma = tamCromosoma;
	}
	
	public abstract void cruzar(int[] c1, int[] c2);
}
