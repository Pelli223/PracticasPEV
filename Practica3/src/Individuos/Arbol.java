package Individuos;

import java.util.ArrayList;
import java.util.Random;

public class Arbol {
	private static final int minProfundidad = 2;
	private String valor;
	private Arbol hi, hd;
	private int totalNodos;
	private int maxProfundidad;
	private int altura;
	
	
	public Arbol(String v, int maxProfundidad) {
		this.valor = v;
		this.hi = null;
		this.hd = null;
		this.totalNodos = 1;
		this.maxProfundidad = maxProfundidad;
		this.altura = 0;
	}
	
	public Arbol(Arbol a) {
		this.valor = a.getValor();
		this.totalNodos = a.getTotalNodos();
		this.maxProfundidad = a.maxProfundidad;
		this.altura = a.altura;
		if(a.getHijoIzq() != null) {
			this.hi = new Arbol(a.getHijoIzq());
		}
		if(a.getHijoDer() != null) {
			this.hd = new Arbol(a.getHijoDer());
		}
	}
	/**devuelve el subarbol de dado su indice*/
	public Arbol at(int index) {
		return this.at(this, 0, index);
	}
	
	private Arbol at(Arbol a, int pos, int index) {
		Arbol s = null;
		if(pos >= index)
			s = a;
		else {
			if(a.getHijoIzq() != null && s == null)
				s = this.at(a.getHijoIzq(), pos++, index);
			if(a.getHijoDer() != null && s == null)
				s = this.at(a.getHijoDer(), pos+a.getHijoIzq().getTotalNodos()+1, index);
		}
		return s;
	}
	/**Convierte el arbol en un array*/
	public ArrayList<String> toArray(){
		ArrayList<String> formula = new ArrayList<String>();
		this.rellenaArrayList(formula);
		return formula;
	}
	/** Convierte el arbol en un array recorrriendo el arbol en inorden*/
	private void rellenaArrayList(ArrayList<String> formula) {
		if(!this.esHoja()) {
			formula.add("(");
			this.hi.rellenaArrayList(formula);
			formula.add(this.valor);
			this.hd.rellenaArrayList(formula);
			formula.add(")");
		}
		else {
			formula.add(this.valor);
		}
	}
	/**deveulve valor de nodo*/
	public String getValor() {
		return this.valor;
	}
	/**devuelve numero total de nodos del arbol*/
	public int getTotalNodos() {
		return this.totalNodos;
	}
	/**Devuelve hijo izquierdo*/
	public Arbol getHijoIzq() {
		return this.hi;
	}
	/**Devuelve hijo derecho*/
	public Arbol getHijoDer() {
		return this.hd;
	}
	/**Devuelve maxima profundidad del arbol*/
	public int getMaxProfundidad() {
		return this.maxProfundidad;
	}
	/**Devuleve si es hoja o no*/
	public boolean esHoja() {
		return this.hi == null && this.hd == null;
	}
	/**Pone como valor el string pasado por parametro*/
	public void setValor(String v) {
		this.valor = v;
	}
	/**Pone el como hijo izquerdo el arbol pasado por parametro*/
	public void setHijoIzq(Arbol a) {
		this.hi = a;
	}
	/**Pone el como hijo izquerdo el arbol pasado por parametro*/
	public void setHijoDer(Arbol a) {
		this.hd = a;
	}
	/**Actualiza el numero total de nodos*/
	public void actualizaTotalNodo() {
		if(this.esHoja()) {
			this.totalNodos = 0;
		}
		else {
			this.totalNodos = 0;
			if(this.hi != null) {
				this.hi.actualizaTotalNodo();
				this.totalNodos += this.hi.getTotalNodos() + 1;
			}
			if(this.hd != null) {
				this.hd.actualizaTotalNodo();
				this.totalNodos += this.hd.getTotalNodos() + 1;
			}
		}
	}
	/**Pone el nodo pasado por parametro*/
	public void setNodo(Arbol a) {
		this.valor = a.getValor();
		this.hi = a.getHijoIzq();
		this.hd = a.getHijoDer();
	}
	/**Vamos tomando nodos del conjunto de funciones hasta llegar a una máxima
		profundidad del árbol definida previamente
	 Una vez llegados a la profundidad máxima los símbolos sólo se toman del
	conjunto de símbolos terminales*/
	public void inicializacionCompleta(int pos, int maxProfundidad) {
		// TODO Auto-generated method stub
		int altura = pos;
		if (pos < maxProfundidad) {
			pos++;
			this.hi = new Arbol(Individuo.getFunc(new Random().nextInt(3)), maxProfundidad);
			this.hi.altura = altura;
			this.hi.inicializacionCompleta(pos, maxProfundidad);
			this.hd = new Arbol(Individuo.getFunc(new Random().nextInt(3)), maxProfundidad);
			this.hd.altura = altura;
			this.hd.inicializacionCompleta(pos, maxProfundidad);
		}
		else if(pos == maxProfundidad) {
			this.hi = new Arbol(Individuo.getTerm(new Random().nextInt(6)), maxProfundidad);
			this.hi.altura = altura;
			this.hd = new Arbol(Individuo.getTerm(new Random().nextInt(6)), maxProfundidad);
			this.hd.altura = altura;
		}
	}
/** Vamos tomando nodos del conjunto completo (funciones y terminales) hasta
llegar al límite de profundidad especificado previamente
Una vez llegados a la profundidad máxima los símbolos sólo se toman del
	conjunto de símbolos terminales*/
	public void inicializacionCreciente(int pos, int maxProfundidad) {
		// TODO Auto-generated method stub
		int altura = pos;
		boolean terminal;
		if (pos < minProfundidad) {
			pos++;
			this.hi = new Arbol(Individuo.getFunc(new Random().nextInt(3)), maxProfundidad);
			this.hi.altura = altura;
			this.hi.inicializacionCreciente(pos, maxProfundidad);
			this.hd = new Arbol(Individuo.getFunc(new Random().nextInt(3)), maxProfundidad);
			this.hd.altura = altura;
			this.hd.inicializacionCreciente(pos, maxProfundidad);
		}
		else if(pos < maxProfundidad) {
			terminal = new Random().nextBoolean();
			pos++;
			if(terminal) {
				this.hi = new Arbol(Individuo.getTerm(new Random().nextInt(6)), maxProfundidad);
			}
			else {
				this.hi = new Arbol(Individuo.getFunc(new Random().nextInt(3)), maxProfundidad);
				this.hi.inicializacionCreciente(pos, maxProfundidad);
			}
			this.hi.altura = altura;
			terminal = new Random().nextBoolean();
			if(terminal) {
				this.hd = new Arbol(Individuo.getTerm(new Random().nextInt(6)), maxProfundidad);
			}
			else {
				this.hd = new Arbol(Individuo.getFunc(new Random().nextInt(3)), maxProfundidad);
				this.hd.inicializacionCreciente(pos, maxProfundidad);
			}
			this.hd.altura = altura;
		}
		else if(pos == maxProfundidad) {
			this.hi = new Arbol(Individuo.getTerm(new Random().nextInt(6)), maxProfundidad);
			this.hd = new Arbol(Individuo.getTerm(new Random().nextInt(6)), maxProfundidad);
			this.hi.altura = altura;
			this.hd.altura = altura;
		}
	}
/**Devuelve altura del arbol*/
	public int getAltura() {
		// TODO Auto-generated method stub
		return this.altura;
	}

	public void setAlturas(int alturaPadre) {
		if(this.hi != null) {
			this.hi.altura = alturaPadre+1;
			this.hi.setAlturas(this.hi.altura);
		}
		if(this.hd != null) {
			this.hd.altura = alturaPadre+1;
			this.hd.setAlturas(this.hd.altura);
		}
	}
}
