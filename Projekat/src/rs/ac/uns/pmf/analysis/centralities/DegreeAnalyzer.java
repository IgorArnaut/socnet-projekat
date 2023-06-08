package rs.ac.uns.pmf.analysis.centralities;

import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.Triple;

public class DegreeAnalyzer extends CentralitiesAnalyzer {

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Map<Vertex, Integer> shellIndices) {
		for (Vertex vertex : shellIndices.keySet()) {
			String id = vertex.getId();
			int x = shellIndices.get(vertex);
			double y = graph.degree(vertex);
			results.add(new Triple<>(id, x, y));
		}

		correlation = getCorrelation();
	}

	@Override
	public void report(String folder) {
		String file = "degrees.csv";
		String header = "Node ID;Shell index;Degree";
		exporter.setData(results, correlation);
		exporter.exportToCSV(folder, file, header);
	}

}