package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.Set;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ComponentCountAnalyzer extends MacroscopicAnalyzer {

	private int getComponentCount(Graph<Vertex, Edge> graph) {
		Set<Set<Vertex>> clusters = new WeakComponentClusterer<Vertex, Edge>().apply(graph);
		return clusters.size();
	}

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		Graph<Vertex, Edge> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			int y = getComponentCount(core);
			results.put(x, 1.0 * y);
			x++;
		} while (core.getVertexCount() > 0);
	}
	
	@Override
	public void report(String folder) {
		String file = "component-counts.csv";
		String header = "Core;Component count";
		exportToCSV(folder, file, header);
	}

}
