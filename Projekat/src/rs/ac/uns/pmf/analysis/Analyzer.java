package rs.ac.uns.pmf.analysis;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public interface Analyzer {

	void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer, String folder);
	
}
