package rs.ac.uns.pmf.generators;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class ErdosRenyiGeneratorMatrix {

	private final String LINE = "--";

	private int linkCount;
	private double[][] randoms;

	private Node[] nodes;
	private int[][] links;
	private UndirectedSparseGraph<Node, Link> r;

	public ErdosRenyiGeneratorMatrix() {
		this.linkCount = 0;
		this.r = new UndirectedSparseGraph<Node, Link>();
	}

	private void insertNodes(int n) {
		this.nodes = new Node[n];

		for (int i = 0; i < n; i++) {
			nodes[i] = new Node(Integer.toString(i));
			r.addVertex(nodes[i]);
		}
	}

	private void populateLinks(int n) {
		this.links = new int[n][n];
		this.randoms = new double[n][n];

		for (int i = 0; i < n - 1; i++) {
			for (int j = i + 1; j < n; j++) {
				links[i][j] = 1;
				randoms[i][j] = Math.random();
				linkCount++;
			}
		}
	}

	private void insertLinks(int l) {
		if (linkCount > l) {
			int k = 0;

			for (int i = 0; i < links.length - 1 && k < l; i++) {
				for (int j = i + 1; j < links.length && k < l; j++) {
					double probability = 1.0 / linkCount;
					k++;

					if (randoms[i][j] >= probability) {
						Link link = new Link(i + LINE + j);
						links[i][j] = 0;
						linkCount--;

						Node first = nodes[i];
						Node second = nodes[j];
						r.addEdge(link, first, second);
					}
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
