package Cruce;

import java.util.Arrays;
import java.util.Random;

public class CruceOX_ordenPrio extends Cruce<Integer>{

	public CruceOX_ordenPrio(int tamCromosoma) {
		super(tamCromosoma);
		// TODO Auto-generated constructor stub
	}

	
	//Generamos 7 posiciones al hazar, nos guardamos su valor y marcamos su posición, las posiciones no marcadas se intercambian, para el
	//resto se intercambian con el primer valor obtenido de una posición al hazar del otro cromosoma que no haya sido ya cruzada
	@Override
	public void cruzar(Integer[] c1, Integer[] c2) {
		// TODO Auto-generated method stub
		Integer[] vals1 = new Integer[this.distCorte];
		Integer[] vals2 = new Integer[this.distCorte];
		int pos = -1, cont1 = 0, cont2 = 0;
		Random rand = new Random();
		Boolean[] orden1 = new Boolean[this.tamCromosoma];
		Boolean[] orden2 = new Boolean[this.tamCromosoma];
		Boolean[] usedPos = new Boolean[this.tamCromosoma];
		Arrays.fill(orden1, false);
		Arrays.fill(orden2, false);
		Arrays.fill(usedPos, false);
		Integer[] c1Cruc = new Integer[this.tamCromosoma];
		Integer[] c2Cruc = new Integer[this.tamCromosoma];
		for(int i = 0; i < this.distCorte; i++) {
			pos = rand.nextInt(0, this.tamCromosoma);
			while(usedPos[pos]) 
				pos = rand.nextInt(0, this.tamCromosoma);
			usedPos[pos] = true;
			vals1[i] = c1[pos];
			vals2[i] = c2[pos];
			for(int j = 0; j < this.tamCromosoma; j++) {
				if(vals1[i] == c2[j])
					orden1[j] = true;
				if(vals2[i] == c1[j])
					orden2[j] = true;	
			}
		}
		for(int i = 0; i < this.tamCromosoma; i++) {
			if(!orden1[i]) {
				c1Cruc[i] = c2[i];
			}
			else {
				c1Cruc[i] = vals1[cont1];
				cont1++;
			}
			if(!orden2[i]) {
				c2Cruc[i] = c1[i];
			}
			else {
				c2Cruc[i] = vals2[cont2];
				cont2++;
			}
		}
		for(int i = 0; i < this.tamCromosoma; i++) {
			c1[i] = c1Cruc[i];
			c2[i] = c2Cruc[i];
		}
	}

}
