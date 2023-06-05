
public class SeleccionEstoUniver extends Seleccion{
	
	private double dist;
	private double[] probAcu;
	private double[] fitnessCorr;
	
	public SeleccionEstoUniver (double[] fitness, int tamPoblacion, boolean min, double mejor) {
		double uTotal = 0;
		this.min = min;
		this.fitness = fitness;
		this.tamPoblacion = tamPoblacion;
		this.prob = new double[this.tamPoblacion];
		this.probAcu = new double[this.tamPoblacion];
		this.seleccion = new int[this.tamPoblacion];
		this.dist = 1.0/this.tamPoblacion;
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
		int i = 0;
		int seleccionados = 0;
		double x = this.rand.nextDouble(0, this.dist);
		double a = (x + (seleccionados+1) - 1)/this.tamPoblacion;
		while (seleccionados < this.tamPoblacion) {
			if(this.probAcu[i] >= a) {
				this.seleccion[seleccionados] = i;
				i = 0;
				seleccionados++;
				a = (x + (seleccionados+1) - 1)/this.tamPoblacion;
			}
			i++;
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
