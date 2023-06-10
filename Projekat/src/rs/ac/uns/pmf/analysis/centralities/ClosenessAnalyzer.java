package rs.ac.uns.pmf.analysis.centralities;

import java.util.Map;

import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ClosenessAnalyzer extends CentralitiesAnalyzer {

	private void init(ClosenessCentrality<Vertex, Edge> centrality, Map<Vertex, Integer> shellIndices) {
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
		ClosenessCentrality<Vertex, Edge> centrality = new ClosenessCentrality<Vertex, Edge>(graph);
		init(centrality, shellIndices);
		insertValues();
	}

	@Override
	public void report(String folder) {
		String file = "closeness-centralities.csv";
		String header = "Node ID;Shell index;Closeness centrality";
		exporter.setData(results, correlation);
		exporter.exportToCSV(folder, file, header);
	}

}
