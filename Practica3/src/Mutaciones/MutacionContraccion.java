package Mutaciones;

import java.util.Random;

import Individuos.Arbol;
import Individuos.Individuo;

public class MutacionContraccion extends Mutacion {
/**Reduce un subárbol a un terminal
*/
	MutacionContraccion(int maxProfundidad) {
		super(maxProfundidad);
		// TODO Auto-generated constructor stub
	}

	public void mutar(Arbol arbol) {
		
		Random rand = new Random();
		String terminal=Individuo.getTerm(rand.nextInt(6));
		Arbol nodo=arbol.at(rand.nextInt(1, arbol.getTotalNodos()));
		Arbol nuevoArbol=new Arbol(terminal,this.maxProfundidad);
		nodo.setNodo(nuevoArbol);
		arbol.actualizaTotalNodo();
		arbol.setAlturas(0);
		
		
	}
}
