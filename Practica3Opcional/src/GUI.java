import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import org.math.plot.Plot2DPanel;

import Individuo.Individuo;

public class GUI {
	
	private static AlgoritmoGenetico al;
	private static AnalisisView av;
	private static double[] medias, presionSelectiva;
	private static double[] mejoresFitness;
	private static double [] mejorFitness;
	private static double[] funcDataset;
	private static double [] mejorFunc;
	private static int tamPoblacion, numGeneraciones;
	private static int sel, cruc, mut, maxProfundidad;
	private static double probCruc, probMut, elitPorc;
	private static String Ssol;
	private static ArrayList<String> func;
	private static JCheckBox mejorSolChck = new JCheckBox("Mejor solución", true),  mejorIndvChck = new JCheckBox("Mejor Individuo", true),
			mediaChck = new JCheckBox("Aptitud media", true), presChck = new JCheckBox("Presión selectiva", true);
	private static Plot2DPanel plot = new Plot2DPanel();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("parameters selector");
		JPanel panel = new JPanel(new BorderLayout());
		JPanel solPanel = new JPanel();
		JPanel graficas = new JPanel(), checksPanel = new JPanel();
		JLabel title = new JLabel("Algoritmo genético");
		Plot2DPanel plot2 = new Plot2DPanel();
		JButton run = new JButton("Run"), analisis = new JButton("Analisis Parámetros");
		JLabel sol = new JLabel("Solucion: "), funciones = new JLabel("Problema de regresión simbólica"), tam = new JLabel("Tamaño población"), tipoCrucc = new JLabel("Tipo de cruce");
		JLabel numGen = new JLabel("Número de generaciones"), tipoSel = new JLabel("Tipo de seleccion");
		JLabel elit = new JLabel("%Elitismo"), porcCruc = new JLabel("%Cruce"), porcMut = new JLabel("%Mutacion");
		JLabel mutText = new JLabel("Tipo de mutacion") , maxProf = new JLabel("Wraps maximos"); 
		JTextField solText = new JTextField(), tamText = new JTextField("100"),numGenText = new JTextField("100");
		JTextField elitText = new JTextField("0.0"), porcCrucText = new JTextField("0.6"), porcMutText = new JTextField("0.05");
		JToolBar botBar = new JToolBar();
		JComboBox<String> selBox = new JComboBox<String>(), crucBox = new JComboBox<String>();
		JComboBox<String> mutTipo = new JComboBox<String>();
		JPanel funcionesPanel = new JPanel(new FlowLayout());
		JPanel parametros = new JPanel(new GridLayout(0, 1, 5,20));
		JScrollBar scroller = new JScrollBar(JScrollBar.HORIZONTAL);
		JSpinner profundidad = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1));
		BoundedRangeModel brm = solText.getHorizontalVisibility();
		ActionListener a = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				muestraPlot();
			}
		};
		scroller.setModel(brm);
		
		solPanel.setLayout(new BoxLayout(solPanel, BoxLayout.Y_AXIS));
		graficas.setLayout(new BoxLayout(graficas, BoxLayout.Y_AXIS));
		checksPanel.setLayout(new BoxLayout(checksPanel, BoxLayout.Y_AXIS));
		
		solPanel.add(solText);
		solPanel.add(scroller);

		selBox.addItem("Ruleta");selBox.addItem("Estocastico");selBox.addItem("Torneo_E");selBox.addItem("Torneo_P");selBox.addItem("Truncamiento");
		selBox.addItem("Restos");selBox.addItem("Ranking");
		mutTipo.addItem("Mutacion Basica");
		crucBox.addItem("Cruce Monopunto");crucBox.addItem("Cruce Uniforme");
		selBox.setMinimumSize(new Dimension(175, 30));
		selBox.setMaximumSize(new Dimension(175, 30));
		selBox.setPreferredSize(new Dimension(175, 30));
		crucBox.setMinimumSize(new Dimension(175, 30));
		crucBox.setMaximumSize(new Dimension(175, 30));
		crucBox.setPreferredSize(new Dimension(175, 30));
		solText.setEditable(false);
		solText.setMinimumSize(new Dimension(700, 30));
		solText.setMaximumSize(new Dimension(2000, 30));
		solText.setPreferredSize(new Dimension(1200, 30));
		tamText.setPreferredSize(new Dimension(200, 20));
		tamText.setMinimumSize(new Dimension(200, 20));
		tamText.setMaximumSize(new Dimension(200, 20));
		tamText.setEditable(true);
		numGenText.setPreferredSize(new Dimension(200, 20));
		numGenText.setMinimumSize(new Dimension(200, 20));
		numGenText.setMaximumSize(new Dimension(200, 20));
		numGenText.setEditable(true);
		elitText.setEditable(true);
		porcMutText.setEditable(true);
		porcCrucText.setEditable(true);
		plot.addLegend("SOUTH");
		plot.setAxisLabel(0, "Número de generaciones");
		plot.getAxis(0).setLabelPosition(.50, -.1); 
		plot.setAxisLabel(1, "Fitness");
		plot.getAxis(1).setLabelPosition(-0.15, 0.5);
		plot2.addLegend("SOUTH");
		title.setMinimumSize(new Dimension(175, 80));
		title.setMaximumSize(new Dimension(175, 80));
		title.setPreferredSize(new Dimension(175, 80));
		title.setPreferredSize(new Dimension(175, 80));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setSize(600,600);
		frame.setSize(new Dimension(2500, 2500));
		botBar.setBackground(Color.LIGHT_GRAY);
		parametros.setBackground(Color.LIGHT_GRAY);
		funcionesPanel.setBackground(Color.LIGHT_GRAY);
		panel.add(graficas, BorderLayout.CENTER);
		panel.add(checksPanel, BorderLayout.EAST);
		graficas.add(plot2);
		graficas.add(plot);
		checksPanel.add(new JLabel("Selección de datos de la gráfica de evolución"));
		checksPanel.add(mejorSolChck);
		checksPanel.add(mejorIndvChck);
		checksPanel.add(mediaChck);
		checksPanel.add(presChck);
		panel.add(botBar, BorderLayout.SOUTH);
		panel.add(funcionesPanel, BorderLayout.NORTH);
		panel.add(parametros, BorderLayout.WEST);
		funcionesPanel.add(funciones);
		botBar.add(sol);
		botBar.add(solPanel);
		botBar.add(Box.createHorizontalGlue());
		botBar.addSeparator();
		botBar.add(analisis);
		botBar.addSeparator();
		botBar.addSeparator();
		botBar.add(run);
		botBar.add(Box.createHorizontalGlue());
		parametros.add(tam);
		parametros.add(tamText);
		parametros.add(numGen);
		parametros.add(numGenText);
		parametros.add(tipoCrucc);
		parametros.add(crucBox);
		parametros.add(tipoSel);
		parametros.add(selBox);
		parametros.add(porcCruc);
		parametros.add(porcCrucText);
		parametros.add(mutText);
		parametros.add(mutTipo);
		parametros.add(porcMut);
		parametros.add(porcMutText);
		parametros.add(elit);
		parametros.add(elitText);
		parametros.add(maxProf);
		parametros.add(profundidad);
		profundidad.setMinimumSize(new Dimension(40, 40));
		profundidad.setMaximumSize(new Dimension(40, 40));
		profundidad.setPreferredSize(new Dimension(40, 40));
		panel.setVisible(true);
		run.setVisible(true);
		run.setPreferredSize(new Dimension(175, 40));
		run.setMinimumSize(new Dimension(175, 40));
		run.setMaximumSize(new Dimension(175, 40));
		analisis.setVisible(true);
		analisis.setPreferredSize(new Dimension(175, 40));
		analisis.setMinimumSize(new Dimension(175, 40));
		analisis.setMaximumSize(new Dimension(175, 40));
		mejorSolChck.addActionListener(a);
		mejorIndvChck.addActionListener(a);
		mediaChck.addActionListener(a);
		presChck.addActionListener(a);
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tamPoblacion = Integer.valueOf(tamText.getText());
				numGeneraciones = Integer.valueOf(numGenText.getText());
				probCruc = Double.valueOf(porcCrucText.getText());
				probMut = Double.valueOf(porcMutText.getText());
				elitPorc = Double.valueOf(elitText.getText());
				sel = selBox.getSelectedIndex()+1;
				cruc = crucBox.getSelectedIndex() + 1;
				maxProfundidad = (int) profundidad.getValue();
				mut = mutTipo.getSelectedIndex()+1;
				medias = new double[numGeneraciones];
				presionSelectiva = new double[numGeneraciones];
				mejorFitness = new double[numGeneraciones];
				mejoresFitness = new double[numGeneraciones];
				al = new AlgoritmoGenetico(tamPoblacion, probCruc, probMut, sel, cruc, mut, elitPorc, maxProfundidad);
				run();
				func = al.getFuncionMejor();
				Ssol = "Solución: ";
				for(int i = 0; i < func.size(); i++) {
					Ssol += func.get(i) + " ";
				}
				solText.setText(Ssol + " | Fitness: " + String.valueOf(al.getMejor()));
				muestraPlot();
				plot2.removeAllPlots();
				plot2.addLinePlot("Funcion esperada", Color.BLUE , funcDataset);
				plot2.addLinePlot("Funcion obtenida", Color.ORANGE, mejorFunc);
				plot2.setAutoBounds();
			}
		});
		analisis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tamPoblacion = Integer.valueOf(tamText.getText());
				numGeneraciones = Integer.valueOf(numGenText.getText());
				probCruc = Double.valueOf(porcCrucText.getText());
				probMut = Double.valueOf(porcMutText.getText());
				elitPorc = Double.valueOf(elitText.getText());
				sel = selBox.getSelectedIndex()+1;
				cruc = crucBox.getSelectedIndex()+1;
				mut = mutTipo.getSelectedIndex()+1;
				medias = new double[numGeneraciones];
				presionSelectiva = new double[numGeneraciones];
				mejorFitness = new double[numGeneraciones];
				mejoresFitness = new double[numGeneraciones];
				av = new AnalisisView(tamPoblacion, numGeneraciones, probCruc, probMut, sel, cruc, mut, elitPorc, maxProfundidad, frame);
				av.open();
				
			}
			
		});
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	private static void run() {
		al.analizaGeneracion();
		medias[0] = al.getMedia();
		mejoresFitness[0] = al.getMejorAc();
		mejorFitness[0] = al.getMejor();
		for(int i = 1; i < numGeneraciones; i++) {
			al.run();
			al.analizaGeneracion();
			medias[i] = al.getMedia();
			presionSelectiva[i] = al.getPresionSelectiva();
			mejoresFitness[i] = al.getMejorAc();
			mejorFitness[i] = al.getMejor();
		}
		funcDataset = Individuo.getValorDataset();
		mejorFunc = al.getValorFunc();
	}
	
	private static void muestraPlot() {
		plot.removeAllPlots();
		if(mejorSolChck.isSelected()) {
			plot.addLinePlot("Mejor Solucion", Color.BLUE , mejorFitness);
		}
		if(mejorIndvChck.isSelected()) {
			plot.addLinePlot("Mejor individuo", Color.RED, mejoresFitness);
		}
		if(mediaChck.isSelected()) {
			plot.addLinePlot("Aptitud Media", Color.GREEN, medias);
		}
		if(presChck.isSelected()) {
			plot.addLinePlot("Presion Selectiva", Color.BLACK, presionSelectiva);
		}
		plot.setAutoBounds();
	}

}
