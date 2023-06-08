package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class DensityAnalyzer extends MacroscopicAnalyzer {

	private double getDensity(Graph<Vertex, Edge> graph) {
		int vertexCount = graph.getVertexCount();
		int edgeCount = graph.getEdgeCount();

		if (vertexCount == 0)
			return 0.0;

		return 2.0 * edgeCount / (vertexCount * (vertexCount - 1));
	}

	@Override
	public void analyze(List<Graph<Vertex, Edge>> cores) {
		for (int x = 0; x < cores.size(); x++) {
			double y = getDensity(cores.get(x));
			results.put(x, y);
		}
	}

	@Override
	public void report(String folder) {
		String file = "densities.csv";
		String header = "Core;Density";
		exporter.setData(results);
		exporter.exportToCSV(folder, file, header);
	}

}
