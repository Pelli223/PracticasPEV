package Selecciones;
import java.util.Arrays;

public class SeleccionTrunc extends Seleccion{
	private double[] fitnessOrd;
	private double[] fitnessCorr;
	
	public SeleccionTrunc (double[] fitness, int tamPoblacion, double max) {
		this.fitness = fitness;
		this.tamPoblacion = tamPoblacion;
		this.prob = new double[this.tamPoblacion];
		this.fitnessCorr = new double[this.tamPoblacion];
		this.seleccion = new int[this.tamPoblacion];
		this.fitnessOrd = new double[this.tamPoblacion];
		
		this.corrigeMinimizar(max, fitnessCorr);
		for(int i = 0; i < this.tamPoblacion; i++)
			this.fitnessOrd[i] = this.fitnessCorr[i];
	}

	@Override
	public int[] getSeleccion() {
		int seleccionados = 0;
		Arrays.sort(this.fitnessOrd);
		for(int i = this.tamPoblacion - 1; i >= this.tamPoblacion / 2 ; i--) {
			for(int j = 0; j < this.tamPoblacion; j++) {
				if(this.fitnessOrd[i] == this.fitnessCorr[j]) {
					this.seleccion[seleccionados++] = j;
					this.seleccion[seleccionados++] = j;
					break;
				}
			}
			}
		return this.seleccion;
	}
}
