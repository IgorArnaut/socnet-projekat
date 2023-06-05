package rs.ac.uns.pmf.analysis;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class DensityAnalyzer extends Analyzer {

	private Decomposer decomposer;

	public DensityAnalyzer(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		super(graph);
		this.decomposer = decomposer;
	}

	private double getDensity(Graph<Vertex, Edge> graph) {
		int vertexCount = graph.getVertexCount();
		int edgeCount = graph.getEdgeCount();

		if (vertexCount == 0)
			return 0.0;

		return 2.0 * edgeCount / (vertexCount * (vertexCount - 1));
	}

	@Override
	public void analyze() {
		rows.add("Core;Density");
		Graph<Vertex, Edge> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			double y = getDensity(core);
			String row = String.format("%d;%.2f", x, y);
			rows.add(row);
			x++;
		} while (core.getVertexCount() > 0);
	}

}
