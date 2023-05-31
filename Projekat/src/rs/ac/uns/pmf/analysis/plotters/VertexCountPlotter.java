package rs.ac.uns.pmf.analysis.plotters;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;

@SuppressWarnings("serial")
public class VertexCountPlotter<V, E> extends Plotter<V, E> {

	private Decomposer<V, E> decomposer;

	public VertexCountPlotter(Graph<V, E> graph, Decomposer<V, E> decomposer) {
		super(graph);
		this.decomposer = decomposer;
	}

	@Override
	protected XYDataset createDataset() {
		XYSeriesCollection collection = new XYSeriesCollection();
		XYSeries series = new XYSeries("Vertex counts");

		Graph<V, E> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			int y = core.getVertexCount();
			series.add(x, y);
			x++;
		} while (core.getVertexCount() > 0);

		collection.addSeries(series);
		return collection;
	}

	@Override
	public void plot() {
		XYDataset dataset = createDataset();
		JFreeChart chart = ChartFactory.createXYLineChart("Cores vs. Vertex counts", "Cores", "Vertex counts", dataset);
		plot(chart);
	}

}