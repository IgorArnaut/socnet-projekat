package rs.ac.uns.pmf.analysis.macroscopic;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Analyzer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class DensityAnalyzer implements Analyzer {

	private double getDensity(Graph<Vertex, Edge> graph) {
		int vertexCount = graph.getVertexCount();
		int edgeCount = graph.getEdgeCount();

		if (vertexCount == 0)
			return 0.0;

		return 2.0 * edgeCount / (vertexCount * (vertexCount - 1));
	}

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		Graph<Vertex, Edge> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			double y = getDensity(core);
			x++;
		} while (core.getVertexCount() > 0);
	}

}
