package Mutaciones;

public class MutacionFactory { 
	
		public static Mutacion getMutacionType(int n, int maxProfundidad){
			switch(n) {
			case 1: return new MutacionArbolSubarbol(maxProfundidad);
			case 2: return new MutacionFuncional(maxProfundidad);
			case 3: return new MutacionPermutacion(maxProfundidad);
			case 4: return new MutacionHoist(maxProfundidad);
			case 5: return new MutacionContraccion(maxProfundidad);
			case 6: return new MutacionExpansion(maxProfundidad);
			default: return new MutacionArbolSubarbol(maxProfundidad);
			}
		}
	}
