package Selecciones;

import java.util.Arrays;
import java.util.Collections;

public class SeleccionRanking extends Seleccion{
	
	static private final double beta = 1.5;
	private double[] probAcu;
	private double[] fitnessCorr;
	private Double[] fitnessOrd;
	
	public SeleccionRanking (double[] fitness, int tamPoblacion, double max) {
		this.fitness = fitness;
		this.tamPoblacion = tamPoblacion;
		this.prob = new double[this.tamPoblacion];
		this.probAcu = new double[this.tamPoblacion];
		this.fitnessCorr = new double[this.tamPoblacion];
		this.fitnessOrd = new Double[this.tamPoblacion];
		this.seleccion = new int[this.tamPoblacion];
		
		this.corrigeMinimizar(max, fitnessCorr);
		
		for(int i = 0; i < this.tamPoblacion; i++)
			this.fitnessOrd[i] = this.fitnessCorr[i];
		
		Arrays.sort(this.fitnessOrd, Collections.reverseOrder());
		
		for(int i = 0; i < this.tamPoblacion; i++) {
			double probOfIth = (double)i/(this.tamPoblacion-1);
			probOfIth *= 2*(beta-1);
			probOfIth = beta - probOfIth;
			probOfIth = (double)probOfIth * ((double)1/this.tamPoblacion);
			this.prob[i] = probOfIth;
			if (i == 0)
				this.probAcu[i] = this.prob[i];
			else
				this.probAcu[i] = this.prob[i] + this.probAcu[i-1];
		}	
	}

	@Override
	public int[] getSeleccion() {
		// TODO Auto-generated method stub
		int seleccionados = 0;
		double x;
		int posSel = 0;
		while (seleccionados < this.tamPoblacion) {
			x = this.rand.nextDouble();
			for(int i = 0; i < this.tamPoblacion; i++) {
				if(this.probAcu[i] >= x ) {
					for(int j = 0; j < this.tamPoblacion; j++) {
						if(this.fitnessOrd[i] == this.fitnessCorr[j]) {
							posSel = j;
							break;
						}
					}
					this.seleccion[seleccionados] = posSel;
					seleccionados++;
					break;
				}
			}
		}
		return this.seleccion;
	}

}
