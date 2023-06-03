package rs.ac.uns.pmf.analysis;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;

public interface Analyzer<V, E> {

	void analyze(Graph<V, E> graph, Decomposer<V, E> decomposer);
	
}
