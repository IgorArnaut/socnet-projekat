package rs.ac.uns.pmf.decomposers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ToIntFunction;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class BatageljZaversnikDecomposer extends Decomposer {

	private Vertex[] vertices;
	private int[] degrees;
	private int maxDegree;
	private Map<Integer, List<Vertex>> verticesPerDegree;

	private void populateVertices(Graph<Vertex, Edge> graph) {
		this.vertices = graph.getVertices().stream().toArray(Vertex[]::new);
	}

	private void populateDegrees(Graph<Vertex, Edge> graph) {
		ToIntFunction<Vertex> mapper = v -> graph.degree(v);
		this.degrees = Arrays.stream(vertices).mapToInt(mapper).toArray();
	}

	private void setMaxDegree() {
		this.maxDegree = Arrays.stream(degrees).max().getAsInt();
	}

	private void populateVerticesPerDegree(Graph<Vertex, Edge> graph) {
		this.verticesPerDegree = new LinkedHashMap<>();

		for (int k = 0; k <= maxDegree; k++)
			verticesPerDegree.put(k, new ArrayList<>());

		Arrays.stream(vertices).forEach(v -> {
			int degree = graph.degree(v);
			verticesPerDegree.get(degree).add(v);
		});
	}

	private void init(Graph<Vertex, Edge> graph) {
		populateVertices(graph);
		populateDegrees(graph);
		setMaxDegree();
		populateVerticesPerDegree(graph);
	}

	private boolean isEmpty(List<Vertex> vertices) {
		return vertices == null || vertices.isEmpty();
	}

	private void changeDegree(Vertex vertex, int k) {
		int i = Arrays.asList(vertices).indexOf(vertex);
		int degree = degrees[i];

		if (degree > k) {
			verticesPerDegree.get(degree).remove(vertex);
			System.out.println("Removed neighbor from " + degree + ": " + vertex);
			verticesPerDegree.get(degree - 1).add(vertex);
			System.out.println("Moved neighbor to " + (degree - 1) + ": " + vertex);
			degrees[k] -= 1;
		}
	}

	private Vertex removeVertex(Graph<Vertex, Edge> graph, List<Vertex> vertices, int k) {
		Vertex vertex = vertices.remove(0);
		System.out.println("Removed vertex from " + k + ": " + vertex);
		Collection<Vertex> neighbors = graph.getNeighbors(vertex);
		neighbors.forEach(n -> changeDegree(n, k));
		return vertex;
	}

	@Override
	public Map<Vertex, Integer> decompose(Graph<Vertex, Edge> graph) {
		init(graph);

		for (int k = 0; k <= maxDegree; k++) {
			System.out.println("Core: " + k);
			
			if (!isEmpty(verticesPerDegree.get(k))) {
				List<Vertex> verticesOfDegree = verticesPerDegree.get(k);

				do {
					Vertex vertex = removeVertex(graph, verticesOfDegree, k);
					shellIndices.put(vertex, k);
				} while (!verticesOfDegree.isEmpty());
			}
		}

		return shellIndices;
	}

}
