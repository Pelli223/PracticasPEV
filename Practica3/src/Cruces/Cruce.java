package Cruces;

import java.util.Random;

import Individuos.Arbol;

public class Cruce {
	public void cruzar(Arbol p1, Arbol p2) {
		Random rand = new Random();
		int numNodosMin = Math.min(p1.getTotalNodos(), p2.getTotalNodos());
		int nodo;
		Arbol cruc1, cruc2;
		nodo = rand.nextInt(numNodosMin);
		cruc1 = new Arbol(p1.at(nodo));
		cruc2 = new Arbol(p2.at(nodo));
		p1.at(nodo).setNodo(cruc2);
		p2.at(nodo).setNodo(cruc1);
		p1.actualizaTotalNodo();
		p2.actualizaTotalNodo();
		p1.setAlturas(0);
		p2.setAlturas(0);
	}
}
