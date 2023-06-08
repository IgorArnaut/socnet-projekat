package rs.ac.uns.pmf.utils;

import java.util.Set;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class GiantComponent {

	/**
	 * Finds the giant component in the network.
	 * 
	 * @param graph
	 * @return
	 */
	public Graph<Vertex, Edge> getGiantComponent(Graph<Vertex, Edge> graph) {
		Set<Set<Vertex>> clusters = new WeakComponentClusterer<Vertex, Edge>().apply(graph);

		if (clusters.isEmpty())
			return null;

		Set<Vertex> cluster = clusters.stream().toList().get(0);
		return FilterUtils.createInducedSubgraph(cluster, graph);
	}

}
