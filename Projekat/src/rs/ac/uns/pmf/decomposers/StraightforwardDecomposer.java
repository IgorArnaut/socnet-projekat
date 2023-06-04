package rs.ac.uns.pmf.decomposers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class StraightforwardDecomposer extends Decomposer {

	private void insertPair(Graph<Vertex, Edge> source, Graph<Vertex, Edge> target, Edge link) {
		Pair<Vertex> pair = source.getEndpoints(link);
		target.addEdge(link, pair);
	}

	private Graph<Vertex, Edge> copyGraph(Graph<Vertex, Edge> graph) {
		Graph<Vertex, Edge> copy = new UndirectedSparseGraph<>();
		graph.getEdges().forEach(e -> insertPair(graph, copy, e));
		return copy;
	}

	private int getMaxDegree(Graph<Vertex, Edge> graph) {
		ToIntFunction<Vertex> function = v -> graph.degree(v);
		return graph.getVertices().stream().mapToInt(function).max().getAsInt();
	}

	private boolean degreeExists(Graph<Vertex, Edge> copy, int i) {
		Predicate<Vertex> predicate = v -> copy.degree(v) == i;
		return copy.getVertices().stream().anyMatch(predicate);
	}

	@Override
	public Map<Vertex, Integer> decompose(Graph<Vertex, Edge> graph) {
		this.shellIndices = new LinkedHashMap<>();

		Graph<Vertex, Edge> copy = copyGraph(graph);
		Graph<Vertex, Edge> temp = null;

		int maxDegree = getMaxDegree(copy);

		for (int i = 0; i < maxDegree; i++) {
			do {
				temp = new UndirectedSparseGraph<>();

				for (Edge edge : copy.getEdges()) {
					Pair<Vertex> pair = copy.getEndpoints(edge);
					Vertex first = pair.getFirst();
					Vertex second = pair.getSecond();
					boolean inCore = copy.degree(first) > i && copy.degree(second) > i;

					if (inCore) {
						temp.addEdge(edge, pair);
						shellIndices.put(first, i);
						shellIndices.put(second, i);
					}
				}

				copy = temp;
			} while (degreeExists(copy, i));
		}

		sortShellIndices();
		return shellIndices;
	}

}
