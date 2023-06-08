package rs.ac.uns.pmf.decomposers;

import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public interface Decomposer {

	Map<Vertex, Integer> decompose(Graph<Vertex, Edge> graph);

}
