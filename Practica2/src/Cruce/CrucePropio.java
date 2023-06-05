package Cruce;

import java.util.Random;

public class CrucePropio extends Cruce<Integer>{

	public CrucePropio(int tamCromosoma) {
		super(tamCromosoma);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cruzar(Integer[] c1, Integer[] c2) {
		// TODO Auto-generated method stub
		int i = 0;
		int pos, val, temp;
		Random rand = new Random();
		Integer[] c1Cruc = new Integer[this.tamCromosoma];
		Integer[] c2Cruc = new Integer[this.tamCromosoma];
		c1Cruc = c1.clone();
		c2Cruc = c2.clone();
		while(i < 7) {
			pos = rand.nextInt(0, this.tamCromosoma);
			val = c2Cruc[pos];
			for(int j = 0; j < this.tamCromosoma; j++) {
				if(val == c1Cruc[j]) {
					temp = c1Cruc[pos];
					c1Cruc[pos] = val;
					c1Cruc[j] = temp;
				}
			}
			val = c1Cruc[pos];	
			for(int j = 0; j < this.tamCromosoma; j++) {
				if(val == c2Cruc[j]) {
					temp = c2Cruc[pos];
					c2Cruc[pos] = val;
					c2Cruc[j] = temp;
				}
			}
			i++;
		}	
		for(int j = 0; j < this.tamCromosoma; j++) {
			c1[j] = c1Cruc[j];
			c2[j] = c2Cruc[j];
		}
	}

}
