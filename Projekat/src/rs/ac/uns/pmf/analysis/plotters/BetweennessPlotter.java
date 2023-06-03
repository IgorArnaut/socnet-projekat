package rs.ac.uns.pmf.analysis.plotters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.graph.Graph;

@SuppressWarnings("serial")
public class BetweennessPlotter<V, E> extends Plotter<V, E> {

	private Map<V, Integer> shellIndices;
	private BetweennessCentrality<V, E> centrality;
	private SpearmansCorrelation correlation = new SpearmansCorrelation();

	public BetweennessPlotter(Graph<V, E> graph, Map<V, Integer> shellIndices) {
		super(graph);
		this.shellIndices = shellIndices;
		this.centrality = new BetweennessCentrality<>(graph);
	}

	@Override
	protected XYDataset createDataset() {
		XYSeriesCollection collection = new XYSeriesCollection();
		XYSeries series = new XYSeries("Betweenness centralities");

		for (V vertex : shellIndices.keySet()) {
			double x = shellIndices.get(vertex);
			double y = centrality.getVertexScore(vertex);
			series.add(x, y);
		}

		collection.addSeries(series);
		return collection;
	}

	@Override
	public void plot() {
		XYDataset dataset = createDataset();
		JFreeChart chart = ChartFactory.createScatterPlot("Shell indices vs. Betweenness centralities", "Shell indices",
				"Betweenness centralities", dataset);
		plot(chart);
	}
	
	private double calculateCorrelation(List<Double> list1, List<Double> list2) {
		double[] array1 = list1.stream().mapToDouble(d -> d).toArray();
		double[] array2 = list2.stream().mapToDouble(d -> d).toArray();
		return correlation.correlation(array1, array2);
	}

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
