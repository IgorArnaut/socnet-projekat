package rs.ac.uns.pmf.generators;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ErdosRenyiGenerator extends Generator {

	private List<Vertex> vertices;
	private List<Edge> edges;
	private List<Pair<Vertex>> pairs;

	// 1.
	private void populateVertices(int vertexCount) {
		this.vertices = new ArrayList<Vertex>();

		for (int i = 0; i < vertexCount; i++) {
			Vertex vertex = new Vertex(String.format("%03d", i));
			vertices.add(vertex);
		}
	}

	// 2.
	private void populateEdges() {
		this.edges = new ArrayList<Edge>();
		this.pairs = new ArrayList<Pair<Vertex>>();

		for (int i = 0; i < vertices.size() - 1; i++) {
			for (int j = i + 1; j < vertices.size(); j++) {
				Edge edge = new Edge();
				edges.add(edge);

				Pair<Vertex> pair = new Pair<Vertex>(vertices.get(i), vertices.get(j));
				pairs.add(pair);
			}
		}
	}

	// 3.
	private void insertEdge(double probability, Edge edge, Pair<Vertex> pair) {
		if (RANDOM.nextDouble() <= probability)
			graph.addEdge(edge, pair);
	}

	// 4.
	private void insertEdges(double probability) {
		for (int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);
			Pair<Vertex> pair = pairs.get(i);
			// 3.
			insertEdge(probability, edge, pair);
		}
	}

	@Override
	public Graph<Vertex, Edge> generate(int nodeCount, double probability) {
		this.graph = new UndirectedSparseGraph<Vertex, Edge>();
		// 1.
		populateVertices(nodeCount);
		// 2.
		populateEdges();
		// 4.
		insertEdges(probability);
		return graph;
	}

}
