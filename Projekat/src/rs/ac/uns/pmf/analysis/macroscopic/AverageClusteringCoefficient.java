package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;
import java.util.Map;
import java.util.function.ToDoubleFunction;

import edu.uci.ics.jung.algorithms.metrics.Metrics;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class AverageClusteringCoefficient implements Macroscopic {

	private double getAvgClusteringCoefficient(Graph<Vertex, Edge> graph) {
		if (graph.getVertexCount() == 0)
			return 0.0;

		Map<Vertex, Double> clusteringCoefficients = Metrics.clusteringCoefficients(graph);
		ToDoubleFunction<Vertex> mapper = c -> clusteringCoefficients.get(c);
		double coefficient = clusteringCoefficients.keySet().stream().mapToDouble(mapper).average().getAsDouble();
		return coefficient;
	}

	@Override
	public double[] getValues(List<Graph<Vertex, Edge>> cores) {
		ToDoubleFunction<Graph<Vertex, Edge>> mapper = c -> getAvgClusteringCoefficient(c);
		return cores.stream().mapToDouble(mapper).toArray();
	}

}
