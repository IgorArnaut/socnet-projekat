package rs.ac.uns.pmf.generators;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class ErdosRenyiGenerator extends Generator {

	private Node[] nodes;
	private List<Link> links;

	private static final Generator INSTANCE = new ErdosRenyiGenerator();

	private ErdosRenyiGenerator() {
	}

	public static Generator instance() {
		return INSTANCE;
	}

	// Kreiraj mrezu R sa N cvorova i bez linkova
	private void insertNodes(int nodeCount) {
		this.nodes = new Node[nodeCount];

		for (int i = 0; i < nodeCount; i++)
			nodes[i] = new Node(String.format("%02d", i));
	}

	// Kreiraj S - skup svih mogucih linkova u R
	private void populateLinks() {
		this.links = new ArrayList<Link>();

		for (int i = 0; i < nodes.length - 1; i++) {
			for (int j = i + 1; j < nodes.length; j++)
				links.add(new Link(String.format("%02d%s%02d", i, LINE, j)));
		}
	}

	private void insertLink(int i, double probability) {
		// a = odaberi link iz S gde je 1/|S| verovatnoca odabira bilo kog linka
		if (random.nextDouble() >= probability) {
			// S = S - {a}
			Link link = links.remove(i);

			String[] endpoints = link.getLabel().split(LINE);
			int first = Integer.parseInt(endpoints[0]);
			int second = Integer.parseInt(endpoints[1]);

			// Dodaj a u R
			graph.addEdge(link, nodes[first], nodes[second]);
		}
	}

	private void insertLinks(double probability) {
		// Dok iz skupa S nije odabrano L linkova
		for (int i = 0; i < links.size(); i++)
			insertLink(i, probability);
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

}
