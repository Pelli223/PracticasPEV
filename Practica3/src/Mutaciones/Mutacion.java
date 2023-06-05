package Mutaciones;

import Individuos.Arbol;

public abstract class Mutacion {
	
	protected int maxProfundidad;
	
	Mutacion(int maxProfundidad) {
		this.maxProfundidad = maxProfundidad;
	}

	public abstract void mutar(Arbol arbol);

}
