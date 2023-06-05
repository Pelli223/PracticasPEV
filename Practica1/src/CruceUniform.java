import java.util.Random;

public class CruceUniform<T> extends Cruce <T>{

	public CruceUniform(int tamCromosoma) {
		super(tamCromosoma);
		// TODO Auto-generated constructor stub
	}
	
	public void cruzar(T[] c1, T[] c2) {
		int i = 0;
		T temp = null;
		Random rand = new Random();
		double x ;
		for( i = 0; i < this.tamCromosoma; i++) {
			x = rand.nextDouble(0, 1);
			if(x > 0.5) {
				temp = c1[i];
				c1[i] = c2[i];
				c2[i] = temp;
			}
		}
	}

}
