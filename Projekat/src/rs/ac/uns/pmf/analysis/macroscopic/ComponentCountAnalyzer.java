package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.Set;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Analyzer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ComponentCountAnalyzer implements Analyzer {

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
			x++;
		} while (core.getVertexCount() > 0);
	}

}
