package rs.ac.uns.pmf.generators;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ErdosRenyiGenerator extends Generator {

	private int n;
	private double p;

	private Vertex[] vertices;
	private Edge[][] edges;

	public ErdosRenyiGenerator(int n, double p) {
		this.n = n;
		this.p = p;
		this.vertices = new Vertex[n];
		this.edges = new Edge[n][n];
	}

	private void populateVertices() {
		for (int i = 0; i < n; i++)
			vertices[i] = new Vertex(String.format("%03d", i));
	}

	private void populateEdges() {
		for (int i = 0; i < n - 1; i++) {
			for (int j = 0; j < n; j++) {
				edges[i][j] = new Edge(Integer.toString(id));
				id++;
			}
		}
	}

	private void insertEdges() {
		for (int i = 0; i < edges.length - 1; i++) {
			for (int j = i + 1; j < edges.length; j++) {
				Edge edge = edges[i][j];
				edges[i][j] = null;
				insertEdge(edge, vertices[i], vertices[j], p);
			}
		}
	}

	@Override
	public Graph<Vertex, Edge> generate() {
		this.graph = new UndirectedSparseGraph<>();
		populateVertices();
		populateEdges();
		insertEdges();
		return graph;
	}

}
