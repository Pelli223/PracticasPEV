package Cruce;

public class CruceFactory {
	public static Cruce getCruceType(int n, int tamCromosoma){
		if(n == 1)
			return new CruceMonopunto(tamCromosoma);
		else if (n==2)
			return new CruceUniform(tamCromosoma);
		else 
			return null;			
	}
} 
