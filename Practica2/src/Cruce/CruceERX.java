package Cruce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CruceERX extends Cruce<Integer>{
	
	private List<List<Integer>> tablaConex; 

	public CruceERX(int tamCromosoma) {
		super(tamCromosoma);
		// TODO Auto-generated constructor stub
		//this.tablaConex = new ArrayList<List<Integer>>();
		//for(int i = 0; i < this.tamCromosoma; i++)
			//tablaConex.add(new ArrayList<Integer>());
	}

	@Override
	public void cruzar(Integer[] c1, Integer[] c2) {
		// TODO Auto-generated method stub
		Boolean[] orden1 = new Boolean[this.tamCromosoma];
		Boolean[] orden2 = new Boolean[this.tamCromosoma];
		boolean is1 = false, is2 = false;
		Arrays.fill(orden1, false);
		Arrays.fill(orden2, false);
		Integer[] c1Cruc = new Integer[this.tamCromosoma];
		Integer[] c2Cruc = new Integer[this.tamCromosoma];
		Random rand = new Random();
		int j = 0, aux;
		int cont = 1;
		int trys = this.tamCromosoma*4;
		int min;
		
		this.constructConex(c1, c2);
		
		c1Cruc[0] = c2[0];
		orden1[c1Cruc[0]] = true;
		c2Cruc[0] = c1[0];
		orden2[c2Cruc[0]] = true;
		
		//Recorremos las listas de adyacencia de cada ciudad construyendo las rutas cogiendo las de menor adyacencia
		//En caso de bloqueo repetimos hasta un total de 27*4 veces en cuyo caso cancelamos el cruce y el cromosoma se queda igual
		while(cont < this.tamCromosoma && trys > 0) {
			
			min = 27;
			aux = this.tablaConex.get(c1Cruc[j]).get(0);
			for(Integer i: this.tablaConex.get(c1Cruc[j])) {
				if(!orden1[i] && (min > this.tablaConex.get(i).size())) {
					min = this.tablaConex.get(i).size();
					aux = i;
				}
				else if(!orden1[i] && (min == this.tablaConex.get(i).size())) {
					if(rand.nextBoolean()) {
						min = this.tablaConex.get(i).size();
						aux = i;
					}
				}
			}
			if(!orden1[aux]) {
				j++;
				c1Cruc[cont] = aux;
				orden1[c1Cruc[cont]] = true;
				cont++;
			}
			else {
				j = 0;
				cont = 1;
				Arrays.fill(orden1, false);
				c1Cruc[0] = c2[0];
				orden1[c1Cruc[0]] = true;
				trys--;
			}
		}
		
		j = 0;
		cont = 1;
		trys = this.tamCromosoma*4;
		
		//Segundo progenitor
		while(cont < this.tamCromosoma && trys > 0) {
			min = 27;
			aux = this.tablaConex.get(c2Cruc[j]).get(0);
			for(Integer i: this.tablaConex.get(j)) {
				if(!orden2[i] && (min > this.tablaConex.get(i).size())) {
					min = this.tablaConex.get(i).size();
					aux = i;
				}
				else if(!orden2[i] && (min == this.tablaConex.get(i).size())) {
					if(rand.nextBoolean()) {
						min = this.tablaConex.get(i).size();
						aux = i;
					}
				}
			}
			if(!orden2[aux]) {
				j++;
				c2Cruc[cont] = aux;
				orden2[c2Cruc[cont]] = true;
				cont++;
			}
			else {
				trys--;
				j = 0;
				cont = 1;
				Arrays.fill(orden2, false);
				c2Cruc[0] = c1[0];
				orden2[c2Cruc[0]] = true;
			}
		}
		for(int i = 0; i < this.tamCromosoma; i++) {
			if(is1)
				c1[i] = c1Cruc[i];
			if(is2)
				c2[i] = c2Cruc[i];
		}
	}
	
	private void constructConex(Integer[] c1, Integer[] c2) {
		//Se construye la tabla de conectividades 
		this.tablaConex = new ArrayList<List<Integer>>();
		for(int i = 0; i < this.tamCromosoma; i++)
			tablaConex.add(new ArrayList<Integer>());
		
		this.tablaConex.get(c1[0]).add(c1[this.tamCromosoma-1]);
		this.tablaConex.get(c1[0]).add(c1[1]);
		if(!this.tablaConex.get(c2[0]).contains(c2[this.tamCromosoma-1]))
			this.tablaConex.get(c2[0]).add(c2[this.tamCromosoma-1]);
		if(!this.tablaConex.get(c2[0]).contains(c2[1]))
			this.tablaConex.get(c2[0]).add(c2[1]);
		for(int i = 1; i < this.tamCromosoma-1; i++) {
			if(!this.tablaConex.get(c1[i]).contains(c1[i-1]))
				this.tablaConex.get(c1[i]).add(c1[i-1]);
			if(!this.tablaConex.get(c1[i]).contains(c1[i+1]))
				this.tablaConex.get(c1[i]).add(c1[i+1]);
			if(!this.tablaConex.get(c2[i]).contains(c2[i-1]))
				this.tablaConex.get(c2[i]).add(c2[i-1]);
			if(!this.tablaConex.get(c2[i]).contains(c2[i+1]))
				this.tablaConex.get(c2[i]).add(c2[i+1]);

		}
		if(!this.tablaConex.get(c1[this.tamCromosoma-1]).contains(c1[this.tamCromosoma-2]))
			this.tablaConex.get(c1[this.tamCromosoma-1]).add(c1[this.tamCromosoma-2]);
		if(!this.tablaConex.get(c1[this.tamCromosoma-1]).contains(c1[0]))
			this.tablaConex.get(c1[this.tamCromosoma-1]).add(c1[0]);
		if(!this.tablaConex.get(c2[this.tamCromosoma-1]).contains(c2[this.tamCromosoma-2]))
			this.tablaConex.get(c2[this.tamCromosoma-1]).add(c2[this.tamCromosoma-2]);
		if(!this.tablaConex.get(c2[this.tamCromosoma-1]).contains(c2[0]))
			this.tablaConex.get(c2[this.tamCromosoma-1]).add(c2[0]);
	}

}
