package rs.ac.uns.pmf.generators;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class BarabasiAlbertGenerator extends Generator {

	private static final Generator INSTANCE = new BarabasiAlbertGenerator();

	public BarabasiAlbertGenerator() {
	}

	public static Generator instance() {
		return INSTANCE;
	}

	private void insertLink(int i, int j, Node newNode, Node oldNode, double probability) {
		if (random.nextDouble() >= probability) {
			Link link = new Link(String.format("%02d%s%02d", i, LINE, j));
			graph.addEdge(link, newNode, oldNode);
		}
	}

	private void insertLinks(int i, int nodeCount, Node newNode, int newDegree, int degreeSum) {
		int insertedCount = 0;

		for (int j = 0; j < nodeCount && insertedCount < newDegree; j++) {
			Node oldNode = (Node) graph.getVertices().toArray()[j];
			int oldDegree = graph.degree(oldNode);

			// Verovatnoca da stari cvor bude povezan sa novim cvorom je direktno
			// proporcionalna stepenu starog cvora
			double probability = oldDegree / degreeSum;

			insertLink(i, j, newNode, oldNode, probability);
		}
	}

	private void insertNode(int nodeCount, int i) {
		// U svakoj iteraciji dodajemo novi cvor sa m linkova (m < m_0)
		int newDegree = (int) Math
				.round(graph.getVertices().stream().mapToInt(current -> graph.degree(current)).average().getAsDouble());
		int degreeSum = graph.getVertices().stream().mapToInt(current -> graph.degree(current)).sum();

		Node newNode = new Node(String.format("%02d", i));
		insertLinks(i, nodeCount, newNode, newDegree, degreeSum);
	}

	private void insertNewNodes(int nodeCount, int y) {
		for (int i = nodeCount; i < y; i++)
			insertNode(nodeCount, i);
	}

	public void generate(int nodeCount, double probability) {
		// Pocetno stanje: random mreza sa m_0 cvorova
		int erNodeCount = (int) (Math.random() * (nodeCount / 2));
		ErdosRenyiGenerator.instance().generate(erNodeCount, probability);
		this.graph = ErdosRenyiGenerator.instance().getResult();

		insertNewNodes(erNodeCount, nodeCount);
	}

	public Graph<Node, Link> getResult() {
		return this.graph;
	}

}
