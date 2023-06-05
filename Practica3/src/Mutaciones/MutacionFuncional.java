package Mutaciones;

import java.util.Random;

import Individuos.Arbol;
import Individuos.Individuo;

public class MutacionFuncional extends Mutacion {
	/**Se selecciona al azar una función dentro del individuo, y se
sustituye por otra diferente del conjunto de funciones posibles con el mismo
número de operandos*/
	MutacionFuncional(int maxProfundidad) {
		super(maxProfundidad);
		// TODO Auto-generated constructor stub
	}

	public void mutar(Arbol arbol) {
		
		Random rand = new Random();
		String funcionN=Individuo.getFunc(new Random().nextInt(3));
		Arbol arbo=arbol.at(rand.nextInt(arbol.getTotalNodos()));
		int posFuncion=0;
		while(!Individuo.esFuncion(arbo.getValor())) {
			posFuncion=rand.nextInt(arbol.getTotalNodos());
			arbo=arbol.at(posFuncion);
		}
		arbo.setValor(funcionN);
		
		
	}
}
