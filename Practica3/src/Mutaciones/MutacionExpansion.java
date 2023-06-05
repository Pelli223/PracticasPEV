package Mutaciones;

import java.util.Random;

import Individuos.Arbol;
import Individuos.Individuo;

public class MutacionExpansion extends Mutacion {
	/**Expande un nodo terminal*/
	MutacionExpansion(int maxProfundidad) {
		super(maxProfundidad);
		// TODO Auto-generated constructor stub
	}

	public void mutar(Arbol arbol) {		
		Random rand = new Random();
		Arbol nodo=arbol.at(rand.nextInt(arbol.getTotalNodos()));
		while(!nodo.esHoja()) {
			nodo=arbol.at(rand.nextInt(arbol.getTotalNodos()));
		}
		Arbol arbolRamdom=new Arbol(Individuo.getFunc(rand.nextInt(3)),this.maxProfundidad);
		arbolRamdom.inicializacionCompleta(0, this.maxProfundidad);
		nodo.setNodo(arbolRamdom);
		arbol.actualizaTotalNodo();
		arbol.setAlturas(0);
	}
}




