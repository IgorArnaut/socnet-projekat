package rs.ac.uns.pmf.decomposers;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class StraightforwardDecomposer extends Decomposer {

	private void copyPair(Graph<Vertex, Edge> source, Graph<Vertex, Edge> target, Edge edge) {
		Pair<Vertex> pair = source.getEndpoints(edge);
		target.addEdge(edge, pair);
	}

	private Graph<Vertex, Edge> copyGraph(Graph<Vertex, Edge> graph) {
		Graph<Vertex, Edge> copy = new UndirectedSparseGraph<>();
		graph.getEdges().forEach(e -> copyPair(graph, copy, e));
		return copy;
	}

	private int getMaxDegree(Graph<Vertex, Edge> graph) {
		ToIntFunction<Vertex> function = v -> graph.degree(v);
		return graph.getVertices().stream().mapToInt(function).max().getAsInt();
	}

	private boolean degreeExists(Graph<Vertex, Edge> graph, int k) {
		Predicate<Vertex> predicate = v -> graph.degree(v) == k;
		return graph.getVertices().stream().anyMatch(predicate);
	}

	@Override
	public Map<Vertex, Integer> decompose(Graph<Vertex, Edge> graph) {
		Graph<Vertex, Edge> copy = copyGraph(graph);
		Graph<Vertex, Edge> temp = null;

		int maxDegree = getMaxDegree(copy);

		for (int k = 0; k <= maxDegree; k++) {
			do {
				temp = new UndirectedSparseGraph<>();

				for (Edge edge : copy.getEdges()) {
					Pair<Vertex> pair = copy.getEndpoints(edge);
					Vertex first = pair.getFirst();
					Vertex second = pair.getSecond();
					boolean inCore = copy.degree(first) > k && copy.degree(second) > k;

					if (inCore) {
						temp.addEdge(edge, pair);
						shellIndices.put(first, k);
						shellIndices.put(second, k);
					}
				}

				copy = temp;
			} while (degreeExists(copy, k));
		}

		return shellIndices;
	}

}
