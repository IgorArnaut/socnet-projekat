package rs.ac.uns.pmf.analysis.plotters;

import java.util.Set;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.graph.Graph;

public class GiantComponent<V, E> {

	public Graph<V, E> getGiantComponent(Graph<V, E> graph) {
		Set<Set<V>> clusters = new WeakComponentClusterer<V, E>().apply(graph);

		if (clusters.isEmpty())
			return null;

		Set<V> cluster = clusters.stream().toList().get(0);
		return FilterUtils.createInducedSubgraph(cluster, graph);
	}

}
