package rs.ac.uns.pmf.analysis.centralities;

import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.Triple;

public class ClosenessAnalyzer extends CentralitiesAnalyzer {

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		this.shellIndices = decomposer.decompose(graph);
		ClosenessCentrality<Vertex, Edge> centrality = new ClosenessCentrality<Vertex, Edge>(graph);

		for (Vertex vertex : shellIndices.keySet()) {
			String id = vertex.getId();
			int x = shellIndices.get(vertex);
			double y = centrality.getVertexScore(vertex);
			results.add(new Triple<>(id, x, y));
		}
	}

	@Override
	public void report(String folder) {
		String file = "closeness-centralities.csv";
		String header = "Node ID;Shell index;Closeness centrality";
		exportToCSV(folder, file, header);
	}

}
