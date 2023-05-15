package rs.ac.uns.pmf.generators;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class ErdosRenyiGenerator extends Generator {

	private Node[] nodes;
	private List<Link> links;

	public ErdosRenyiGenerator() {
	}

	private void insertNodes(int nodeCount) {
		this.nodes = new Node[nodeCount];

		for (int i = 0; i < nodeCount; i++) {
			nodes[i] = new Node(Integer.toString(i));
			graph.addVertex(nodes[i]);
		}
	}

	private void populateLinks(int nodeCount) {
		this.links = new ArrayList<Link>();

		for (int i = 0; i < nodeCount - 1; i++) {
			for (int j = i + 1; j < nodeCount; j++)
				links.add(new Link(i + LINE + j));
		}
	}

	private int insertLink(int i, int insertedCount) {
		double probability = 1.0 / links.size();

		if (Math.random() >= probability) {
			Link link = links.remove(i);
			String[] endpoints = link.getLabel().split(LINE);
			int first = Integer.parseInt(endpoints[0]);
			int second = Integer.parseInt(endpoints[1]);

			graph.addEdge(link, nodes[first], nodes[second]);
			insertedCount++;
		}

		return insertedCount;
	}

	private void insertLinks() {
		if (links.size() >= totalLinkCount) {
			int insertedCount = 0;

			for (int i = 0; i < links.size() && insertedCount < totalLinkCount; i++)
				insertedCount = insertLink(i, insertedCount);
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

}
