package rs.ac.uns.pmf.analysis;

import java.util.Map;

import edu.uci.ics.jung.algorithms.metrics.Metrics;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ClusteringAnalyzer extends Analyzer {

	private Decomposer decomposer;

	public ClusteringAnalyzer(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		super(graph);
		this.decomposer = decomposer;
	}

	private double getAvgClusteringCoefficient(Graph<Vertex, Edge> graph) {
		int n = graph.getVertexCount();

		if (n == 0)
			return 0.0;

		Map<Vertex, Double> clusteringCoefficients = Metrics.clusteringCoefficients(graph);
		return clusteringCoefficients.values().stream().mapToDouble(d -> d).average().getAsDouble();
	}

	@Override
	public void analyze() {
		rows.add("Core;Clustering coefficient");
		Graph<Vertex, Edge> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			double y = getAvgClusteringCoefficient(core);
			String row = String.format("%d;%.2f", x, y);
			rows.add(row);
			x++;
		} while (core.getVertexCount() > 0);
	}

}
