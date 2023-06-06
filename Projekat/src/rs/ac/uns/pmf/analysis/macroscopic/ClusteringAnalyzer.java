package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.Map;

import edu.uci.ics.jung.algorithms.metrics.Metrics;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Analyzer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ClusteringAnalyzer implements Analyzer {

	private double getAvgClusteringCoefficient(Graph<Vertex, Edge> graph) {
		int n = graph.getVertexCount();

		if (n == 0)
			return 0.0;

		Map<Vertex, Double> clusteringCoefficients = Metrics.clusteringCoefficients(graph);
		return clusteringCoefficients.values().stream().mapToDouble(d -> d).average().getAsDouble();
	}

	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		Graph<Vertex, Edge> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			double y = getAvgClusteringCoefficient(core);
			x++;
		} while (core.getVertexCount() > 0);
	}

}
