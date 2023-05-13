package rs.ac.uns.pmf.main;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.GraphIOException;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class Main {

	public static void main(String[] args) {
		try {
			UndirectedSparseGraph<Node, Link> graph1 = GraphReader.readGraphml("src/graph1.graphml");
			UndirectedSparseGraph<Node, Link> graph2 = GraphReader.readGraphml("src/graph2.graphml");
			UndirectedSparseGraph<Node, Link> graph3 = GraphReader.readGraphml("src/graph3.graphml");
		} catch (GraphIOException e) {
			e.printStackTrace();
		}
	}
}
