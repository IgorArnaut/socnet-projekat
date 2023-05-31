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

	private void populateVertices(int vertexCount) {
		this.vertices = new ArrayList<>();

		for (int i = 0; i < vertexCount; i++) {
			Vertex vertex = new Vertex(String.format("%03d", i));
			vertices.add(vertex);
		}
	}

	private void populateEdges() {
		this.edges = new ArrayList<>();
		this.pairs = new ArrayList<>();

		for (int i = 0; i < vertices.size() - 1; i++) {
			for (int j = i + 1; j < vertices.size(); j++) {
				Edge edge = new Edge();
				edges.add(edge);
				Pair<Vertex> pair = new Pair<>(vertices.get(i), vertices.get(j));
				pairs.add(pair);
			}
		}
	}

	private void insertEdge(double probability, Edge edge, Pair<Vertex> pair) {
		if (RANDOM.nextDouble() <= probability)
			graph.addEdge(edge, pair);
	}

	private void insertEdges(double probability) {
		for (int i = 0; i < edges.size(); i++) {
			Edge edge = edges.get(i);
			Pair<Vertex> pair = pairs.get(i);
			insertEdge(probability, edge, pair);
		}
	}

	@Override
	public Graph<Vertex, Edge> generate(int nodeCount, double probability) {
		this.graph = new UndirectedSparseGraph<>();
		populateVertices(nodeCount);
		populateEdges();
		insertEdges(probability);
		return graph;
	}

}
