package Mutaciones;

import java.util.Random;

import Individuos.Arbol;

public class MutacionPermutacion extends Mutacion{
	/**Intercambia el orden de la lista de argumentos de una función*/
	MutacionPermutacion(int maxProfundidad) {
		super(maxProfundidad);
		// TODO Auto-generated constructor stub
	}

	public void mutar(Arbol arbol) {
		Random rand = new Random();
		int indiceAleatorio=rand.nextInt(arbol.getTotalNodos());
		Arbol arbo=arbol.at(indiceAleatorio);
		while(arbo.esHoja()) {
			indiceAleatorio=rand.nextInt(arbol.getTotalNodos());
			arbo=arbol.at(indiceAleatorio);
		}
		Arbol izq= arbo.getHijoIzq();
		Arbol der= arbo.getHijoDer();		
		arbo.setHijoIzq(der);
		arbo.setHijoDer(izq);	}

}

