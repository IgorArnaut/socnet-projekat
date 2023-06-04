package rs.ac.uns.pmf.decomposers;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public abstract class Decomposer {

	protected Map<Vertex, Integer> shellIndices;

	protected void sortShellIndices() {
		shellIndices = new TreeMap<>(shellIndices);
	}

	public abstract Map<Vertex, Integer> decompose(Graph<Vertex, Edge> graph);

	public Graph<Vertex, Edge> getKCore(Graph<Vertex, Edge> graph, int k) {
		decompose(graph);
		Predicate<Vertex> predicate = v -> shellIndices.get(v) > k;
		List<Vertex> vertices = shellIndices.keySet().stream().filter(predicate).toList();
		return FilterUtils.createInducedSubgraph(vertices, graph);
	}

}
