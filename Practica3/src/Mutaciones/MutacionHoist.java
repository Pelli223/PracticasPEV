package Mutaciones;

import java.util.Random;

import Individuos.Arbol;

public class MutacionHoist extends Mutacion {
	/**Muta a un subarbol*/
	MutacionHoist(int maxProfundidad) {
		super(maxProfundidad);
		// TODO Auto-generated constructor stub
	}

	public void mutar(Arbol arbol) {
		
		Random rand = new Random();
		
		// cogemos arbol aleatorio del individuo
		Arbol nodo=arbol.at(rand.nextInt(arbol.getTotalNodos()));
		//miramos su altura para no escoger un arbol con altura por debaajo de la minima(2).
		int altura=nodo.getAltura();
		while(altura<2) {
			nodo=arbol.at(rand.nextInt(arbol.getTotalNodos()));
			altura=nodo.getAltura();
		}		
		arbol=nodo;
		arbol.actualizaTotalNodo();
		arbol.setAlturas(0);
	}
}




