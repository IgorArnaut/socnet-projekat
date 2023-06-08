package rs.ac.uns.pmf.analysis.centralities;

import edu.uci.ics.jung.algorithms.scoring.EigenvectorCentrality;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.Triple;

public class EigenvectorAnalyzer extends CentralitiesAnalyzer {

	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		this.shellIndices = decomposer.decompose(graph);
		EigenvectorCentrality<Vertex, Edge> centrality = new EigenvectorCentrality<Vertex, Edge>(graph);

		for (Vertex vertex : shellIndices.keySet()) {
			String id = vertex.getId();
			int x = shellIndices.get(vertex);
			double y = centrality.getVertexScore(vertex);
			results.add(new Triple<>(id, x, y));
		}
	}

	@Override
	public void report(String folder) {
		String file = "eigenvector-centralities.csv";
		String header = "Node ID;Shell index;Eigenvector centrality";
		exportToCSV(folder, file, header);
	}

}