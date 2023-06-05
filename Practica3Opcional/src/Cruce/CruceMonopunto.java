package Cruce;
import java.util.Random;

public class CruceMonopunto extends Cruce {
	
	public CruceMonopunto(int tamCromosoma) {
		super(tamCromosoma);
		// TODO Auto-generated constructor stub
	}

	public void cruzar(int[] c1, int[] c2) {
		int i = 0;
		int temp;
		Random rand = new Random();
		int x = rand.nextInt(0, this.tamCromosoma);
		for( i = 0; i < x; i++) {
			temp = c1[i];
			c1[i] = c2[i];
			c2[i] = temp;
		}
	}

}
