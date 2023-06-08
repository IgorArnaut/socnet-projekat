package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.algorithms.metrics.Metrics;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ClusteringAnalyzer extends MacroscopicAnalyzer {

	private double getAvgClusteringCoefficient(Graph<Vertex, Edge> graph) {
		int n = graph.getVertexCount();

		if (n == 0)
			return 0.0;

		Map<Vertex, Double> clusteringCoefficients = Metrics.clusteringCoefficients(graph);
		return clusteringCoefficients.values().stream().mapToDouble(d -> d).average().getAsDouble();
	}

	@Override
	public void analyze(List<Graph<Vertex, Edge>> cores) {
		for (int x = 0; x < cores.size(); x++) {
			double y = getAvgClusteringCoefficient(cores.get(x));
			results.put(x, y);
		}
	}

	@Override
	public void report(String folder) {
		String file = "clustering-coefficients.csv";
		String header = "Core;Clustering coefficient";
		exporter.setData(results);
		exporter.exportToCSV(folder, file, header);
	}

}
