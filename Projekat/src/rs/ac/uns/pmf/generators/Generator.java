package rs.ac.uns.pmf.generators;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public abstract class Generator {

	protected final String LINE = "--";

	protected int totalLinkCount;
	protected UndirectedSparseGraph<Node, Link> graph;

	public abstract void generate(int nodeCount, int linkCount);

	public UndirectedSparseGraph<Node, Link> getResult() {
		return this.graph;
	}

}
