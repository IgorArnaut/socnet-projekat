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

	@Override
	public Map<Vertex, Integer> decompose(Graph<Vertex, Edge> graph) {
		copyGraph(graph);
		Map<Vertex, Integer> shellIndices = new TreeMap<>();

		int maxDegree = getMaxDegree();

		for (int k = 1; k <= maxDegree; k++) {
			final int k2 = k;

			do {
				Predicate<Vertex> predicate = v -> {
					if (copy.degree(v) > k2)
						return true;
					else {
						shellIndices.put(v, k2);
						return false;
					}
				};
				List<Vertex> vertices = copy.getVertices().stream().filter(predicate).toList();
				copy = FilterUtils.createInducedSubgraph(vertices, copy);
			} while (degreeExists(k));
		}

		return shellIndices;
	}

}
