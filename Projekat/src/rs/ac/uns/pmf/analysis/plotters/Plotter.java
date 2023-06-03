package rs.ac.uns.pmf.analysis.plotters;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;

@SuppressWarnings("serial")
public abstract class Plotter<V, E> extends JFrame {

	protected Graph<V, E> graph;
	protected Map<V, Integer> shellIndices;
	protected SpearmansCorrelation correlation;

	public Plotter(Graph<V, E> graph, Decomposer<V, E> decomposer, SpearmansCorrelation correlation) {
		this.graph = graph;
		this.shellIndices = decomposer.decompose(graph);
		this.correlation = correlation;
	}

	protected void plot(JFreeChart chart) {
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);

		ChartPanel panel = new ChartPanel(chart);
		setContentPane(panel);
		setSize(800, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	protected double calculateCorrelation(List<Double> list1, List<Double> list2) {
		double[] array1 = list1.stream().mapToDouble(d -> d).toArray();
		double[] array2 = list2.stream().mapToDouble(d -> d).toArray();
		return correlation.correlation(array1, array2);
	}

	protected abstract XYDataset createDataset();

	public abstract void plot();

	public abstract double getSpearmanCorrelation();

}
