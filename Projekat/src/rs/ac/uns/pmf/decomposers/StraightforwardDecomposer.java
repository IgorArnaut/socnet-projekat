package rs.ac.uns.pmf.decomposers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class StraightforwardDecomposer implements Decomposer {

	private static final StraightforwardDecomposer INSTANCE = new StraightforwardDecomposer();

	private StraightforwardDecomposer() {
	}

	public static StraightforwardDecomposer instance() {
		return INSTANCE;
	}

	private void insertPair(Graph<Node, Link> source, Graph<Node, Link> target, Link link) {
		Pair<Node> pair = source.getEndpoints(link);
		target.addEdge(link, pair);
	}

	private Graph<Node, Link> copyGraph(Graph<Node, Link> graph) {
		Graph<Node, Link> copy = new UndirectedSparseGraph<Node, Link>();

		for (Link link : graph.getEdges())
			insertPair(graph, copy, link);

		return copy;
	}

	private int getMaxDegree(Graph<Node, Link> graph) {
		return graph.getVertices().stream().mapToInt(node -> graph.degree(node)).max().getAsInt();
	}

	private void insertLink(Graph<Node, Link> source, Graph<Node, Link> target, int i, Link link, Node opposite) {
		if (source.degree(opposite) > i)
			insertPair(source, target, link);
	}

	private void insertNode(Graph<Node, Link> source, Graph<Node, Link> target, int i, Node node) {
		if (source.degree(node) > i) {
			for (Link link : source.getIncidentEdges(node)) {
				Node opposite = source.getOpposite(node, link);
				insertLink(source, target, i, link, opposite);
			}
		}
	}

	private boolean degreeExists(Graph<Node, Link> graph, int degree) {
		for (Node node : graph.getVertices()) {
			if (graph.degree(node) == degree)
				return true;
		}

		return false;
	}

	@Override
	public Map<Node, Integer> decompose(Graph<Node, Link> graph) {
		Map<Node, Integer> shellIndices = new HashMap<Node, Integer>();

		Graph<Node, Link> copy = copyGraph(graph);
		int maxDegree = getMaxDegree(copy);

		for (int i = 1; i < maxDegree; i++) {
		}

		return shellIndices;
	}

	@Override
	public Graph<Node, Link> getKCore(Graph<Node, Link> graph, int k) {
		Graph<Node, Link> copy = copyGraph(graph);
		Graph<Node, Link> temp = null;

		for (int i = 0; i <= k; i++) {
			System.out.println("Shell index: " + i);

			do {
				System.out.println("Node in index count: " + copy.getVertexCount());
				temp = new UndirectedSparseGraph<Node, Link>();

				for (Node node : copy.getVertices())
					insertNode(copy, temp, i, node);

				copy = temp;
			} while (degreeExists(copy, i));

			System.out.println();
		}

		return copy;
	}

}
