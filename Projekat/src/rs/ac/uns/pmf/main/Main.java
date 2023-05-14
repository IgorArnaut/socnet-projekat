package rs.ac.uns.pmf.main;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.GraphIOException;
import rs.ac.uns.pmf.generators.BarabasiAlbertGenerator;
import rs.ac.uns.pmf.generators.ErdosRenyiGenerator;
import rs.ac.uns.pmf.generators.ErdosRenyiGeneratorMatrix;
import rs.ac.uns.pmf.generators.Generator;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class Main {

	private static final int nodeCount = 100;
	private static final int linkCount = 200;

	public static void insertNewNodes(Generator gen) {
		for (int i = 0; i < nodeCount; i++) {
			int label = i + nodeCount;
			Node n = new Node(Integer.toString(label));
			int m = (int) (Math.random() * nodeCount);

			((BarabasiAlbertGenerator) gen).insertNewNode(n, m);
		}
	}

	public static void main(String[] args) {
		try {
			UndirectedSparseGraph<Node, Link> graph1 = GraphReader.readGraphml("src/graph1.graphml");
			UndirectedSparseGraph<Node, Link> graph2 = GraphReader.readGraphml("src/graph2.graphml");
			UndirectedSparseGraph<Node, Link> graph3 = GraphReader.readGraphml("src/graph3.graphml");
			System.out.format("Nodes: %d, Edges: %d\n", graph1.getVertexCount(), graph1.getEdgeCount());

			Generator erg = new ErdosRenyiGenerator();
			erg.generate(nodeCount, linkCount);

			UndirectedSparseGraph<Node, Link> erGraph = erg.getResult();
			System.out.format("Nodes: %d, Edges: %d\n", erGraph.getVertexCount(), erGraph.getEdgeCount());

			Generator ergM = new ErdosRenyiGeneratorMatrix();
			ergM.generate(nodeCount, linkCount);

			UndirectedSparseGraph<Node, Link> erGraphM = ergM.getResult();
			System.out.format("Nodes: %d, Edges: %d\n", erGraphM.getVertexCount(), erGraphM.getEdgeCount());

			Generator bag = new BarabasiAlbertGenerator();
			bag.generate(nodeCount, linkCount);
			insertNewNodes(bag);

			UndirectedSparseGraph<Node, Link> baGraph = bag.getResult();
			System.out.format("Nodes: %d, Edges: %d\n", baGraph.getVertexCount(), baGraph.getEdgeCount());
		} catch (GraphIOException e) {
			e.printStackTrace();
		}
	}
}
