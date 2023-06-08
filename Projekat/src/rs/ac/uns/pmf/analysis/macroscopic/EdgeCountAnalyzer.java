package rs.ac.uns.pmf.analysis.macroscopic;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class EdgeCountAnalyzer extends MacroscopicAnalyzer {

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		Graph<Vertex, Edge> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			int y = core.getEdgeCount();
			results.put(x, 1.0 * y);
			x++;
		} while (core.getVertexCount() > 0);
	}
	
	@Override
	public void report(String folder) {
		String file = "edge-counts.csv";
		String header = "Core;Edge count";
		exportToCSV(folder, file, header);
	}

}
