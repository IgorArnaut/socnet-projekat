package rs.ac.uns.pmf.decomposers;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;

public class StraightforwardDecomposer<V, E> extends Decomposer<V, E> {

	private void insertPair(Graph<V, E> source, Graph<V, E> target, E edge) {
		Pair<V> pair = source.getEndpoints(edge);
		target.addEdge(edge, pair);
	}

	private Graph<V, E> copyGraph(Graph<V, E> graph) {
		Graph<V, E> copy = new UndirectedSparseGraph<>();
		graph.getEdges().forEach(edge -> insertPair(graph, copy, edge));
		return copy;
	}

	private int getMaxDegree(Graph<V, E> graph) {
		ToIntFunction<V> function = vertex -> graph.degree(vertex);
		return graph.getVertices().stream().mapToInt(function).max().getAsInt();
	}

	private boolean degreeExists(Graph<V, E> copy, int i) {
		Predicate<V> predicate = vertex -> copy.degree(vertex) == i;
		return copy.getVertices().stream().anyMatch(predicate);
	}

	@Override
	public Map<V, Integer> decompose(Graph<V, E> graph) {
		this.shellIndices = new LinkedHashMap<>();
		Graph<V, E> copy = copyGraph(graph);
		Graph<V, E> temp = null;
		int maxDegree = getMaxDegree(copy);

		for (int i = 0; i < maxDegree; i++) {
			do {
				temp = new UndirectedSparseGraph<>();

				for (E edge : copy.getEdges()) {
					Pair<V> pair = copy.getEndpoints(edge);
					V first = pair.getFirst();
					V second = pair.getSecond();
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
