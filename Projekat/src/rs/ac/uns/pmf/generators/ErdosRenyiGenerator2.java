package rs.ac.uns.pmf.generators;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ErdosRenyiGenerator2 extends Generator {

	private Vertex[] vertices;
	private Edge[][] edges;

	private void populateVertices(int vertexCount) {
		this.vertices = new Vertex[vertexCount];

		for (int i = 0; i < vertices.length; i++)
			vertices[i] = new Vertex(String.format("%02d", i));
	}

	private void populateEdges() {
		this.edges = new Edge[vertices.length][vertices.length];

		for (int i = 0; i < vertices.length - 1; i++) {
			for (int j = i + 1; j < vertices.length; j++)
				edges[i][j] = new Edge();
		}
	}

	private void insertEdge(double probability, int i, int j) {
		if (RANDOM.nextDouble() <= probability)
			graph.addEdge(edges[i][j], vertices[i], vertices[j]);
	}

	private void insertEdges(double probability) {
		for (int i = 0; i < vertices.length - 1; i++) {
			for (int j = i + 1; j < vertices.length; j++)
				insertEdge(probability, i, j);
		}
	}

	@Override
	public Graph<Vertex, Edge> generate(int vertexCount, double probability) {
		this.graph = new UndirectedSparseGraph<>();
		populateVertices(vertexCount);
		populateEdges();
		insertEdges(probability);
		return graph;
	}

}
