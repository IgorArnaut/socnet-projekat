package rs.ac.uns.pmf.analysis;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.GiantComponent;

public class PercentageAnalyzer extends Analyzer {

	private Decomposer decomposer;
	private GiantComponent component = new GiantComponent();

	public PercentageAnalyzer(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		super(graph);
		this.decomposer = decomposer;
	}

	private double getPercentage(Graph<Vertex, Edge> graph) {
		if (graph == null)
			return 0.0;

		int n = graph.getVertexCount();
		int l = graph.getEdgeCount();

		if (l == 0)
			return 0;

		return (100.0 * n) / l;
	}

	@Override
	public void analyze() {
		rows.add("Core;Giant component node percentage\n");
		Graph<Vertex, Edge> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			Graph<Vertex, Edge> giantComponent = component.getGiantComponent(graph);
			double y = getPercentage(giantComponent);
			String row = String.format("%d;%.2f\n", x, y);
			rows.add(row);
			x++;
		} while (core.getVertexCount() > 0);
	}

}
