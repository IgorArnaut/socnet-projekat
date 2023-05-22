package rs.ac.uns.pmf.generators;

import java.util.Arrays;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class ErdosRenyiGenerator2 extends Generator {

	private Node[] nodes;
	private Link[][] links;

	@Override
	public Graph<Node, Link> generate(int nodeCount, double probability) {
		populateNodes(nodeCount);
		populateLinks();
		insertLinks(probability);
		return graph;
	}

	private void insertLinks(double probability) {
		for (int i = 0; i < nodes.length - 1; i++) {
			for (int j = i + 1; j < nodes.length; j++) {
				if (random.nextDouble() >= probability) {
					graph.addEdge(links[i][j], nodes[i], nodes[j]);
					links[i][j] = null;
				}
			}
		}
	}

	private void populateLinks() {
		this.links = new Link[nodes.length][nodes.length];

		for (int i = 0; i < nodes.length; i++)
			Arrays.fill(links[i], null);

		for (int i = 0; i < nodes.length - 1; i++) {
			for (int j = i + 1; j < nodes.length; j++)
				links[i][j] = new Link(String.format("%02d%s%02d", i, LINE, j));
		}
	}

	private void populateNodes(int nodeCount) {
		this.graph = new UndirectedSparseGraph<Node, Link>();
		this.nodes = new Node[nodeCount];

		for (int i = 0; i < nodes.length; i++)
			nodes[i] = new Node(String.format("%02d", i));
	}

}
