package Selecciones;

public class SeleccionFactory {
	public static Seleccion getSeleccioAlg(int n, double[] fitness, int tamPoblacion, double max) {
		switch(n) {
		case 1: return new SeleccionRuleta(fitness, tamPoblacion, max);
		case 2: return new SeleccionEstoUniver(fitness, tamPoblacion, max);
		case 3: return new SeleccionTorneoDet(fitness, tamPoblacion);
		case 4: return new SeleccionTorneoProb(fitness, tamPoblacion);
		case 5: return new SeleccionTrunc(fitness, tamPoblacion, max);
		case 6: return new SeleccionRestos(fitness, tamPoblacion, max);
		case 7: return new SeleccionRanking(fitness, tamPoblacion, max);
		default: return  new SeleccionRuleta(fitness, tamPoblacion, max);
		}
		
	}
}
