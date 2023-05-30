package rs.ac.uns.pmf.generators;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ErdosRenyiGenerator2 extends Generator {

	private Vertex[] vertices;
	private Edge[][] edges;

	@Override
	public Graph<Vertex, Edge> generate(int vertexCount, double probability) {
		this.graph = new UndirectedSparseGraph<Vertex, Edge>();
		// 1.
		populateVertices(vertexCount);
		populateEdges();
		insertEdges(probability);
		return graph;
	}

	// 1.
	private void populateVertices(int vertexCount) {
		this.vertices = new Vertex[vertexCount];

		for (int i = 0; i < vertices.length; i++)
			vertices[i] = new Vertex(String.format("%02d", i));
	}

	// 2.
	private void populateEdges() {
		this.edges = new Edge[vertices.length][vertices.length];

		for (int i = 0; i < vertices.length - 1; i++) {
			for (int j = i + 1; j < vertices.length; j++)
				edges[i][j] = new Edge();
		}
	}

	// 3.
	private void insertEdge(double probability, int i, int j) {
		if (RANDOM.nextDouble() <= probability)
			graph.addEdge(edges[i][j], vertices[i], vertices[j]);
	}

	// 4.
	private void insertEdges(double probability) {
		for (int i = 0; i < vertices.length - 1; i++) {
			for (int j = i + 1; j < vertices.length; j++)
				// 3.
				insertEdge(probability, i, j);
		}
	}

}
