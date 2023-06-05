package rs.ac.uns.pmf.generators;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ErdosRenyiGenerator extends Generator {

	private Vertex[] vertices;

	private void populateVertices(int vertexCount) {
		this.vertices = new Vertex[vertexCount];

		for (int i = 0; i < vertexCount; i++)
			vertices[i] = new Vertex(String.format("%03d", i));
	}

	private void insertEdge(Edge edge, Vertex source, Vertex target, double probability) {
		if (RANDOM.nextDouble() <= probability)
			graph.addEdge(edge, source, target);
	}

	private void insertEdges(double probability) {
		for (int i = 0; i < vertices.length - 1; i++) {
			for (int j = i + 1; j < vertices.length; j++) {
				String sourceId = "" + vertices[i];
				String targetId = "" + vertices[j];
				Edge edge = new Edge(sourceId, targetId);
				insertEdge(edge, vertices[i], vertices[j], probability);
			}
		}
	}

	@Override
	public Graph<Vertex, Edge> generate(int vertexCount, double probability) {
		this.graph = new UndirectedSparseGraph<>();
		populateVertices(vertexCount);
		insertEdges(probability);
		return graph;
	}

}
