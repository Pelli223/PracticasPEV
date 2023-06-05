package Selecciones;

public class SeleccionRuleta extends Seleccion{
	
	private double[] probAcu;
	private double[] fitnessCorr;
	
	public SeleccionRuleta (double[] fitness, int tamPoblacion, double max) {
		double uTotal = 0;
		this.fitness = fitness;
		this.tamPoblacion = tamPoblacion;
		this.prob = new double[this.tamPoblacion];
		this.probAcu = new double[this.tamPoblacion];
		this.fitnessCorr = new double[this.tamPoblacion];
		this.seleccion = new int[this.tamPoblacion];
		
		this.corrigeMinimizar(max, this.fitnessCorr);
		
		for(int j = 0; j < this.tamPoblacion; j++) {
			uTotal += this.fitnessCorr[j];
		}
		
		for(int i = 0; i < this.tamPoblacion-1; i++) {
			this.prob[i] = this.fitnessCorr[i] / uTotal;
			
			if (i == 0)
				this.probAcu[i] = this.prob[i];
			else
				this.probAcu[i] = this.prob[i] + this.probAcu[i-1];
		}
		this.probAcu[this.tamPoblacion-1] = 1.0;	
	}

	@Override
	public int[] getSeleccion() {
		// TODO Auto-generated method stub
		int seleccionados = 0;
		double x;
		while (seleccionados < this.tamPoblacion) {
			x = this.rand.nextDouble();
			for(int i = 0; i < this.tamPoblacion; i++) {
				if(this.probAcu[i] >= x ) {
					this.seleccion[seleccionados] = i;
					seleccionados++;
					break;
				}
			}
		}
		return this.seleccion;
	}

}
