package rs.ac.uns.pmf.analysis;

import java.util.Set;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ComponentCountAnalyzer extends Analyzer {

	private Decomposer decomposer;

	public ComponentCountAnalyzer(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		super(graph);
		this.decomposer = decomposer;
	}

	private int getComponentCount(Graph<Vertex, Edge> graph) {
		Set<Set<Vertex>> clusters = new WeakComponentClusterer<Vertex, Edge>().apply(graph);
		return clusters.size();
	}

	@Override
	public void analyze() {
		rows.add("Core;Component count");
		Graph<Vertex, Edge> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			int y = getComponentCount(core);
			String row = String.format("%d;%d", x, y);
			rows.add(row);
			x++;
		} while (core.getVertexCount() > 0);
	}

}
