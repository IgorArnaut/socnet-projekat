package rs.ac.uns.pmf.decomposers;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class StraightforwardDecomposer implements Decomposer {

	private Graph<Vertex, Edge> copy;

	protected void copyPair(Graph<Vertex, Edge> source, Edge edge) {
		Pair<Vertex> pair = source.getEndpoints(edge);
		copy.addEdge(edge, pair);
	}

	protected void copyGraph(Graph<Vertex, Edge> graph) {
		this.copy = new UndirectedSparseGraph<>();
		graph.getEdges().forEach(e -> copyPair(graph, e));
	}

	private int getMaxDegree() {
		ToIntFunction<Vertex> function = v -> copy.degree(v);
		return copy.getVertices().stream().mapToInt(function).max().getAsInt();
	}

	private boolean degreeExists(int k) {
		Predicate<Vertex> predicate = v -> copy.degree(v) == k;
		return copy.getVertices().stream().anyMatch(predicate);
	}

	private boolean put(Map<Vertex, Integer> shellIndices, Vertex v, final int k2) {
		if (copy.degree(v) > k2)
			return true;

		shellIndices.put(v, k2);
		return false;
	}

	@Override
	public Map<Vertex, Integer> decompose(Graph<Vertex, Edge> graph) {
		copyGraph(graph);
		Map<Vertex, Integer> shellIndices = new TreeMap<>();

		int maxDegree = getMaxDegree();

		for (int k = 0; k <= maxDegree; k++) {
			final int k2 = k;

			do {
				Predicate<Vertex> predicate = v -> put(shellIndices, v, k2);
				List<Vertex> vertices = copy.getVertices().stream().filter(predicate).toList();
				copy = FilterUtils.createInducedSubgraph(vertices, copy);
			} while (degreeExists(k));
		}

		return shellIndices;
	}

}
