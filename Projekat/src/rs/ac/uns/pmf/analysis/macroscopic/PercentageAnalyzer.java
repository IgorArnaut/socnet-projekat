package rs.ac.uns.pmf.analysis.macroscopic;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Analyzer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.GiantComponent;

public class PercentageAnalyzer implements Analyzer {

	private GiantComponent component = new GiantComponent();

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
	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		Graph<Vertex, Edge> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			Graph<Vertex, Edge> giantComponent = component.getGiantComponent(graph);
			double y = getPercentage(giantComponent);
			x++;
		} while (core.getVertexCount() > 0);
	}

}
