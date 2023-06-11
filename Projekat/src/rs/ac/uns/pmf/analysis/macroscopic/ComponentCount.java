package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;
import java.util.Set;
import java.util.function.ToDoubleFunction;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ComponentCount implements Macroscopic {

	private int getComponentCount(Graph<Vertex, Edge> graph) {
		if (graph == null)
			return 0;

		Set<Set<Vertex>> clusters = new WeakComponentClusterer<Vertex, Edge>().apply(graph);
		return clusters.size();
	}

	@Override
	public double[] getValues(List<Graph<Vertex, Edge>> cores) {
		ToDoubleFunction<Graph<Vertex, Edge>> mapper = c -> getComponentCount(c);
		return cores.stream().mapToDouble(mapper).toArray();
	}

}
