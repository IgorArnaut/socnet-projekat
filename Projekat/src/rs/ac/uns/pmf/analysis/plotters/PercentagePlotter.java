package rs.ac.uns.pmf.analysis.plotters;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;

@SuppressWarnings("serial")
public class PercentagePlotter<V, E> extends Plotter<V, E> {

	private Decomposer<V, E> decomposer;
	private GiantComponent<V, E> component = new GiantComponent<>();

	public PercentagePlotter(Graph<V, E> graph, Decomposer<V, E> decomposer) {
		super(graph);
		this.decomposer = decomposer;
	}

	private double getPercentage(Graph<V, E> graph) {
		if (graph == null)
			return 0.0;

		int n = graph.getVertexCount();
		int l = graph.getEdgeCount();

		if (l == 0)
			return 0;

		return (100.0 * n) / l;
	}

	@Override
	protected XYDataset createDataset() {
		XYSeriesCollection collection = new XYSeriesCollection();
		XYSeries series = new XYSeries("Percentages");

		Graph<V, E> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			Graph<V, E> giantComponent = component.getGiantComponent(graph);
			double y = getPercentage(giantComponent);
			series.add(x, y);
			x++;
		} while (core.getVertexCount() > 0);

		collection.addSeries(series);
		return collection;
	}

	@Override
	public void plot() {
		XYDataset dataset = createDataset();
		JFreeChart chart = ChartFactory.createXYLineChart("Cores vs. Percentages", "Cores", "Percentages", dataset);
		plot(chart);
	}

}
