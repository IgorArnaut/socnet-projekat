package rs.ac.uns.pmf.analysis.plotters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;

@SuppressWarnings("serial")
public class DegreePlotter<V, E> extends Plotter<V, E> {

	public DegreePlotter(Graph<V, E> graph, Decomposer<V, E> decomposer, SpearmansCorrelation correlation) {
		super(graph, decomposer, correlation);
	}

	@Override
	protected XYDataset createDataset() {
		XYSeriesCollection collection = new XYSeriesCollection();
		XYSeries series = new XYSeries("Degrees");

		for (V vertex : shellIndices.keySet()) {
			double shellIndex = shellIndices.get(vertex);
			double degree = graph.degree(vertex);
			series.add(shellIndex, degree);
		}

		collection.addSeries(series);
		return collection;
	}

	@Override
	public void plot() {
		XYDataset degreeDataset = createDataset();
		JFreeChart chart = ChartFactory.createScatterPlot("Shell indices vs. Degrees", "Shell indices", "Degrees",
				degreeDataset);
		plot(chart);
	}

	@Override
	public double getSpearmanCorrelation() {
		List<Double> shellIndexValues = new ArrayList<>();
		List<Double> degrees = new ArrayList<>();
		shellIndices.forEach((k, v) -> {
			shellIndexValues.add(1.0 * v);
			degrees.add(1.0 * graph.degree(k));
		});
		return calculateCorrelation(shellIndexValues, degrees);
	}

}