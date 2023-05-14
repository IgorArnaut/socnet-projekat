package rs.ac.uns.pmf.generators;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class ErdosRenyiGenerator extends Generator {

	private Node[] nodes;
	private List<Link> s;

	public ErdosRenyiGenerator() {
	}

	private void insertNodes(int n) {
		this.nodes = new Node[n];

		for (int i = 0; i < n; i++) {
			nodes[i] = new Node(Integer.toString(i));
			r.addVertex(nodes[i]);
		}
	}

	private void populateLinks(int n) {
		this.s = new ArrayList<Link>();

		for (int i = 0; i < n - 1; i++) {
			for (int j = i + 1; j < n; j++)
				s.add(new Link(i + LINE + j));
		}
	}

	private void insertLinks(int l) {
		if (s.size() > l) {
			int k = 0;

			for (int i = 0; i < s.size() && k < l; i++) {
				double probability = 1.0 / s.size();

				if (Math.random() >= probability) {
					Link link = s.remove(i);

					String[] endpoints = link.getLabel().split(LINE);
					int first = Integer.parseInt(endpoints[0]);
					int second = Integer.parseInt(endpoints[1]);
					r.addEdge(link, nodes[first], nodes[second]);
					k++;
				}
			}
		}
	}

	@Override
	public void generate(int n, int l) {
		this.r = new UndirectedSparseGraph<Node, Link>();
		insertNodes(n);
		populateLinks(n);
		insertLinks(l);
	}

}
