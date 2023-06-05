
public class SeleccionTorneoProb extends Seleccion{
	
	private double p = 0.5;
	
	public SeleccionTorneoProb (double[] fitness, int tamPoblacion, boolean min) {
		this.fitness = fitness;
		this.tamPoblacion = tamPoblacion;
		this.prob = new double[this.tamPoblacion];
		this.seleccion = new int[this.tamPoblacion];
		this.min = min;
	}

	@Override
	public int[] getSeleccion() {
		if(!min) {
			for(int i = 0; i < this.tamPoblacion; i++) {
				int ind1 = this.rand.nextInt(0, this.tamPoblacion), ind2 = this.rand.nextInt(0, this.tamPoblacion), ind3 = this.rand.nextInt(0, this.tamPoblacion);
				double x = this.rand.nextDouble();
					if(this.fitness[ind1] < this.fitness[ind2])
						if(this.fitness[ind2] < this.fitness[ind3])
							this.seleccion[i] = x>p ? ind3 : ind1;
						else
							this.seleccion[i] = x>p ? ind2 : ind1;
					else
						if(this.fitness[ind1] < this.fitness[ind3])
							this.seleccion[i] = x>p ? ind3 : ind2;
						else
							this.seleccion[i] = x>p ? ind1 : ind2;
			}
		}
		else {
			for(int i = 0; i < this.tamPoblacion; i++) {
				int ind1 = this.rand.nextInt(0, this.tamPoblacion), ind2 = this.rand.nextInt(0, this.tamPoblacion), ind3 = this.rand.nextInt(0, this.tamPoblacion);
				double x = this.rand.nextDouble();
					if(this.fitness[ind1] > this.fitness[ind2])
						if(this.fitness[ind2] > this.fitness[ind3])
							this.seleccion[i] = x>p ? ind3 : ind1;
						else
							this.seleccion[i] = x>p ? ind2 : ind1;
					else
						if(this.fitness[ind1] > this.fitness[ind3])
							this.seleccion[i] = x>p ? ind3 : ind2;
						else
							this.seleccion[i] = x>p ? ind1 : ind2;
			}			
		}
		return this.seleccion;
	}

}
