import java.util.Random;

public class CruceMonopunto<T> extends Cruce <T>{
	
	public CruceMonopunto(int tamCromosoma) {
		super(tamCromosoma);
		// TODO Auto-generated constructor stub
	}

	public void cruzar(T[] c1, T[] c2) {
		int i = 0;
		T temp;
		Random rand = new Random();
		int x = rand.nextInt(0, this.tamCromosoma);
		for( i = 0; i < x; i++) {
			temp = c1[i];
			c1[i] = c2[i];
			c2[i] = temp;
		}
	}

}
