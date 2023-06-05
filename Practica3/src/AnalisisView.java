import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoundedRangeModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import org.math.plot.Plot2DPanel;

public class AnalisisView extends JDialog{

	private static final long serialVersionUID = -840968650806619183L;
	private static AlgoritmoGenetico al;
	private static Plot2DPanel plot = new Plot2DPanel();
	private JPanel panel, topPanel, solPanel = new JPanel();
	private static double [] mejorFitnessAnalisis;
	private static JComboBox<String> TipoAnalisis = new JComboBox<String>();
	private static JSpinner min, max;
	private JLabel analisis, minLabel, maxLabel;
	private JToolBar toolBar;
	private JButton run;
	private JScrollBar scroller = new JScrollBar(JScrollBar.HORIZONTAL);
	private static JTextField solText = new JTextField();
	private static double sum, probCruc, probMut, elit;
	private static int tamPob, maxGen, tipSel, tipCruc, tipMut, maxProfundidad;
	private static boolean controlBloating;
	
	public AnalisisView(int tamPob, int maxGen, double probCruc, double probMut,int tipSel, int tipCruc, int tipMut, double elit, 
		int maxProfundidad, boolean controlBloating ,JFrame parent) {
		super(parent, true);
		this.setTitle("Panel de análisis");
		this.panel = new JPanel(new BorderLayout());
		this.topPanel = new JPanel(new FlowLayout());
		BoundedRangeModel brm = solText.getHorizontalVisibility();
		scroller.setModel(brm);
		solPanel.add(solText);
		solPanel.add(scroller);
		solPanel.setLayout(new BoxLayout(solPanel, BoxLayout.Y_AXIS));
		plot = new Plot2DPanel();
		plot.addLegend("SOUTH");
		plot.setAxisLabel(0, "Número de cruces distinto %");
		plot.getAxis(0).setLabelPosition(.50, -.1); 
		plot.setAxisLabel(1, "Fitness total");
		plot.getAxis(1).setLabelPosition(-0.15, 0.5);
		solText.setEditable(false);
		solText.setMinimumSize(new Dimension(200, 30));
		solText.setMaximumSize(new Dimension(200, 30));
		solText.setPreferredSize(new Dimension(200, 30));
		this.analisis = new JLabel("Parámetro a Analizar");
		this.minLabel = new JLabel("Mínimo");
		this.maxLabel = new JLabel("Máximo");
		TipoAnalisis = new JComboBox<String>();
		this.run = new JButton("Run Analisis");
		this.run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				run();
			}
		});
		this.toolBar = new JToolBar();
		TipoAnalisis.addItem("Probabilidad de Cruce");
		TipoAnalisis.addItem("Probabilidad de Mutación");
		TipoAnalisis.addItem("Tamaño de población");
		TipoAnalisis.addItem("% de elitismo");
		TipoAnalisis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(TipoAnalisis.getSelectedIndex() == 0) {
					min.setModel(new SpinnerNumberModel(0.6, 0.0, 1.0, 0.05));
					max.setModel(new SpinnerNumberModel(1.0, 0.0, 1.0, 0.05));
					sum = 0.05;
					plot.setAxisLabel(0, "Número de cruces distinto %");
				}
				else if(TipoAnalisis.getSelectedIndex() == 1) {
					min.setModel(new SpinnerNumberModel(0.05, 0.0, 1.0, 0.05));
					max.setModel(new SpinnerNumberModel(0.2, 0.0, 1.0, 0.05));
					sum = 0.05;
					plot.setAxisLabel(0, "Número de mutaciones distinto %");
				}	
				else if(TipoAnalisis.getSelectedIndex() == 2) {
					min.setModel(new SpinnerNumberModel(100.0, 10.0, 1000.0, 5.0));
					max.setModel(new SpinnerNumberModel(500.0, 10.0, 1000.0, 5.0));	
					sum = 5;
					plot.setAxisLabel(0, "Número de cruces distinto %");
				}
				else if(TipoAnalisis.getSelectedIndex() == 3) {
					min.setModel(new SpinnerNumberModel(0.0, 0.0, 1.0, 0.01));
					max.setModel(new SpinnerNumberModel(0.02, 0.0, 1.0, 0.01));	
					sum = 0.01;
					plot.setAxisLabel(0, "Número de cruces distinto %");
				}
			}
		});
		min = new JSpinner(new SpinnerNumberModel(0.6, 0.0, 1.0, 0.05));
		max = new JSpinner(new SpinnerNumberModel(1.0, 0.0, 1.0, 0.05));
		this.panel.add(this.topPanel, BorderLayout.NORTH);
		this.panel.add(this.toolBar, BorderLayout.SOUTH);
		this.panel.add(plot, BorderLayout.CENTER);
		this.topPanel.add(this.analisis);
		this.topPanel.add(TipoAnalisis);
		this.toolBar.add(this.minLabel);
		this.toolBar.add(min);
		this.toolBar.add(Box.createHorizontalGlue());
		this.toolBar.add(this.maxLabel);
		this.toolBar.add(max);
		this.toolBar.add(Box.createHorizontalGlue());
		this.toolBar.add(new JLabel("Resultado:"));
		this.toolBar.add(solPanel);
		this.toolBar.add(Box.createHorizontalGlue());
		this.toolBar.add(this.run);
		this.setPreferredSize(new Dimension(700, 500));
		this.setMinimumSize(new Dimension(700, 500));
		this.setMaximumSize(new Dimension(700, 500));
		this.setContentPane(panel);
		this.sum = 0.05;
		this.tamPob = tamPob;
		this.maxGen = maxGen;
		this.probCruc = probCruc;
		this.probMut = probMut;
		this.tipSel = tipSel;
		this.tipCruc = tipCruc;
		this.tipMut = tipMut;
		this.elit = elit;
		this.maxProfundidad = maxProfundidad;
		this.controlBloating = controlBloating;
	}

	public void open() {
		// TODO Auto-generated method stub
		if(getParent() != null)
			setLocation(this.getParent().getLocation().x + this.getParent().getWidth() / 2 - this.getWidth() /2,
					this.getParent().getLocation().y + this.getParent().getHeight() / 2 - this.getHeight()/2);
		pack();
		setVisible(true);
	}
	
	private static void run() {
		double minVal, maxVal;
		minVal = (double) min.getValue();
		maxVal = (double) max.getValue();
		double mejorVal = minVal;
		double mejorFitness = 1000;
		boolean fin = false;
		int cont = 0;
		
		List<Double> results = new ArrayList<Double>();

		if(minVal < maxVal) {
			while(!fin) {
				if(TipoAnalisis.getSelectedIndex() == 0) 
					al = new AlgoritmoGenetico(tamPob, minVal, probMut, tipSel, tipCruc, tipMut, elit, maxProfundidad, controlBloating);
				else if(TipoAnalisis.getSelectedIndex() == 1) 
					al = new AlgoritmoGenetico(tamPob, probCruc, minVal, tipSel, tipCruc, tipMut, elit, maxProfundidad, controlBloating);
				else if(TipoAnalisis.getSelectedIndex() == 2) 
					al = new AlgoritmoGenetico((int) minVal, probCruc, probMut, tipSel, tipCruc, tipMut, elit, maxProfundidad, controlBloating);
				else if(TipoAnalisis.getSelectedIndex() == 3) 
					al = new AlgoritmoGenetico(tamPob, probCruc, probMut, tipSel, tipCruc, tipMut, minVal, maxProfundidad, controlBloating);
				if((minVal + sum) > maxVal && minVal < maxVal) {
					minVal = maxVal;
				}	
				else if(minVal >= maxVal)
					fin = true;
				else 
					minVal += sum;
				
				al.analizaGeneracion();
				for(int i = 1; i < maxGen; i++) {
					al.run();
					al.analizaGeneracion();
				}
				if(al.getMejor() < mejorFitness) {
					mejorFitness = al.getMejor();
					mejorVal = minVal;
				}
				results.add(al.getMejor());
				cont++;
			}
		}
		mejorFitnessAnalisis = new double[cont];
		for(int i = 0; i < cont; i++) {
			mejorFitnessAnalisis[i] = results.get(i);
			//System.out.println(mejorFitnessAnalisis[i]);
			
		}
		mejorVal = Math.round(mejorVal * 100);
		mejorVal = mejorVal/100;
		String s = "Mejor fitness: "+ mejorFitness + " " + "Valor: " + mejorVal;
		solText.setText(s);
		plot.removeAllPlots();
		plot.addLinePlot("Mejores fitness", Color.RED, mejorFitnessAnalisis);
	}
}
