package rs.ac.uns.pmf.analysis.centralities;

import java.util.Map;

import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class BetweennessAnalyzer extends CentralitiesAnalyzer {

	private void init(BetweennessCentrality<Vertex, Edge> centrality, Map<Vertex, Integer> shellIndices) {
		this.vertices = shellIndices.keySet().toArray(Vertex[]::new);
		this.xs = new double[vertices.length];
		this.ys = new double[vertices.length];
	
		for (int i = 0; i < vertices.length; i++) {
			xs[i] = shellIndices.get(vertices[i]);
			ys[i] = centrality.getVertexScore(vertices[i]);
		}
	}

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Map<Vertex, Integer> shellIndices) {
		BetweennessCentrality<Vertex, Edge> centrality = new BetweennessCentrality<>(graph);
		init(centrality, shellIndices);
		insertValues();
	}

	@Override
	public void report(String folder) {
		String file = "betweenness-centralities.csv";
		String header = "Node ID;Shell index;Betweenness centrality";
		exporter.setData(results, correlation);
		exporter.exportToCSV(folder, file, header);
	}

}
