package Mutaciones;

import java.util.Random;

public class MutacionBasica {
	
	private int tamCromosoma;
	private double probMutacion;
	
	public MutacionBasica(int tamCromosoma, double probMutacion) {
		this.tamCromosoma = tamCromosoma;
		this.probMutacion = probMutacion;
	}
	
	
	public void mutacion(int [] cromosoma) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		for(int i = 0; i < this.tamCromosoma; i++) {
			if(rand.nextDouble() < probMutacion)
				cromosoma[i] = rand.nextInt(256);
		}
	}
}
