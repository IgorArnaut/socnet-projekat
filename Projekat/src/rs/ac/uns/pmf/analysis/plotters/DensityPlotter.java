package rs.ac.uns.pmf.analysis.plotters;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;

@SuppressWarnings("serial")
public class DensityPlotter<V, E> extends Plotter<V, E> {

	private Decomposer<V, E> decomposer;

	public DensityPlotter(Graph<V, E> graph, Decomposer<V, E> decomposer) {
		super(graph);
		this.decomposer = decomposer;
	}

	private double getDensity(Graph<V, E> graph) {
		int vertexCount = graph.getVertexCount();
		int edgeCount = graph.getEdgeCount();

		if (vertexCount == 0)
			return 0.0;

		return 2.0 * edgeCount / (vertexCount * (vertexCount - 1));
	}

	@Override
	protected XYDataset createDataset() {
		XYSeriesCollection collection = new XYSeriesCollection();
		XYSeries series = new XYSeries("Densities");

		Graph<V, E> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			double y = getDensity(core);
			series.add(x, y);
			x++;
		} while (core.getVertexCount() > 0);

		collection.addSeries(series);
		return collection;
	}

	@Override
	public void plot() {
		XYDataset dataset = createDataset();
		JFreeChart chart = ChartFactory.createXYLineChart("Cores vs. Densities", "Cores", "Densities", dataset);
		plot(chart);
	}

}
