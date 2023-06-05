package Cruce;

public abstract class Cruce <T>{
	
	protected int distCorte = 7;
	
	int tamCromosoma;
	public Cruce (int tamCromosoma) {
		this.tamCromosoma = tamCromosoma;
	}
	
	public abstract void cruzar(T[] c1, T[] c2);
	
}
