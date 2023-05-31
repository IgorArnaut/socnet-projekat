package rs.ac.uns.pmf.analysis.plotters;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;

@SuppressWarnings("serial")
public class DiameterPlotter<V, E> extends Plotter<V, E> {

	private Decomposer<V, E> decomposer;
	private GiantComponent<V, E> component = new GiantComponent<>();

	public DiameterPlotter(Graph<V, E> graph, Decomposer<V, E> decomposer) {
		super(graph);
		this.decomposer = decomposer;
	}

	private double getDiameter(Graph<V, E> graph) {
		if (graph == null)
			return 0.0;

		return DistanceStatistics.diameter(graph);
	}

	@Override
	protected XYDataset createDataset() {
		XYSeriesCollection collection = new XYSeriesCollection();
		XYSeries series = new XYSeries("Diameters");

		Graph<V, E> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			Graph<V, E> giantComponent = component.getGiantComponent(graph);
			double y = getDiameter(giantComponent);
			series.add(x, y);
			x++;
		} while (core.getVertexCount() > 0);

		collection.addSeries(series);
		return collection;
	}

	@Override
	public void plot() {
		XYDataset dataset = createDataset();
		JFreeChart chart = ChartFactory.createXYLineChart("Cores vs. Diameters", "Cores", "Diameters", dataset);
		plot(chart);
	}

}
