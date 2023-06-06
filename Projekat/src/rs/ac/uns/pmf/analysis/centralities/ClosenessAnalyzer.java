package rs.ac.uns.pmf.analysis.centralities;

import java.util.Map;

import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Analyzer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ClosenessAnalyzer implements Analyzer {

	private Map<Vertex, Integer> shellIndices;
	private ClosenessCentrality<Vertex, Edge> centrality;

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		this.shellIndices = decomposer.decompose(graph);
		this.centrality = new ClosenessCentrality<Vertex, Edge>(graph);

		for (Vertex vertex : shellIndices.keySet()) {
			double x = shellIndices.get(vertex);
			double y = centrality.getVertexScore(vertex);
		}
	}

}
