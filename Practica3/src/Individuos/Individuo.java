package Individuos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Individuo {
	private static HashMap<Double, Double> dataset;
	private Arbol arbol;
	private double fitness;
	
	private static final String funciones[] = { "add", "sub", "mul"};
	private static final String terminales[] = { "x", "-2", "-1", "0", "1", "2"};
	
	public Individuo(int tipoCreacion, int maxProfundidad) {
		arbol = new Arbol(funciones[new Random().nextInt(3)], maxProfundidad);
		switch(tipoCreacion){
		case 0:
				this.arbol.inicializacionCompleta(0, maxProfundidad);
				break;
		case 1:
			this.arbol.inicializacionCreciente(0, maxProfundidad);
			break;
		case 2:
			int ini = new Random().nextInt(2);
			if(ini == 0) arbol.inicializacionCreciente(0, maxProfundidad);
			else this.arbol.inicializacionCompleta(0, maxProfundidad);
			break;
		}
		this.arbol.actualizaTotalNodo();
		this.getValor();
	}
	
	public Individuo() {
		this.arbol = new Arbol("add", 5);
	}
/**el valor de y para cada valor de x se calcula utilizando la fórmula:
y = x^4 + x^3 + x^2 + x + 1*/
	public static void generaDataSet() {
		double val;
		dataset = new HashMap<Double, Double>();
		for(double i = -1.0; i <= 1.0; i = i + 0.01) {
			val = Math.pow(i, 4) + Math.pow(i, 3) + Math.pow(i, 2) + i + 1;
			dataset.put(i, val);
		}
	}
	/**Calcula el fitness del individuo*/
	private void getValor() {
		ArrayList<String> funcion = this.arbol.toArray();
		int pos[] = new int[1];
		pos[0] = 0;
		double acum = 0, y;
		double result, esperado;
		for(double x = -1.0; x <= 1.0; x += 0.01) {
			result = this.deco(pos, funcion, x);
			esperado = dataset.get(x);
			y = Math.abs((esperado-result));
			acum += Math.pow(y, 2);
			pos[0] = 0;
		}
		this.fitness = Math.sqrt(acum/200);
	}
	/**Devuelve array con los valores de las funciones para todos los valores de x */
	public double[] getValorFunc() {
		ArrayList<String> funcion = this.arbol.toArray();
		int pos[] = new int[1];
		pos[0] = 0;
		int cont = 0;
		double [] resultFunc = new double[200]; 
		double result;
		for(double x = -1.0; x <= 1.0; x += 0.01) {
			result = this.deco(pos, funcion, x);
			resultFunc[cont] = result;
			pos[0] = 0;
			cont++;
		}
		return resultFunc;
	}
	/**Devuelve array con los valores del daset*/
	public static double[] getValorDataset() {
		int cont = 0;
		double [] resultFunc = new double[200]; 
		for(double x = -1.0; x <= 1.0; x += 0.01) {
			resultFunc[cont] = dataset.get(x);
			cont++;
		}
		return resultFunc;
	}
	/**Decodificacion valor formula para un x dada*/
	private double deco(int[] pos, ArrayList<String> formula, double x) {
		double op1, op2, result = 0;
		String func;
		pos[0]++;
		
		if(formula.get(pos[0]) == "(") {
			op1 = this.deco(pos, formula, x);
		}
		else {
			op1 = this.decoTerm(formula.get(pos[0]), x);
		}
		pos[0]++;
		func = formula.get(pos[0]);
		pos[0]++;
		if(formula.get(pos[0]) == "(") {
			op2 = this.deco(pos, formula, x);
		}
		else {
			op2 = this.decoTerm(formula.get(pos[0]), x);
		}
		pos[0]++;
		result = this.decoFunc(op1, op2, func);
		
		return result;
	}
	/**Decodifica los terminales*/
	private double decoTerm(String term, double x) {
		switch(term){
		case "x":
			return x;
		case "-2":
			return -2;
		case "-1":
			return -1;
		case "0":
			return 0;
		case "1":
			return 1;
		case "2":
			return 2;
		default:
			return x;
		}
	}
	/**Decodificacion funcion, pasados dos operandos y una funcion devulve su valor */
	private double decoFunc(double op1, double op2, String func) {
		switch(func){
		case "add":
			return op1 + op2;
		case "sub":
			return op1 - op2;
		case "mul":
			return op1 * op2;
		default:
			return 0;
		}
	}
	/**Ddevuelve si el string pasado por parametro es una funcion o no*/
	public static boolean esFuncion(String v) {
		switch(v){
		case "add":
			return true;
		case "sub":
			return true;
		case "mul":
			return true;
		default:
			return false;
		}
	}
	/**Dado un indice te devuelve su valor en el array de funciones*/
	public static String getFunc(int n) {
		return funciones[n];
	}
	/**Dado un indice te devuelve su valor en el array de terminales*/
	public static String getTerm(int n) {
		return terminales[n];
	}
	/**Devuelve el arbol del individuo*/
	public Arbol getArbol() {
		return this.arbol;
	}
	/**Devuelve fitness del individuo*/
	public double getFitness() {
		this.getValor();
		return this.fitness;
	}
/**Pone como arbol el pasado*/
	public void setArbol(Arbol a) {
		// TODO Auto-generated method stub
		this.arbol = new Arbol(a);
	}
	
}
