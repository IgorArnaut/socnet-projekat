package rs.ac.uns.pmf.main;

import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.GraphIOException;
import rs.ac.uns.pmf.decomposers.StraightforwardDecomposer;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class Main {

	private static final int nodeCount = 100;
	private static final double probability = 0.1;

	public static void main(String[] args) {
		try {
			Graph<Node, Link> graph = GraphReader.readGraphml("src/graph3.graphml");
			System.out.println("Graph:");
			
			Graph<Node, Link> core = StraightforwardDecomposer.instance().getKCore(graph, 3);
			
			/*
			Graph<Node, Link> graph2 = GraphReader.readGraphml("src/graph2.graphml");
			System.out.println("Graphml 2:");
			System.out.format("Nodes: %d, Edges: %d\n", graph2.getVertexCount(), graph2.getEdgeCount());

			Graph<Node, Link> graph3 = GraphReader.readGraphml("src/graph3.graphml");
			System.out.println("Graphml 3:");
			System.out.format("Nodes: %d, Edges: %d\n", graph3.getVertexCount(), graph3.getEdgeCount());

			ErdosRenyiGenerator.instance().generate(nodeCount, probability);

			Graph<Node, Link> erGraph = ErdosRenyiGenerator.instance().getResult();
			System.out.println("Erdos-Renyi:");
			System.out.format("Nodes: %d, Edges: %d\n", erGraph.getVertexCount(), erGraph.getEdgeCount());
			GraphWriter.writeGraphml(erGraph, "src/er-graph.graphml");

			ErdosRenyiGenerator2.instance().generate(nodeCount, probability);

			Graph<Node, Link> erGraphM = ErdosRenyiGenerator2.instance().getResult();
			System.out.println("Erdos-Renyi 2:");
			System.out.format("Nodes: %d, Edges: %d\n", erGraphM.getVertexCount(), erGraphM.getEdgeCount());
			GraphWriter.writeGraphml(erGraphM, "src/er-graph-2.graphml");

			BarabasiAlbertGenerator.instance().generate(nodeCount, probability);

			Graph<Node, Link> baGraph = BarabasiAlbertGenerator.instance().getResult();
			System.out.println("Barabasi-Albert:");
			System.out.format("Nodes: %d, Edges: %d\n", baGraph.getVertexCount(), baGraph.getEdgeCount());
			GraphWriter.writeGraphml(baGraph, "src/ba-graph.graphml");
			*/
		} catch (GraphIOException e) {
			e.printStackTrace();
		}
	}
}
