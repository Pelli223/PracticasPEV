package Selecciones;
import java.util.Random;

public abstract class Seleccion {
	int[] seleccion;
	double[] fitness;
	double[] prob;
	int tamPoblacion;
	Random rand = new Random();
	
	public abstract int[] getSeleccion();
	
	protected void corrigeMinimizar(double max, double[] fitnessCorr) {
		for(int i = 0; i < this.tamPoblacion; i++) 
			fitnessCorr[i] = (1.05 * max) - this.fitness[i];
	}
	
}
