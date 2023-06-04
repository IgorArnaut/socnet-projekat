package rs.ac.uns.pmf.generators;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ErdosRenyiGenerator extends Generator {

	private Vertex[] vertices;
	private List<Edge> edges;
	private List<Pair<Vertex>> pairs;

	private void populateVertices(int vertexCount) {
		this.vertices = new Vertex[vertexCount];

		for (int i = 0; i < vertexCount; i++)
			vertices[i] = new Vertex(String.format("%03d", i));
	}

	private void populateEdges() {
		this.edges = new ArrayList<>();

		for (int i = 0; i < vertices.length - 1; i++) {
			for (int j = i + 1; j < vertices.length; j++) {
				edges.add(new Edge());
				pairs.add(new Pair<>(vertices[i], vertices[j]));
			}
		}
	}

	private void insertEdge(Pair<Vertex> pair, Edge edge, double probability) {
		if (RANDOM.nextDouble() <= probability)
			graph.addEdge(edge, pair);
	}

	private void insertEdges(double probability) {
		edges.forEach(e -> {
			Pair<Vertex> pair = pairs.get(edges.indexOf(e));
			insertEdge(pair, e, probability);
		});
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
