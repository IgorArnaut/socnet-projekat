package rs.ac.uns.pmf.analysis.centralities;

import java.util.Arrays;
import java.util.function.ToDoubleFunction;

import edu.uci.ics.jung.algorithms.scoring.EigenvectorCentrality;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class Eigenvector extends Centralities {

	@Override
	public double[] getValues(Graph<Vertex, Edge> graph, Vertex[] vertices) {
		EigenvectorCentrality<Vertex, Edge> centrality = new EigenvectorCentrality<>(graph);
		ToDoubleFunction<Vertex> mapper = v -> centrality.getVertexScore(v);
		double[] xs = Arrays.stream(vertices).mapToDouble(mapper).toArray();
		xs = normalizeAll(xs);
		return xs;
	}

}
