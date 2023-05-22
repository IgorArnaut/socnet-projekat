package rs.ac.uns.pmf.generators;

import java.util.function.ToIntFunction;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class BarabasiAlbertGenerator extends Generator {

	public Graph<Node, Link> generate(int nodeCount, double probability) {
		int erNodeCount = (int) (Math.random() * (nodeCount / 2));

		ErdosRenyiGenerator generator = new ErdosRenyiGenerator();
		this.graph = generator.generate(erNodeCount, probability);

		insertNewNodes(nodeCount, erNodeCount);
		return graph;
	}

	private void insertLinks(int i, int degreeSum, Node newNode, int newDegree) {
		for (int j = 0; j < newDegree; j++) {
			int index = (int) (Math.random() * graph.getVertexCount());
			Node node = graph.getVertices().toArray(Node[]::new)[index];

			double probability2 = graph.degree(node) / degreeSum;

			if (random.nextDouble() >= probability2) {
				int id = Integer.parseInt(node.getId());
				Link link = new Link(String.format("%03d%s%03d", i, LINE, id));
				graph.addEdge(link, node, newNode);
			}

			Link[] duplicates = graph.findEdgeSet(newNode, node).toArray(Link[]::new);

			if (duplicates.length > 1) {
				Link duplicate = duplicates[1];
				graph.removeEdge(duplicate);
			}
		}
	}

	private void insertNewNodes(int nodeCount, int erNodeCount) {
		for (int i = erNodeCount; i < nodeCount; i++) {
			ToIntFunction<Node> function = node -> graph.degree(node);
			int degreeSum = graph.getVertices().stream().mapToInt(function).sum();

			Node newNode = new Node(String.format("%03d", i));
			int maxdDegree = graph.getVertices().stream().mapToInt(function).max().getAsInt();
			int newDegree = (int) Math.round(Math.sqrt(maxdDegree));

			insertLinks(i, degreeSum, newNode, newDegree);
		}
	}

}
