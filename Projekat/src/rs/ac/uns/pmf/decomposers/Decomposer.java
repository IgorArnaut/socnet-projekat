package rs.ac.uns.pmf.decomposers;

import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public interface Decomposer {

	public abstract Map<Node, Integer> decompose(Graph<Node, Link> graph);
	
	public abstract Graph<Node, Link> getKCore(Graph<Node, Link> graph, int k);
	
}
