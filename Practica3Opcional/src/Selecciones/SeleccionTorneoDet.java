package Selecciones;

public class SeleccionTorneoDet extends Seleccion{
	
	public SeleccionTorneoDet (double[] fitness, int tamPoblacion) {
		this.fitness = fitness;
		this.tamPoblacion = tamPoblacion;
		this.prob = new double[this.tamPoblacion];
		this.seleccion = new int[this.tamPoblacion];
	}

	@Override
	public int[] getSeleccion() {
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
		return this.seleccion;
	}

}
