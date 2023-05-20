package rs.ac.uns.pmf.generators;

import java.util.Arrays;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class ErdosRenyiGenerator2 extends Generator {

	private Node[] nodes;
	private Link[][] links;

	public ErdosRenyiGenerator2() {
	}

	// Kreiraj mrezu R sa N cvorova i bez linkova
	private void insertNodes(int nodeCount) {
		this.nodes = new Node[nodeCount];

		for (int i = 0; i < nodeCount; i++) {
			nodes[i] = new Node(Integer.toString(i));
			graph.addVertex(nodes[i]);
		}
	}

	// Kreiraj S - skup svih mogucih linkova u R
	private void populateLinks() {
		this.links = new Link[nodes.length][nodes.length];
		
		for (int i = 0; i < nodes.length; i++)
			Arrays.fill(links[i], null);

		for (int i = 0; i < nodes.length - 1; i++) {
			for (int j = i + 1; j < nodes.length; j++)
				links[i][j] = new Link(i + LINE + j);
		}
	}

	private void insertLink(int i, int j, double probability) {
		// a = odaberi link iz S gde je 1/|S| verovatnoca odabira bilo kog linka
		if (random.nextDouble() >= probability) {
			// Dodaj a u R
			graph.addEdge(links[i][j], nodes[i], nodes[j]);
			// S = S - {a}
			links[i][j] = null;
		}
	}

	private void insertLinks(double probability) {
		// Dok iz skupa S nije odarbano L linkova
		for (int i = 0; i < nodes.length - 1; i++) {
			for (int j = i + 1; j < nodes.length; j++) {
				insertLink(i, j, probability);
				links[i][j] = null;
			}
		}
	}

	@Override
	public void generate(int nodeCount, double probability) {
		this.graph = new UndirectedSparseGraph<Node, Link>();

		// Kreiraj mrezu R sa N cvorova i bez linkova
		insertNodes(nodeCount);
		// Kreiraj S - skup svih mogucih linkova u R
		populateLinks();
		insertLinks(probability);
	}

	public UndirectedSparseGraph<Node, Link> getResult() {
		return this.graph;
	}

}
