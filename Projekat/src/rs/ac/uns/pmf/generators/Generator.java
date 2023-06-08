package rs.ac.uns.pmf.generators;

import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public abstract class Generator {

	protected final Random RANDOM = new Random();

	protected int id = 0;
	protected Graph<Vertex, Edge> graph;

	protected void insertEdge(Edge edge, Vertex source, Vertex target, double p) {
		if (RANDOM.nextDouble() <= p)
			graph.addEdge(edge, source, target);
	}

	public abstract Graph<Vertex, Edge> generate();

}
