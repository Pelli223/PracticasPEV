import java.util.Random;

public abstract class Seleccion {
	int[] seleccion;
	double[] fitness;
	double[] prob;
	int tamPoblacion;
	boolean min;
	Random rand = new Random();
	
	public abstract int[] getSeleccion();
}
