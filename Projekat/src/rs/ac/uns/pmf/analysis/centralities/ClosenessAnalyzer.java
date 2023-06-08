package rs.ac.uns.pmf.analysis.centralities;

import java.util.Map;

import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.Triple;

public class ClosenessAnalyzer extends CentralitiesAnalyzer {

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Map<Vertex, Integer> shellIndices) {
		ClosenessCentrality<Vertex, Edge> centrality = new ClosenessCentrality<Vertex, Edge>(graph);

		for (Vertex vertex : shellIndices.keySet()) {
			String id = vertex.getId();
			int x = shellIndices.get(vertex);
			double y = centrality.getVertexScore(vertex);
			results.add(new Triple<>(id, x, y));
		}

		correlation = getCorrelation();
	}

	@Override
	public void report(String folder) {
		String file = "closeness-centralities.csv";
		String header = "Node ID;Shell index;Closeness centrality";
		exporter.setData(results, correlation);
		exporter.exportToCSV(folder, file, header);
	}

}
