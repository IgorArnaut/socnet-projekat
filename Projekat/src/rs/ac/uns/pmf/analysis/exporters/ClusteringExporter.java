package rs.ac.uns.pmf.analysis.exporters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import edu.uci.ics.jung.algorithms.metrics.Metrics;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ClusteringExporter extends Exporter {

	private Decomposer decomposer;

	public ClusteringExporter(Graph<Vertex, Edge> graph, Decomposer decomposer) {
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
	public void saveToCSV(String folder, String file) {
		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(new FileWriter("src/" + folder + "/" + file)))) {
			bw.append("Core;Clustering coefficient\n");
			Graph<Vertex, Edge> core = null;
			int x = 0;

			do {
				core = decomposer.getKCore(graph, x);
				double y = getAvgClusteringCoefficient(core);
				String row = String.format("%d;%.2f\n", x, y);
				bw.append(row);
				x++;
			} while (core.getVertexCount() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
