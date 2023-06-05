import java.util.Random;

public class CruceBLXAlf extends Cruce<Double>{
	
	private double alfa = 0.5;

	public CruceBLXAlf(int tamCromosoma) {
		super(tamCromosoma);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cruzar(Double[] c1, Double[] c2) {
		// TODO Auto-generated method stub
		double max, min, L;
		Random rand = new Random();
		for( int i = 0; i < this.tamCromosoma; i++) {
			if(c1[i] > c2[i]) {
				max = c1[i];
				min = c2[i];
			}
			else {
				max = c2[i];
				min = c1[i];				
			}
			L = max - min;
			if(L != 0) {
				c1[i] = rand.nextDouble(min-L*this.alfa, max+L*this.alfa);
				c2[i] = rand.nextDouble(min-L*this.alfa, max+L*this.alfa);
			}
		}
	}

}
