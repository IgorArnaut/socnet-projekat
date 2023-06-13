package rs.ac.uns.pmf.analysis.centralities;

import java.util.Arrays;
import java.util.function.ToDoubleFunction;

import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class Closeness extends Centralities {

	@Override
	public double[] getValues(Graph<Vertex, Edge> graph, Vertex[] vertices) {
		ClosenessCentrality<Vertex, Edge> centrality = new ClosenessCentrality<>(graph);
		ToDoubleFunction<Vertex> mapper = v -> centrality.getVertexScore(v);
		double[] xs = Arrays.stream(vertices).mapToDouble(mapper).toArray();
		xs = normalizeAll(xs);
		return xs;
	}

}
