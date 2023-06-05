
public class SeleccionRestos extends Seleccion{
	
	public SeleccionRestos (double[] fitness, int tamPoblacion, boolean min) {
		double uTotal = 0;
		this.min = min;
		this.fitness = fitness;
		this.tamPoblacion = tamPoblacion;
		this.prob = new double[this.tamPoblacion];
		this.seleccion = new int[this.tamPoblacion];
		for(int j = 0; j < this.tamPoblacion; j++)
			uTotal += this.fitness[j];
		for(int i = 0; i < this.tamPoblacion; i++) {
			this.prob[i] = this.fitness[i] / uTotal;
		}
			
	}

	@Override
	public int[] getSeleccion() {
		// TODO Auto-generated method stub
		int seleccionados = 0;
		
		for(int i = 0; i < this.tamPoblacion; i++) {
			if(this.prob[i] * this.tamPoblacion >= 1) {
				this.seleccion[seleccionados] = i;
				seleccionados++;
			}
		}
		
		//El resto de individuos se selecciona por torneo
		if(!this.min) {
			for(int i = 0; i < this.tamPoblacion - seleccionados; i++) {
				int ind1 = this.rand.nextInt(0, this.tamPoblacion), ind2 = this.rand.nextInt(0, this.tamPoblacion), ind3 = this.rand.nextInt(0, this.tamPoblacion);
				if(this.fitness[ind1] < this.fitness[ind2])
					if(this.fitness[ind2] < this.fitness[ind3])
						this.seleccion[i] = ind3;
					else
						this.seleccion[i] = ind2;
				else
					if(this.fitness[ind1] < this.fitness[ind3])
						this.seleccion[i] = ind3;
					else
						this.seleccion[i] = ind1;
			}
		}
		else {
			for(int i = 0; i < this.tamPoblacion - seleccionados; i++) {
				int ind1 = this.rand.nextInt(0, this.tamPoblacion), ind2 = this.rand.nextInt(0, this.tamPoblacion), ind3 = this.rand.nextInt(0, this.tamPoblacion);
				if(this.fitness[ind1] > this.fitness[ind2])
					if(this.fitness[ind2] > this.fitness[ind3])
						this.seleccion[i] = ind3;
					else
						this.seleccion[i] = ind2;
				else
					if(this.fitness[ind1] > this.fitness[ind3])
						this.seleccion[i] = ind3;
					else
						this.seleccion[i] = ind1;
			}
		}
		return this.seleccion;
	}
}
