package rs.ac.uns.pmf.generators;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class ErdosRenyiGeneratorMatrix extends Generator {

	private int linkCount;
	private double[][] randoms;

	private Node[] nodes;
	private int[][] links;

	public ErdosRenyiGeneratorMatrix() {
		this.linkCount = 0;
	}

	private void insertNodes(int nodeCount) {
		this.nodes = new Node[nodeCount];

		for (int i = 0; i < nodeCount; i++) {
			nodes[i] = new Node(Integer.toString(i));
			graph.addVertex(nodes[i]);
		}
	}

	private void populateLinks(int nodeCount) {
		this.links = new int[nodeCount][nodeCount];
		this.randoms = new double[nodeCount][nodeCount];

		for (int i = 0; i < nodeCount - 1; i++) {
			for (int j = i + 1; j < nodeCount; j++) {
				links[i][j] = 1;
				randoms[i][j] = Math.random();
				linkCount++;
			}
		}
	}

	private int insertLink(int insertedCount, int i, int j) {
		double probability = 1.0 / linkCount;

		if (randoms[i][j] >= probability) {
			Link link = new Link(i + LINE + j);
			links[i][j] = 0;

			graph.addEdge(link, nodes[i], nodes[j]);
			insertedCount++;
		}

		return insertedCount;
	}

	private void insertLinks() {
		if (linkCount >= totalLinkCount) {
			int insertedCount = 0;

			for (int i = 0; i < links.length - 1 && insertedCount < totalLinkCount; i++) {
				for (int j = i + 1; j < links.length && insertedCount < totalLinkCount; j++)
					insertedCount = insertLink(insertedCount, i, j);
			}
		}
	}

	@Override
	public void generate(int nodeCount, int linkCount) {
		this.totalLinkCount = linkCount;
		this.graph = new UndirectedSparseGraph<Node, Link>();

		insertNodes(nodeCount);
		populateLinks(nodeCount);
		insertLinks();
	}

	public UndirectedSparseGraph<Node, Link> getResult() {
		return this.graph;
	}

}
