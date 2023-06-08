package rs.ac.uns.pmf.decomposers;

import java.util.Map;
import java.util.TreeMap;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public abstract class Decomposer {

	protected Map<Vertex, Integer> shellIndices;

	public Decomposer() {
		this.shellIndices = new TreeMap<>();
	}

	public abstract Map<Vertex, Integer> decompose(Graph<Vertex, Edge> graph);

}
