package Cruce;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class CruceCO extends Cruce<Integer>{

	public CruceCO(int tamCromosoma) {
		super(tamCromosoma);
		// TODO Auto-generated constructor stub
	}

	@Override
	
	//codificamos y realizamos el cruce monopunto despues decodificamos
	public void cruzar(Integer[] c1, Integer[] c2) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int temp;
		int corte = rand.nextInt(0, this.tamCromosoma);
		this.codifica(c1);
		this.codifica(c2);
		for(int i = 0; i < corte; i++) {
			temp = c1[i];
			c1[i] = c2[i];
			c2[i] = temp;
		}
		this.decodifica(c1);
		this.decodifica(c2);
	}
	
	//codifica el cormosoma
	private void codifica(Integer[] c) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26));
		int temp;
		for(int i = 0; i < this.tamCromosoma; i++) {
			temp = list.indexOf(c[i]);
			list.remove(temp);
			c[i] = temp;
		}
	}
	
	
	//decodifica el cormosoma
	private void decodifica(Integer[] c) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26));
		int temp, ind;
		for(int i = 0; i < this.tamCromosoma; i++) {
			temp = list.get(c[i]);
			ind = list.indexOf(temp);
			list.remove(ind);
			c[i] = temp;
		}
	}

}
