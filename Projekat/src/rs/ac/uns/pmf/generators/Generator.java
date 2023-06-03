package rs.ac.uns.pmf.generators;

import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public abstract class Generator {

	protected final Random RANDOM = new Random();

	protected Graph<Vertex, Edge> graph;

	public abstract Graph<Vertex, Edge> generate(int vertexCount, double probability);

}
