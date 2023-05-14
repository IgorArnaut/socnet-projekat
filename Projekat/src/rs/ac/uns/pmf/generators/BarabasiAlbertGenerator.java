package rs.ac.uns.pmf.generators;

import java.util.List;

import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class BarabasiAlbertGenerator extends Generator {

	private Generator erg;

	public BarabasiAlbertGenerator() {
		this.erg = new ErdosRenyiGenerator();
	}

	public void generate(int n, int l) {
		erg.generate(n, l);
		this.r = erg.getResult();
	}

	public void insertNewNode(Node n, int m) {
		int k = 0;
		int degreeSum = r.getVertices().stream().mapToInt(node -> r.getNeighborCount(node)).sum();

		r.addVertex(n);

		for (int i = 0; i < r.getVertexCount() && k < m; i++) {
			Node o = (Node) r.getVertices().toArray()[i];

			int degreeO = r.getNeighborCount(o);
			double probability = degreeO / degreeSum;

			if (Math.random() >= probability) {
				Link link = new Link(o + LINE + n);
				r.addEdge(link, o, n);
				k++;
			}
		}
	}

	public UndirectedSparseGraph<Node, Link> getResult() {
		return this.r;
	}

}
