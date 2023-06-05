
public class SeleccionFactory {
	public static Seleccion getSeleccioAlg(int n, double[] fitness, int tamPoblacion, boolean min, double mejor) {
		switch(n) {
		case 1: return new SeleccionRuleta(fitness, tamPoblacion, min, mejor);
		case 2: return new SeleccionEstoUniver(fitness, tamPoblacion, min, mejor);
		case 3: return new SeleccionTorneoDet(fitness, tamPoblacion, min);
		case 4: return new SeleccionTorneoProb(fitness, tamPoblacion, min);
		case 5: return new SeleccionTrunc(fitness, tamPoblacion, min, mejor);
		case 6: return new SeleccionRestos(fitness, tamPoblacion, min);
		default: return  new SeleccionRuleta(fitness, tamPoblacion, min, mejor);
		}
		
	}
}
