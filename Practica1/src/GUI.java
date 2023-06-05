import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.math.plot.Plot2DPanel;
import org.math.plot.PlotPanel;

public class GUI {
	
	private static AlgoritmoGenetico<?> al;
	private static double[] medias;
	private static double[] mejoresFitness;
	private static double [] mejorFitness;
	private static int tamPoblacion, numGeneraciones;
	private static int ind, sel, cruc, d;
	private static double probCruc, probMut, valorError, elitPorc ;
	private static boolean func5 = false, min = false;
	private static String Ssol;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("parameters selector");
		JPanel panel = new JPanel(new BorderLayout());
		JPanel solPanel = new JPanel();
		JLabel title = new JLabel("Algoritmo genético");
		Plot2DPanel plot = new Plot2DPanel();
		JButton run = new JButton("Run");
		JLabel sol = new JLabel("Solucion: "), funciones = new JLabel("Funciones: "), tam = new JLabel("Tamaño población"), tipoCruc = new JLabel("Tipo de cruce");
		JLabel numGen = new JLabel("Número de generaciones"), valErr = new JLabel("Valor de Error"), tipoSel = new JLabel("Tipo de seleccion");
		JLabel elit = new JLabel("%Elitismo"), porcCruc = new JLabel("%Cruce"), porcMut = new JLabel("%Mutacion"), dLabel = new JLabel("d");
		JLabel mutText = new JLabel("Tipo de mutacion");
		JTextField solText = new JTextField(), tamText = new JTextField("100"),numGenText = new JTextField("100"), valErrText = new JTextField("0.001");
		JTextField elitText = new JTextField("0.0"), porcCrucText = new JTextField("0.6"), porcMutText = new JTextField("0.05");
		JSpinner dSpin = new JSpinner(new SpinnerNumberModel(2, 2, 10, 1));
		JToolBar botBar = new JToolBar();
		JComboBox<String> func = new JComboBox<String>(), selBox = new JComboBox<String>(), crucBox = new JComboBox<String>();
		JComboBox<String> mutTipo = new JComboBox<String>();
		JPanel funcionesPanel = new JPanel(new FlowLayout());
		JPanel parametros = new JPanel(new GridLayout(0, 1, 5,20));
		JScrollBar scroller = new JScrollBar(JScrollBar.HORIZONTAL);
		BoundedRangeModel brm = solText.getHorizontalVisibility();
		scroller.setModel(brm);
		
		solPanel.setLayout(new BoxLayout(solPanel, BoxLayout.Y_AXIS));
		
		solPanel.add(solText);
		solPanel.add(scroller);

		//parametros.setLayout(new BoxLayout(parametros, BoxLayout.PAGE_AXIS));
		func.addItem("Funcion 1");func.addItem("Funcion 2");func.addItem("Funcion 3");func.addItem("Funcion 4");func.addItem("Funcion 5");
		selBox.addItem("Ruleta");selBox.addItem("Estocastico");selBox.addItem("Torneo_E");selBox.addItem("Torneo_P");selBox.addItem("Truncamiento");
		selBox.addItem("Restos");
		mutTipo.addItem("Mutacion Basica");
		crucBox.addItem("Monopunto");crucBox.addItem("Uniforme");
		func.setMinimumSize(new Dimension(475, 30));
		func.setMaximumSize(new Dimension(475, 30));
		func.setPreferredSize(new Dimension(175, 30));
		func.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(func.getSelectedIndex() == 0)
					min = false;
				else
					min=true;
				if(func.getSelectedIndex() == 4) {
					func5 = true;
					crucBox.addItem("Aritmetico");crucBox.addItem("BLX-Alfa");
					dSpin.setEnabled(true);
				}
				else if(func.getSelectedIndex() == 3) {
					func5 = false;
					if(crucBox.getItemCount() == 4) {
						crucBox.setSelectedIndex(0);
						crucBox.removeItemAt(2);crucBox.removeItemAt(2);
					}
					dSpin.setEnabled(true);
				}
				else { 
					func5 = false;
					if(crucBox.getItemCount() == 4) {
						crucBox.setSelectedIndex(0);
						crucBox.removeItemAt(2);crucBox.removeItemAt(2);
					}
					dSpin.setEnabled(false);
					dSpin.setValue(2);
				}
			}
		});
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
		valErrText.setPreferredSize(new Dimension(200, 20));
		valErrText.setMinimumSize(new Dimension(200, 20));
		valErrText.setMaximumSize(new Dimension(200, 20));
		valErrText.setEditable(true);
		elitText.setEditable(true);
		porcMutText.setEditable(true);
		porcCrucText.setEditable(true);
		plot.addLegend("SOUTH");
		plot.setAxisLabel(0, "Número de generaciones");
		plot.getAxis(0).setLabelPosition(.50, -.1); 
		plot.setAxisLabel(1, "Valor de la función");
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
		dSpin.setEnabled(false);
		funcionesPanel.add(dLabel);
		funcionesPanel.add(dSpin);
		funcionesPanel.add(funciones);
		funcionesPanel.add(func);
		botBar.add(sol);
		botBar.add(solPanel);
		botBar.add(Box.createHorizontalGlue());
		botBar.addSeparator();
		botBar.add(run);
		botBar.add(Box.createHorizontalGlue());
		parametros.add(tam);
		parametros.add(tamText);
		parametros.add(numGen);
		parametros.add(numGenText);
		parametros.add(valErr);
		parametros.add(valErrText);
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
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tamPoblacion = Integer.valueOf(tamText.getText());
				numGeneraciones = Integer.valueOf(numGenText.getText());
				valorError = Double.valueOf(valErrText.getText());
				probCruc = Double.valueOf(porcCrucText.getText());
				probMut = Double.valueOf(porcMutText.getText());
				elitPorc = Double.valueOf(elitText.getText());
				d = (int) dSpin.getValue();
				ind = func.getSelectedIndex()+1;
				sel = selBox.getSelectedIndex()+1;
				cruc = crucBox.getSelectedIndex()+1;
				medias = new double[numGeneraciones];
				mejorFitness = new double[numGeneraciones];
				mejoresFitness = new double[numGeneraciones];
				if(!func5)
					al = new AlgoritmoGenetico<Boolean>(tamPoblacion, ind, valorError, d, numGeneraciones, probCruc, probMut, sel, cruc, min, elitPorc, func5);
				else
					al = new AlgoritmoGenetico<Double>(tamPoblacion, ind, valorError, d, numGeneraciones, probCruc, probMut, sel, cruc, min, elitPorc, func5);
				run();
				Ssol = "x1: " + String.valueOf(al.getFenotipoMejor(0));
				for(int i = 2; i <= d; i++) {
					Ssol += " x" + i + ": " + String.valueOf(al.getFenotipoMejor(i-1));
				}
				solText.setText(Ssol + " sol: " + String.valueOf(al.getMejor()));
				plot.removeAllPlots();
				plot.addLinePlot("Mejor Solucion", Color.BLUE , mejorFitness);
				plot.addLinePlot("Aptitud media", Color.GREEN, medias);
				plot.addLinePlot("Mejor individuo", Color.RED, mejoresFitness);
				plot.setAutoBounds();
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
			mejoresFitness[i] = al.getMejorAc();
			mejorFitness[i] = al.getMejor();
		}
	}

}
