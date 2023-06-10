package rs.ac.uns.pmf.analysis.centralities;

import java.util.Map;

import edu.uci.ics.jung.algorithms.scoring.EigenvectorCentrality;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class EigenvectorAnalyzer extends CentralitiesAnalyzer {

	private void init(EigenvectorCentrality<Vertex, Edge> centrality, Map<Vertex, Integer> shellIndices) {
		this.vertices = shellIndices.keySet().toArray(Vertex[]::new);
		this.xs = new double[vertices.length];
		this.ys = new double[vertices.length];

		for (int i = 0; i < vertices.length; i++) {
			xs[i] = shellIndices.get(vertices[i]);
			ys[i] = centrality.getVertexScore(vertices[i]);
		}
	}

	public void analyze(Graph<Vertex, Edge> graph, Map<Vertex, Integer> shellIndices) {
		EigenvectorCentrality<Vertex, Edge> centrality = new EigenvectorCentrality<Vertex, Edge>(graph);
		init(centrality, shellIndices);
		insertValues();
	}

	@Override
	public void report(String folder) {
		String file = "eigenvector-centralities.csv";
		String header = "Node ID;Shell index;Eigenvector centrality";
		exporter.setData(results, correlation);
		exporter.exportToCSV(folder, file, header);
	}

}