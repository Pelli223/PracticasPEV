
public class CruceAritmetico extends Cruce<Double> {
	
	private double alfa = 0.6;
	
	public CruceAritmetico(int tamCromosoma) {
		super(tamCromosoma);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cruzar(Double[] c1, Double[] c2) {
		// TODO Auto-generated method stub
		double h1,h2;
		for(int i = 0; i < this.tamCromosoma; i++) {
			h1 = this.alfa*c1[i] + (1-this.alfa)*c2[i];
			h2 = this.alfa*c2[i] + (1-this.alfa) * c1[i];
			c1[i] = h1;
			c2[i] = h2;
		}
	}
}
