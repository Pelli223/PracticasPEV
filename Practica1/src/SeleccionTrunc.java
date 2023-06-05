import java.util.Arrays;

public class SeleccionTrunc extends Seleccion{
	private double[] fitnessCorr, fitnessOrd;
	
	public SeleccionTrunc (double[] fitness, int tamPoblacion, boolean min, double mejor) {
		this.min = min;
		this.fitness = fitness;
		this.tamPoblacion = tamPoblacion;
		this.prob = new double[this.tamPoblacion];
		this.seleccion = new int[this.tamPoblacion];
		this.fitnessCorr = new double[this.tamPoblacion];
		this.fitnessOrd = new double[this.tamPoblacion];
		if(min)
			this.corrigeMinimizar(mejor);
		else
			this.corrigeMaximizar(mejor);;
			
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
	
	private void corrigeMinimizar(double max) {
		for(int i = 0; i < this.tamPoblacion; i++) {
			this.fitnessCorr[i] = (1.05 * max) - this.fitness[i];
			this.fitnessOrd[i] = this.fitnessCorr[i];
		}
	}
	
	private void corrigeMaximizar(double min) {
		for(int i = 0; i < this.tamPoblacion; i++) {
			this.fitnessCorr[i] = this.fitness[i] + Math.abs(min);
			this.fitnessOrd[i] = this.fitnessCorr[i];
		}
	}
	
}
