package rs.ac.uns.pmf.generators;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class ErdosRenyiGenerator {

	private Node[] nodes;
	private List<Link> links;
	private UndirectedSparseGraph<Node, Link> r;

	private final String SEPARATOR = "--";

	public ErdosRenyiGenerator() {
		this.r = new UndirectedSparseGraph<Node, Link>();
		this.links = new ArrayList<Link>();
	}

	private void insertNodes(int n) {
		this.nodes = new Node[n];

		for (int i = 0; i < n; i++) {
			nodes[i] = new Node(Integer.toString(i));
			r.addVertex(nodes[i]);
		}
	}

	private void populateLinks(int n) {
		for (int i = 0; i < n - 1; i++) {
			for (int j = i + 1; j < n; j++) {
				links.add(new Link(i + SEPARATOR + j));
			}
		}
	}

	private void insertLinks(int l) {
		if (links.size() > l) {
			double probability = 1.0 / links.size();

			for (int i = 0; i < l; i++) {
				double random = Math.random();

				if (random >= probability) {
					int index = (int) random * links.size();
					Link link = links.remove(index);

					String[] endpoints = link.getLabel().split(SEPARATOR);
					Node first = nodes[Integer.parseInt(endpoints[0])];
					Node second = nodes[Integer.parseInt(endpoints[1])];
					r.addEdge(link, first, second);
				}
			}
		}
	}

	public void generate(int n, int l) {
		insertNodes(n);
		populateLinks(n);
		insertLinks(l);
	}

	public UndirectedSparseGraph<Node, Link> getResult() {
		return this.r;
	}

}
