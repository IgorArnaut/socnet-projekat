package rs.ac.uns.pmf.decomposers;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.ToIntFunction;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class StraightforwardDecomposer extends Decomposer {

	private void insertPair(Graph<Node, Link> source, Graph<Node, Link> target, Link link) {
		Pair<Node> pair = source.getEndpoints(link);
		target.addEdge(link, pair);
	}

	private Graph<Node, Link> copyGraph(Graph<Node, Link> graph) {
		Graph<Node, Link> copy = new UndirectedSparseGraph<Node, Link>();
		Iterator<Link> iterator = graph.getEdges().iterator();

		while (iterator.hasNext()) {
			Link link = iterator.next();
			insertPair(graph, copy, link);
		}

		return copy;
	}

	private int getMaxDegree(Graph<Node, Link> graph) {
		ToIntFunction<Node> function = node -> graph.degree(node);
		return graph.getVertices().stream().mapToInt(function).max().getAsInt();
	}

	private boolean degreeExists(Graph<Node, Link> copy, int i) {
		for (Node n : copy.getVertices()) {
			if (copy.degree(n) == i)
				return true;
		}

		return false;
	}

	@Override
	public Map<Node, Integer> decompose(Graph<Node, Link> graph) {
		this.shellIndices = new LinkedHashMap<Node, Integer>();

		Graph<Node, Link> copy = copyGraph(graph);
		Graph<Node, Link> temp = null;

		int maxDegree = getMaxDegree(copy);

		for (int i = 0; i < maxDegree; i++) {
			do {
				temp = new UndirectedSparseGraph<Node, Link>();

				Iterator<Link> iterator = copy.getEdges().iterator();

				while (iterator.hasNext()) {
					Link link = iterator.next();
					Pair<Node> pair = copy.getEndpoints(link);
					Node first = pair.getFirst();
					Node second = pair.getSecond();

					if (copy.degree(first) > i && copy.degree(second) > i) {
						temp.addEdge(link, pair);
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
