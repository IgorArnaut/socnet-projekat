package rs.ac.uns.pmf.analysis.centralities;

import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Analyzer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class DegreeAnalyzer implements Analyzer {

	private Map<Vertex, Integer> shellIndices;

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		this.shellIndices = decomposer.decompose(graph);
		
		for (Vertex vertex : shellIndices.keySet()) {
			double x = shellIndices.get(vertex);
			double y = graph.degree(vertex);
		}
	}

}