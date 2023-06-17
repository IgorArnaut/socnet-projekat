package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;
import java.util.function.ToDoubleFunction;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class Density implements Macroscopic {

	private double calculate(int vertexCount, int edgeCount) {
		if (vertexCount == 0 || vertexCount - 1 == 0)
			return 0.0;

		return 2.0 * edgeCount / (vertexCount * (vertexCount - 1));
	}

	private double getDensity(Graph<Vertex, Edge> graph) {
		if (graph == null)
			return 0.0;

		int vertexCount = graph.getVertexCount();
		int edgeCount = graph.getEdgeCount();
		return calculate(vertexCount, edgeCount);
	}

	@Override
	public double[] getValues(List<Graph<Vertex, Edge>> cores) {
		ToDoubleFunction<Graph<Vertex, Edge>> mapper = c -> getDensity(c);
		double[] xs = cores.stream().mapToDouble(mapper).toArray();
		return xs;
	}

}
