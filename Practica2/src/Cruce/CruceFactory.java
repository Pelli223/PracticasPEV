package Cruce;

public class CruceFactory {
	public static Cruce<Integer>  getCruceType(int n, int tamCromosoma){
		switch(n) {
		case 1: return new CrucePMX(tamCromosoma);
		case 2: return new CruceOX(tamCromosoma);
		case 3: return new CruceOX_posicionesPrio(tamCromosoma);
		case 4: return new CruceOX_ordenPrio(tamCromosoma);
		case 5: return new CruceCX(tamCromosoma);
		case 6: return new CruceERX(tamCromosoma);
		case 7: return new CruceCO(tamCromosoma);
		case 8: return new CrucePropio(tamCromosoma);
		default: return new CrucePMX(tamCromosoma);
		}
	}
} 
