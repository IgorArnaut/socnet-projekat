package rs.ac.uns.pmf.decomposers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.graph.Graph;

public abstract class Decomposer<V, E> {

	protected Map<V, Integer> shellIndices;

	protected void sortShellIndices() {
		Map<V, Integer> sorted = new TreeMap<>(shellIndices);
		shellIndices = sorted;
	}

	public abstract Map<V, Integer> decompose(Graph<V, E> graph);

	private List<V> populateVertices(int k) {
		List<V> vertices = new ArrayList<>();
		shellIndices.entrySet().forEach(shellIndex -> {
			if (shellIndex.getValue() > k)
				vertices.add(shellIndex.getKey());
		});
		return vertices;
	}

	public Graph<V, E> getKCore(Graph<V, E> graph, int k) {
		List<V> vertices = populateVertices(k);
		Graph<V, E> core = FilterUtils.createInducedSubgraph(vertices, graph);
		return core;
	}

}
