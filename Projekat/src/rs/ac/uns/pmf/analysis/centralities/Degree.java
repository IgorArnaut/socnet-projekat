package rs.ac.uns.pmf.analysis.centralities;

import java.util.Arrays;
import java.util.function.ToDoubleFunction;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class Degree extends Centralities {

	@Override
	public double[] getValues(Graph<Vertex, Edge> graph, Vertex[] vertices) {
		ToDoubleFunction<Vertex> mapper = v -> graph.degree(v);
		return Arrays.stream(vertices).mapToDouble(mapper).toArray();
	}

}
