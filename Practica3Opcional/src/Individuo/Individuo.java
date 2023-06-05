package Individuo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Individuo {
	private static HashMap<Double, Double> dataset;
	private int wraps, maxWraps;
	private static final int tamCromosoma = 8;
	private int[] cromosoma;
	private double fitness;
	
	/*Gramática usada
	 	<start> ::= <expr> <op> <expr> | <expr>
		<expr> ::= <term> <op> <term> | (<term> <op> <term>) |
		<digit> <op> <expr> | (<digit> <op> <expr>)
		<op> ::= add | sub | mul
		<term> ::= x
		<digit> ::= -2 | -1 | 0 | 1 | 2
	 */
	
	public Individuo(int maxWraps, boolean nuevo) {
		this.maxWraps = maxWraps;
		this.cromosoma = new int[tamCromosoma];
	}
	
	public Individuo(int maxWraps) {
		this.maxWraps = maxWraps;
		this.cromosoma = new int[tamCromosoma];
		for(int i = 0; i < tamCromosoma; i++) {
			this.cromosoma[i] = new Random().nextInt(256);
		}
	}
	
	public static void generaDataSet() {
		double val;
		dataset = new HashMap<Double, Double>();
		for(double i = -1.0; i <= 1.0; i = i + 0.01) {
			val = Math.pow(i, 4) + Math.pow(i, 3) + Math.pow(i, 2) + i + 1;
			dataset.put(i, val);
		}
	}
	
	private void getValor() {
		ArrayList<String> funcion = this.decoFormula();
		Individuo aux;
		int pos[] = new int[1];
		pos[0] = 0;
		double acum = 0, y;
		double result, esperado;
		while (funcion == null) {
			aux = new Individuo(this.maxWraps);
			this.setCromosoma(aux.getCromosoma());
			funcion = this.decoFormula();
		}
		//System.out.println(funcion);
		for(double x = -1.0; x <= 1.0; x += 0.01) {
			result = this.deco(pos, funcion, x);
			esperado = dataset.get(x);
			y = Math.abs((esperado-result));
			acum += Math.pow(y, 2);
			pos[0] = 0;
		}
		this.fitness = Math.sqrt(acum/200);
	}
	
	public double[] getValorFunc() {
		ArrayList<String> funcion = this.decoFormula();
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
	
	public static double[] getValorDataset() {
		int cont = 0;
		double [] resultFunc = new double[200]; 
		for(double x = -1.0; x <= 1.0; x += 0.01) {
			resultFunc[cont] = dataset.get(x);
			cont++;
		}
		return resultFunc;
	}
	
	private double deco(int[] pos, ArrayList<String> formula, double x) {
		double op1 = 0, op2 = 0, op3 = 0;
		String func, func2 = null;
		
		if(formula.get(pos[0]) == "(") {
			pos[0]++;
			op1 = this.deco(pos, formula, x);
		}
		else {
			op1 = this.getTerm(formula.get(pos[0]), x);
			pos[0]++;
		}
		if(pos[0] < formula.size()) {
			func = formula.get(pos[0]);
			pos[0]++;
			if(formula.get(pos[0]) == "(") {
				pos[0]++;
				op2 = this.deco(pos, formula, x);
			}
			else {
				op2 = this.getTerm(formula.get(pos[0]), x);
				pos[0]++;
			}
			while(pos[0] < formula.size()-2 && formula.get(pos[0]) != ")") {
				func2 = formula.get(pos[0]);
				pos[0]++;
				if(formula.get(pos[0]) == "(") {
					pos[0]++;
					op3 = this.deco(pos, formula, x);
				}
				else {
					op3 = this.getTerm(formula.get(pos[0]), x);
					pos[0]++;
				}
				if(func2 == "mul")
					op2 = this.decoFunc(op2, op3, func2);
				else {
					op1 = this.decoFunc(op1, op2, func);
					op2 = op3;
					func = func2;
				}
			}
			op1 = this.decoFunc(op1, op2, func);
			pos[0]++;
		}
		
		return op1;
	}
	
	
	public ArrayList<String> decoFormula(){
		int pos = 0, i = 0;
		ArrayList<String> func;
		func = this.decoStart(this.cromosoma[pos]);
		pos++;
		this.wraps = 0;
		while(i < func.size() && this.wraps <= this.maxWraps) {
			if(func.get(i) == "expr") {
				func = this.decoExpr(this.cromosoma[pos], i, func);
				i = 0;
				pos++;
				if(pos == tamCromosoma) {
					pos = 0;
					this.wraps++;
					if(this.wraps >= this.maxWraps) {
						return null;
					}
				}
			}
			else
				i++;
		}
		for(int j = 0; j < func.size(); j++) {
			if(this.wraps >= this.maxWraps) {
				
				return null;
			}
			if(func.get(j) == "term") {
				func.set(j, this.decoTerm());
				pos++;
			}
			else if(func.get(j) == "digit") {
				func.set(j, this.decoDigit(this.cromosoma[pos]));
				pos++;
			}
			else if(func.get(j) == "op") {
				func.set(j, this.decoOp(this.cromosoma[pos]));
				pos++;
			}
			if(pos == tamCromosoma) {
				pos = 0;
				this.wraps++;
			}
		}
		
		return func;
	}
	
	private ArrayList<String> decoStart(int c){
		ArrayList<String> deco = new ArrayList<String>();
		int n = c%2;
		switch(n) {
		case 0:
			deco.add("expr");deco.add("op");deco.add("expr");
			break;
		case 1:
			deco.add("expr");
			break;
		default:
			deco.add("expr"); deco.add("op");deco.add("expr");
			break;
		}
		return deco;
	}
	
	private ArrayList<String> decoExpr(int c, int pos, ArrayList<String> func){
		ArrayList<String> deco = new ArrayList<String>();
		int n = c%4;
		for(int i = 0; i < pos; i++)
			deco.add(func.get(i));
		switch(n) {
		case 0:
			deco.add("term");deco.add("op");deco.add("term");
			break;
		case 1:
			deco.add("("); deco.add("term");deco.add("op");deco.add("term");deco.add(")");
			break;
		case 2:
			deco.add("digit");deco.add("op");deco.add("expr");
			break;
		case 3:
			deco.add("("); deco.add("digit");deco.add("op");deco.add("expr");deco.add(")");
			break;
		default:
			deco.add("term"); deco.add("op");deco.add("term");
			break;
		}
		for(int i = pos + 1; i < func.size(); i++)
			deco.add(func.get(i));
		
		return deco;
	}
	
	private String decoOp(int c){
		int n = c%3;
		switch(n) {
		case 0:
			return "add";
		case 1:
			return "sub";
		case 2:
			return "mul";
		default:
			return "add";
		}
	}
	
	private String decoTerm() {
		return "x";
	}
	
	private String decoDigit(int c){
		int n = c%5;
		switch(n) {
		case 0:
			return "-2";
		case 1:
			return "-1";
		case 2:
			return "0";
		case 3:
			return "1";
		case 4:
			return "2";
		default:
			return "-1";
		}
	}
	
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
	
	private double getTerm(String term, double x) {
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
	
	public int[] getCromosoma() {
		return this.cromosoma;
	}
	
	public double getFitness() {
		this.getValor();
		return this.fitness;
	}
	
	public void setCromosoma(int [] c) {
		for(int i = 0; i < tamCromosoma; i++) {
			this.cromosoma[i] = c[i];
		}
	}
	
	public static int getTamCromosoma() {
		return tamCromosoma;
	}

}
