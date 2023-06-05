package Mutaciones;

import java.util.Random;

import Individuos.Arbol;
import Individuos.Individuo;

public class MutacionArbolSubarbol extends Mutacion{
	 /**Se selecciona un subárbol del individuo, se elimina totalmente
	 el subárbol seleccionado y en su lugar se incorpora un nuevo subárbol generado
	 aleatoriamente*/
	MutacionArbolSubarbol(int maxProfundidad) {
		super(maxProfundidad);
		// TODO Auto-generated constructor stub
	}

	public  void mutar(Arbol arbol) {
		
		// cogemos arbol aleatorio del individuo
		Arbol arbolRamdom;
		Arbol nodo=arbol.at(new Random().nextInt(1, arbol.getTotalNodos()));
		//miramos su altura para generar un arbol que al insertarlo no se pase de la altura total
		int altura=nodo.getAltura();
		while(altura >= this.maxProfundidad) {
			nodo=arbol.at(new Random().nextInt(1, arbol.getTotalNodos()));
			altura=nodo.getAltura();
		}
		// creamos un arbol ramdom con la altura maxima calculada anteriormente
		arbolRamdom=new Arbol(Individuo.getFunc(new Random().nextInt(3)), this.maxProfundidad);
		arbolRamdom.inicializacionCreciente(altura, this.maxProfundidad);
		//ponemos el nuevo arbol donde estaba el anterior
		nodo.setNodo(arbolRamdom);
		arbol.actualizaTotalNodo();
		arbol.setAlturas(0);
	}
}
