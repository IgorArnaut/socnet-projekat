package rs.ac.uns.pmf.generators;

import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public abstract class Generator {

	protected final String LINE = "--";
	protected final Random RANDOM = new Random();

	protected Graph<Node, Link> graph;

	public abstract Graph<Node, Link> generate(int nodeCount, double probability);

}
