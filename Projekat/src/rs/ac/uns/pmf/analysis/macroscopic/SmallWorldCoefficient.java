package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;
import java.util.function.ToDoubleFunction;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.GiantComponent;

public class SmallWorldCoefficient implements Macroscopic {

	private GiantComponent gc = new GiantComponent();

	private double getDistanceSum(Graph<Vertex, Edge> graph, Vertex[] vertices) {
		DijkstraDistance<Vertex, Edge> usp = new DijkstraDistance<>(graph);
		double distanceSum = 0.0;

		for (int i = 0; i < vertices.length - 1; i++) {
			for (int j = i + 1; j < vertices.length; j++)
				distanceSum += usp.getDistance(vertices[i], vertices[j]).doubleValue();
		}

		return distanceSum;
	}

	private double calculate(Graph<Vertex, Edge> graph) {
		if (graph == null)
			return 0.0;

		Vertex[] vertices = graph.getVertices().toArray(Vertex[]::new);
		int n = vertices.length;

		if (n == 0 || n - 1 == 0)
			return 0.0;

		double distanceSum = getDistanceSum(graph, vertices);
		return distanceSum / (n * (n - 1));
	}

	private double getSmallWorldCoefficient(Graph<Vertex, Edge> graph) {
		Graph<Vertex, Edge> component = gc.getGiantComponent(graph);
		return calculate(component);
	}

	@Override
	public double[] getValues(List<Graph<Vertex, Edge>> cores) {
		ToDoubleFunction<Graph<Vertex, Edge>> mapper = c -> getSmallWorldCoefficient(c);
		return cores.stream().mapToDouble(mapper).toArray();
	}

}
