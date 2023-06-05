package Cruce;

import java.util.Arrays;
import java.util.Random;

public class CrucePMX extends Cruce<Integer>{

	public CrucePMX(int tamCromosoma) {
		super(tamCromosoma);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cruzar(Integer[] c1, Integer[] c2) {
		// TODO Auto-generated method stub
		int j;
		Boolean[] orden1 = new Boolean[this.tamCromosoma];
		Boolean[] orden2 = new Boolean[this.tamCromosoma];
		Arrays.fill(orden1, false);
		Arrays.fill(orden2, false);
		Integer[] c1Cruc = new Integer[this.tamCromosoma];
		Integer[] c2Cruc = new Integer[this.tamCromosoma];
		Random rand = new Random();
		int corte1 = rand.nextInt(0, this.tamCromosoma-this.distCorte);
		int corte2 = corte1 + this.distCorte;
		int pos;
		for(int i = corte1; i <= corte2; i++) {
			c1Cruc[i] = c2[i];
			c2Cruc[i] = c1[i];
			orden1[c1Cruc[i]] = true;
			orden2[c2Cruc[i]] = true;
		}
		j = (corte2+1)%this.tamCromosoma;
		while(j != corte1) {
			if(!orden1[c1[j]]) {
				c1Cruc[j] = c1[j];
				orden1[c1Cruc[j]] = true;
			}
			else {
				pos = j;
				for(int i = corte1; i <= corte2; i++) {
					if(c2[i] == c1[pos]) {
						if(!orden1[c1[i]]) {
							c1Cruc[j] = c1[i];
							orden1[c1Cruc[j]] = true;
							break;
						}
						else {
							pos = i;
							i = corte1-1;
						}
					}
				}
			}
			if(!orden2[c2[j]]) {
				c2Cruc[j] = c2[j];
				orden2[c2Cruc[j]] = true;
			}
			else {
				pos = j;
				for(int i = corte1; i <= corte2; i++) {
					if(c1[i] == c2[pos]) {
						if(!orden2[c2[i]]) {
							c2Cruc[j] = c2[i];
							orden2[c2Cruc[j]] = true;
							break;
						}
						else {
							pos = i;
							i = corte1-1;
						}
					}
				}
			}
			j++;
			j = j % this.tamCromosoma;
		}
		for(int i = 0; i < this.tamCromosoma; i++) {
			c1[i] = c1Cruc[i];
			c2[i] = c2Cruc[i];
		}
	}

}
