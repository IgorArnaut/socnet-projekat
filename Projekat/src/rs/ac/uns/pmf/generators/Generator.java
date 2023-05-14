package rs.ac.uns.pmf.generators;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public abstract class Generator {

	protected final String LINE = "--";

	protected UndirectedSparseGraph<Node, Link> r;

	public abstract void generate(int n, int l);

	public UndirectedSparseGraph<Node, Link> getResult() {
		return this.r;
	}

}
