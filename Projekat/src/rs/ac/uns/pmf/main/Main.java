package rs.ac.uns.pmf.main;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.GraphIOException;
import rs.ac.uns.pmf.generators.ErdosRenyiGenerator;
import rs.ac.uns.pmf.generators.ErdosRenyiGeneratorMatrix;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class Main {

	public static void main(String[] args) {
		try {
			UndirectedSparseGraph<Node, Link> graph1 = GraphReader.readGraphml("src/graph1.graphml");
			UndirectedSparseGraph<Node, Link> graph2 = GraphReader.readGraphml("src/graph2.graphml");
			UndirectedSparseGraph<Node, Link> graph3 = GraphReader.readGraphml("src/graph3.graphml");

			ErdosRenyiGenerator erg = new ErdosRenyiGenerator();
			erg.generate(100, 200);
			UndirectedSparseGraph<Node, Link> erGraph = erg.getResult();
			
			ErdosRenyiGeneratorMatrix erg2 = new ErdosRenyiGeneratorMatrix();
			erg2.generate(100, 200);
			UndirectedSparseGraph<Node, Link> erGraph2 = erg2.getResult();
		} catch (GraphIOException e) {
			e.printStackTrace();
		}
	}
}
