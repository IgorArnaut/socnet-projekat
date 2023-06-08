package rs.ac.uns.pmf.analysis.macroscopic;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class DensityAnalyzer extends MacroscopicAnalyzer {

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
			results.put(x, y);
			x++;
		} while (core.getVertexCount() > 0);
	}
	
	@Override
	public void report(String folder) {
		String file = "densities.csv";
		String header = "Core;Density";
		exportToCSV(folder, file, header);
	}

}
