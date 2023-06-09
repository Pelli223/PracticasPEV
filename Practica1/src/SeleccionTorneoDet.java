
public class SeleccionTorneoDet extends Seleccion{
	
	public SeleccionTorneoDet (double[] fitness, int tamPoblacion, boolean min) {
		this.fitness = fitness;
		this.min = min;
		this.tamPoblacion = tamPoblacion;
		this.prob = new double[this.tamPoblacion];
		this.seleccion = new int[this.tamPoblacion];
	}

	@Override
	public int[] getSeleccion() {
		if(!this.min) {
			for(int i = 0; i < this.tamPoblacion; i++) {
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
			for(int i = 0; i < this.tamPoblacion; i++) {
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
