package rs.ac.uns.pmf.decomposers;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public abstract class Decomposer {

	protected Map<Node, Integer> shellIndices;

	protected void sortShellIndices() {
		Comparator<Entry<Node, Integer>> comparator = Map.Entry.comparingByValue();
		Collector<Entry<Node, Integer>, ?, LinkedHashMap<Node, Integer>> collector = Collectors.toMap(Map.Entry::getKey,
				Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new);
		shellIndices = shellIndices.entrySet().stream().sorted(comparator).collect(collector);
	}

	public abstract Map<Node, Integer> decompose(Graph<Node, Link> graph);

	public Graph<Node, Link> getKCore(Graph<Node, Link> graph, int k) {
		decompose(graph);
		Graph<Node, Link> core = new UndirectedSparseGraph<Node, Link>();
		Iterator<Link> iterator = graph.getEdges().iterator();

		while (iterator.hasNext()) {
			Link link = iterator.next();

			Pair<Node> pair = graph.getEndpoints(link);
			Node first = pair.getFirst();
			Node second = pair.getSecond();

			if (shellIndices.containsKey(first) && shellIndices.containsKey(second)) {
				if (shellIndices.get(first) >= k && shellIndices.get(pair.getSecond()) >= k)
					core.addEdge(link, pair);
			}
		}

		return core;
	}

}
