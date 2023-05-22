package rs.ac.uns.pmf.generators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class ErdosRenyiGenerator extends Generator {

	private Node[] nodes;
	private List<Link> links;

	@Override
	public Graph<Node, Link> generate(int nodeCount, double probability) {
		populateNodes(nodeCount);
		populateLinks();
		insertLinks(probability);
		return graph;
	}

	private void insertLinks(double probability) {
		Iterator<Link> iterator = links.iterator();

		while (iterator.hasNext()) {
			Link link = iterator.next();

			if (random.nextDouble() >= probability) {
				iterator.remove();

				String[] endpoints = link.getLabel().split(LINE);
				Node first = nodes[Integer.parseInt(endpoints[0])];
				Node second = nodes[Integer.parseInt(endpoints[1])];

				graph.addEdge(link, first, second);
			}
		}
	}

	private void populateLinks() {
		this.links = new ArrayList<Link>();

		for (int i = 0; i < nodes.length - 1; i++) {
			for (int j = i + 1; j < nodes.length; j++)
				links.add(new Link(String.format("%03d%s%03d", i, LINE, j)));
		}
	}

	private void populateNodes(int nodeCount) {
		this.graph = new UndirectedSparseGraph<Node, Link>();
		this.nodes = new Node[nodeCount];

		for (int i = 0; i < nodes.length; i++)
			nodes[i] = new Node(String.format("%03d", i));
	}

}
