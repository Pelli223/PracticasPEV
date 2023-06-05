
public class CruceFactory {
	public static Cruce<?>  getCruceType(int n, boolean func5 ,int tamCromosoma){
		if(n == 1 && !func5)
			return new CruceMonopunto<Boolean>(tamCromosoma);
		else if (n==1 && func5)
			return new CruceMonopunto<Double>(tamCromosoma);
		else if (n==2 && !func5)
			return new CruceUniform<Boolean>(tamCromosoma);
		else if (n==2 && func5)
			return new CruceUniform<Double>(tamCromosoma);
		else if (n==3 && func5)
			return new CruceAritmetico(tamCromosoma);
		else if(n==4 && func5)
			return new CruceBLXAlf(tamCromosoma);
		else 
			return null;			
	}
} 
