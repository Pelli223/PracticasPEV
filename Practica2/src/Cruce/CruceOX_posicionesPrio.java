package Cruce;

import java.util.Arrays;
import java.util.Random;

public class CruceOX_posicionesPrio extends Cruce<Integer>{
	

	public CruceOX_posicionesPrio(int tamCromosoma) {
		super(tamCromosoma);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void cruzar(Integer[] c1, Integer[] c2) {
		// TODO Auto-generated method stub
		int[] pos = new int[this.distCorte];
		Random rand = new Random();
		int j;
		Boolean[] orden1 = new Boolean[this.tamCromosoma];
		Boolean[] orden2 = new Boolean[this.tamCromosoma];
		Arrays.fill(orden1, false);
		Arrays.fill(orden2, false);
		Integer[] c1Cruc = new Integer[this.tamCromosoma];
		Integer[] c2Cruc = new Integer[this.tamCromosoma];
		int cont1, cont2;
		
		for(int i = 0; i < this.distCorte; i++) {
			pos[i] = rand.nextInt(0, this.tamCromosoma);
			c1Cruc[pos[i]] = c2[pos[i]];
			c2Cruc[pos[i]] = c1[pos[i]];
			orden1[c1Cruc[pos[i]]] = true;
			orden2[c2Cruc[pos[i]]] = true;
		}
		j = (pos[this.distCorte-1] + 1) % this.tamCromosoma;
		cont1 = j;
		cont2 = j;
		while(j != pos[this.distCorte-1]) {
			if(!this.isPos(pos, j)) {
				cont1 = this.orden(c1Cruc, c1, orden1, j, cont1);
				cont2 = this.orden(c2Cruc, c2, orden2, j, cont2);
			}
			j++;
			j = j % this.tamCromosoma;
		}
		for(int i = 0; i < this.tamCromosoma; i++) {
			c1[i] = c1Cruc[i];
			c2[i] = c2Cruc[i];
		}
	}

	private boolean isPos(int[]pos, int posAct) {
		Boolean is = false;
		for(int i = 0; i < this.distCorte; i++) {
			if(pos[i] == posAct) {
				is = true;
				break;
			}
		}
		return is;
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
