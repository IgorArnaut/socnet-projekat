package rs.ac.uns.pmf.analysis.plotters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;

@SuppressWarnings("serial")
public class BetweennessPlotter<V, E> extends Plotter<V, E> {

	private BetweennessCentrality<V, E> centrality;

	public BetweennessPlotter(Graph<V, E> graph, Decomposer<V, E> decomposer, SpearmansCorrelation correlation) {
		super(graph, decomposer, correlation);
		this.centrality = new BetweennessCentrality<>(graph);
	}

	@Override
	protected XYDataset createDataset() {
		XYSeriesCollection collection = new XYSeriesCollection();
		XYSeries series = new XYSeries("Degrees");

		for (V vertex : shellIndices.keySet()) {
			double shellIndex = shellIndices.get(vertex);
			double bcValue = centrality.getVertexScore(vertex);
			series.add(shellIndex, bcValue);
		}

		collection.addSeries(series);
		return collection;
	}

	@Override
	public void plot() {
		XYDataset betweennessDataset = createDataset();
		JFreeChart chart = ChartFactory.createScatterPlot("Shell indices vs. Betweenness centralities", "Shell indices",
				"Betweenness centralities", betweennessDataset);
		plot(chart);
	}

	@Override
	public double getSpearmanCorrelation() {
		List<Double> shellIndexValues = new ArrayList<>();
		List<Double> centralities = new ArrayList<>();
		shellIndices.forEach((k, v) -> {
			shellIndexValues.add(1.0 * v);
			centralities.add(centrality.getVertexScore(k));
		});
		return calculateCorrelation(shellIndexValues, centralities);
	}

}
