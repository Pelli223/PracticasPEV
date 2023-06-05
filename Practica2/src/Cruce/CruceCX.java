package Cruce;

import java.util.Arrays;

public class CruceCX extends Cruce<Integer>{

	public CruceCX(int tamCromosoma) {
		super(tamCromosoma);
		// TODO Auto-generated constructor stub
	}

	@Override
	
	//Realizamos el recorrido hasta dar con un ciclo de los cromosomas y marcando por las posiciones que hemos pasado
	//En estas nos quedamos con su valor antes del cruce el resto se intercambian
	public void cruzar(Integer[] c1, Integer[] c2) {
		// TODO Auto-generated method stub
		boolean ciclo = false;
		int i = 0;
		Boolean[] orden1 = new Boolean[this.tamCromosoma];
		Boolean[] orden2 = new Boolean[this.tamCromosoma];
		Arrays.fill(orden1, false);
		Arrays.fill(orden2, false);
		Integer[] c1Cruc = new Integer[this.tamCromosoma];
		Integer[] c2Cruc = new Integer[this.tamCromosoma];
		c1Cruc[0] = c1[0];
		c2Cruc[0] = c2[0];
		orden1[0] = true;
		orden2[0] = true;
		while (!ciclo) {
			for(int j = 0; j < this.tamCromosoma; j++) {
				if(c2[i] == c1[j]) {
					if(orden1[j]) {
						ciclo = true;
						break;
					}
					else {
						c1Cruc[j] = c1[j];
						i = j;
						orden1[j] = true;
						break;
					}
				}
			}
		}
		ciclo = false;
		i = 0;
		while (!ciclo) {
			for(int j = 0; j < this.tamCromosoma; j++) {
				if(c1[i] == c2[j]) {
					if(orden2[j]) {
						ciclo = true;
						break;
					}
					else {
						c2Cruc[j] = c2[j];
						i = j;
						orden2[j] = true;
						break;
					}
				}
			}
		}
		for(int j = 0; j < this.tamCromosoma; j++) {
			if(!orden1[j]) {
				c1Cruc[j] = c2[j];
				orden1[j] = true;
			}
			if(!orden2[j]) {
				c2Cruc[j] = c1[j];
				orden2[j] = true;
			}
		}
		for(int j = 0; j < this.tamCromosoma; j++) {
			c1[j] = c1Cruc[j];
			c2[j] = c2Cruc[j];
		}
	}

}
