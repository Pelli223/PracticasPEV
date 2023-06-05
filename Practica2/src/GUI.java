import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.math.plot.Plot2DPanel;

public class GUI {
	
	private static AlgoritmoGenetico al;
	private static AnalisisView av;
	private static double[] medias, presionSelectiva;
	private static double[] mejoresFitness;
	private static double [] mejorFitness;
	//private static double [] mejorFitnessAnalisis;
	private static int tamPoblacion, numGeneraciones;
	private static int sel, cruc, mut;
	private static double probCruc, probMut, elitPorc ;
	private static String Ssol;
	private static String[] ciudades = {"Albacete", "Alicante", "Almería", "Ávila", "Badajoz", "Barcelona", "Bilbao", "Burgos", "Cáceres",
			"Cádiz", "Castellón", "Ciudad Real", "Córdoba", "A Coruña", "Cuenca", "Gerona", "Granada", "Guadalajara", "Huelva", "Huesca",
			"Jaén", "León", "Lérida", "Logroño", "Lugo", "Madrid", "Málaga", "Murcia"};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("parameters selector");
		JPanel panel = new JPanel(new BorderLayout());
		JPanel solPanel = new JPanel();
		JLabel title = new JLabel("Algoritmo genético");
		Plot2DPanel plot = new Plot2DPanel();
		JButton run = new JButton("Run"), analisis = new JButton("Analisis Parámetros");
		JLabel sol = new JLabel("Solucion: "), funciones = new JLabel("Problema del viajante"), tam = new JLabel("Tamaño población"), tipoCruc = new JLabel("Tipo de cruce");
		JLabel numGen = new JLabel("Número de generaciones"), tipoSel = new JLabel("Tipo de seleccion");
		JLabel elit = new JLabel("%Elitismo"), porcCruc = new JLabel("%Cruce"), porcMut = new JLabel("%Mutacion");
		JLabel mutText = new JLabel("Tipo de mutacion");
		JTextField solText = new JTextField(), tamText = new JTextField("100"),numGenText = new JTextField("100");
		JTextField elitText = new JTextField("0.0"), porcCrucText = new JTextField("0.6"), porcMutText = new JTextField("0.05");
		JToolBar botBar = new JToolBar();
		JComboBox<String> selBox = new JComboBox<String>(), crucBox = new JComboBox<String>();
		JComboBox<String> mutTipo = new JComboBox<String>();
		JPanel funcionesPanel = new JPanel(new FlowLayout());
		JPanel parametros = new JPanel(new GridLayout(0, 1, 5,20));
		JScrollBar scroller = new JScrollBar(JScrollBar.HORIZONTAL);
		BoundedRangeModel brm = solText.getHorizontalVisibility();
		scroller.setModel(brm);
		
		solPanel.setLayout(new BoxLayout(solPanel, BoxLayout.Y_AXIS));
		
		solPanel.add(solText);
		solPanel.add(scroller);

		selBox.addItem("Ruleta");selBox.addItem("Estocastico");selBox.addItem("Torneo_E");selBox.addItem("Torneo_P");selBox.addItem("Truncamiento");
		selBox.addItem("Restos");selBox.addItem("Ranking");
		mutTipo.addItem("Mutacion Inserción"); mutTipo.addItem("Mutacion Intercambio"); mutTipo.addItem("Mutacion Inversión");
		mutTipo.addItem("Mutacion Heurística");mutTipo.addItem("Mutacion Propia");
		crucBox.addItem("Emparejamiento parcial");crucBox.addItem("Orden");crucBox.addItem("Posiciones prioritarias");
		crucBox.addItem("Orden prioritario");crucBox.addItem("Ciclos");crucBox.addItem("Recombinación de rutas");
		crucBox.addItem("Codificación Ordinal");crucBox.addItem("Cruce Propio");
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
		plot.setAxisLabel(1, "Distancia total");
		plot.getAxis(1).setLabelPosition(-0.15, 0.5);
		
		title.setMinimumSize(new Dimension(175, 80));
		title.setMaximumSize(new Dimension(175, 80));
		title.setPreferredSize(new Dimension(175, 80));
		title.setPreferredSize(new Dimension(175, 80));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.setSize(new Dimension(2000, 2000));
		botBar.setBackground(Color.LIGHT_GRAY);
		parametros.setBackground(Color.LIGHT_GRAY);
		funcionesPanel.setBackground(Color.LIGHT_GRAY);
		panel.add(plot, BorderLayout.CENTER);
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
		botBar.add(run);
		botBar.add(Box.createHorizontalGlue());
		parametros.add(tam);
		parametros.add(tamText);
		parametros.add(numGen);
		parametros.add(numGenText);
		parametros.add(tipoSel);
		parametros.add(selBox);
		parametros.add(tipoCruc);
		parametros.add(crucBox);
		parametros.add(porcCruc);
		parametros.add(porcCrucText);
		parametros.add(mutText);
		parametros.add(mutTipo);
		parametros.add(porcMut);
		parametros.add(porcMutText);
		parametros.add(elit);
		parametros.add(elitText);
		panel.setVisible(true);
		run.setVisible(true);
		run.setPreferredSize(new Dimension(175, 40));
		run.setMinimumSize(new Dimension(175, 40));
		run.setMaximumSize(new Dimension(175, 40));
		analisis.setVisible(true);
		analisis.setPreferredSize(new Dimension(175, 40));
		analisis.setMinimumSize(new Dimension(175, 40));
		analisis.setMaximumSize(new Dimension(175, 40));
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				al = new AlgoritmoGenetico(tamPoblacion, probCruc, probMut, sel, cruc, mut, elitPorc);
				run();
				Ssol = "Solución: " + ciudades[al.getFenotipoMejor(0)];
				for(int i = 2; i <= 27; i++) {
					Ssol += ", " + ciudades[al.getFenotipoMejor(i-1)];
				}
				solText.setText(Ssol + " | Distancia total: " + String.valueOf(al.getMejor()));
				plot.removeAllPlots();
				plot.addLinePlot("Mejor Solucion", Color.BLUE , mejorFitness);
				plot.addLinePlot("Aptitud Media", Color.GREEN, medias);
				plot.addLinePlot("Presion Selectiva", Color.BLACK, presionSelectiva);
				plot.addLinePlot("Mejor individuo", Color.RED, mejoresFitness);
				plot.setAutoBounds();
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
				av = new AnalisisView(tamPoblacion, numGeneraciones, probCruc, probMut, sel, cruc, mut, elitPorc, frame);
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
	}

}
