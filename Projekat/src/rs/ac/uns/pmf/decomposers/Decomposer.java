package rs.ac.uns.pmf.decomposers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public abstract class Decomposer {

	protected Map<Vertex, Integer> shellIndices;

	protected void sortShellIndices() {
		Map<Vertex, Integer> sorted = new TreeMap<>(shellIndices);
		shellIndices = sorted;
	}

	public abstract Map<Vertex, Integer> decompose(Graph<Vertex, Edge> graph);

	private List<Vertex> populateVertices(int k) {
		List<Vertex> vertices = new ArrayList<>();
		shellIndices.entrySet().forEach(shellIndex -> {
			if (shellIndex.getValue() > k)
				vertices.add(shellIndex.getKey());
		});
		return vertices;
	}

	public Graph<Vertex, Edge> getKCore(Graph<Vertex, Edge> graph, int k) {
		List<Vertex> vertices = populateVertices(k);
		Graph<Vertex, Edge> core = FilterUtils.createInducedSubgraph(vertices, graph);
		return core;
	}

}
