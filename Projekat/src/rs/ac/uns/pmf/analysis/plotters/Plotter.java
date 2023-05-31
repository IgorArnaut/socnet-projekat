package rs.ac.uns.pmf.analysis.plotters;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

import edu.uci.ics.jung.graph.Graph;

@SuppressWarnings("serial")
public abstract class Plotter<V, E> extends JFrame {

	protected Graph<V, E> graph;

	public Plotter(Graph<V, E> graph) {
		this.graph = graph;
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

	protected abstract XYDataset createDataset();

	public abstract void plot();

}
