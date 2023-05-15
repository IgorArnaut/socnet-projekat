package rs.ac.uns.pmf.generators;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class BarabasiAlbertGenerator extends Generator {

	private Generator erg;

	public BarabasiAlbertGenerator() {
		this.erg = new ErdosRenyiGenerator();
	}

	private int insertLinks(int nodeCount, int currentLinkCount, int i, Node newNode, int newDegree, int degreeSum) {
		int insertedCount = 0;

		for (int j = 0; j < nodeCount && insertedCount < newDegree; j++) {
			Node oldNode = (Node) graph.getVertices().toArray()[j];
			int oldDegree = graph.degree(oldNode);
			double probability = oldDegree / degreeSum;

			if (currentLinkCount < totalLinkCount) {
				if (Math.random() >= probability) {
					Link link = new Link(i + LINE + j);
					graph.addEdge(link, newNode, oldNode);
					currentLinkCount++;
				}
			}
		}

		return currentLinkCount;
	}

	private int insertNode(int nodeCount, int currentLinkCount, int i) {
		Node newNode = new Node(Integer.toString(i));
		int newDegree = (int) Math
				.round(graph.getVertices().stream().mapToInt(current -> graph.degree(current)).average().getAsDouble());

		int degreeSum = graph.getVertices().stream().mapToInt(current -> graph.degree(current)).sum();
		graph.addVertex(newNode);

		currentLinkCount = insertLinks(nodeCount, currentLinkCount, i, newNode, newDegree, degreeSum);
		return currentLinkCount;
	}

	private void insertNewNodes(int nodeCount, int y) {
		int currentLinkCount = graph.getEdgeCount();

		for (int i = nodeCount; i < y; i++)
			currentLinkCount = insertNode(nodeCount, currentLinkCount, i);
	}

	public void generate(int nodeCount, int linkCount) {
		int erNodeCount = (int) (Math.random() * (nodeCount / 2));
		int erLinkCount = (int) (Math.random() * (linkCount / 2));
		this.totalLinkCount = linkCount;

		erg.generate(erNodeCount, erLinkCount);
		this.graph = erg.getResult();

		insertNewNodes(erNodeCount, nodeCount);
	}

	public UndirectedSparseGraph<Node, Link> getResult() {
		return this.graph;
	}

}
