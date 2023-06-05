
public class SeleccionRuleta extends Seleccion{
	
	private double[] probAcu;
	private double[] fitnessCorr;
	
	public SeleccionRuleta (double[] fitness, int tamPoblacion, boolean min, double mejor) {
		double uTotal = 0;
		this.min = min;
		this.fitness = fitness;
		this.tamPoblacion = tamPoblacion;
		this.prob = new double[this.tamPoblacion];
		this.probAcu = new double[this.tamPoblacion];
		this.seleccion = new int[this.tamPoblacion];
		this.fitnessCorr = new double[this.tamPoblacion];
		if(min)
			this.corrigeMinimizar(mejor);
		else
			this.corrigeMaximizar(mejor);
		
		for(int j = 0; j < this.tamPoblacion; j++)
			uTotal += this.fitnessCorr[j];
		
		for(int i = 0; i < this.tamPoblacion; i++) {
			this.prob[i] = this.fitnessCorr[i] / uTotal;
			
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
	
	private void corrigeMinimizar(double max) {
		for(int i = 0; i < this.tamPoblacion; i++)
			this.fitnessCorr[i] = (1.05 * max) - this.fitness[i];
	}
	
	private void corrigeMaximizar(double min) {
		for(int i = 0; i < this.tamPoblacion; i++)
			this.fitnessCorr[i] = this.fitness[i] + Math.abs(min);
	}

}
