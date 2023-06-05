package Cruce;

import java.util.Arrays;
import java.util.Random;

public class CruceOX extends Cruce<Integer>{

	public CruceOX(int tamCromosoma) {
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
		int cont1 = 0; int cont2 = 0;
		for(int i = corte1; i <= corte2; i++) {
			c1Cruc[i] = c2[i];
			c2Cruc[i] = c1[i];
			orden1[c1Cruc[i]] = true;
			orden2[c2Cruc[i]] = true;
		}
		j = (corte2+1)%this.tamCromosoma;
		cont1 = j;
		cont2 = j;
		while(j != corte1) {
			cont1 = this.orden(c1Cruc, c1, orden1, j, cont1);
			cont2 = this.orden(c2Cruc, c2, orden2, j, cont2);
			j++;
			j = j % this.tamCromosoma;
		}
		for(int i = 0; i < this.tamCromosoma; i++) {
			c1[i] = c1Cruc[i];
			c2[i] = c2Cruc[i];
		}
	}
	
	private int orden(Integer[] cruc, Integer[]c, Boolean ord[], int pos, int cont) {
		boolean ok = false;
		while(!ok) {
			if(!ord[c[cont]]) {
				cruc[pos] = c[cont];
				ord[cruc[pos]] = true;
				ok = true;
			}
			else {
				cont++;
				cont = cont%this.tamCromosoma;
			}
		}
		return cont;
	}

}
