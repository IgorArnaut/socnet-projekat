package rs.ac.uns.pmf.decomposers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.ToIntFunction;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class BatageljZaversnikDecomposer implements Decomposer {

	private Graph<Vertex, Edge> graph;

	private Vertex[] vertices;
	private int[] degrees;
	private int maxDegree;
	private Map<Integer, List<Vertex>> verticesPerDegree;

	private void populateVertices() {
		this.vertices = graph.getVertices().stream().toArray(Vertex[]::new);
	}

	private void populateDegrees() {
		ToIntFunction<Vertex> mapper = v -> graph.degree(v);
		this.degrees = Arrays.stream(vertices).mapToInt(mapper).toArray();
	}

	private void setMaxDegree() {
		this.maxDegree = Arrays.stream(degrees).max().getAsInt();
	}

	private void populateVerticesPerDegree() {
		this.verticesPerDegree = new LinkedHashMap<>();

		for (int k = 0; k <= maxDegree; k++)
			verticesPerDegree.put(k, new ArrayList<>());

		Arrays.stream(vertices).forEach(v -> {
			int i = Arrays.asList(vertices).indexOf(v);
			int degree = degrees[i];
			verticesPerDegree.get(degree).add(v);
		});
	}

	private void init(Graph<Vertex, Edge> graph) {
		this.graph = graph;
		populateVertices();
		populateDegrees();
		setMaxDegree();
		populateVerticesPerDegree();
	}

	private void changeDegree(Vertex vertex, int k) {
		int i = Arrays.asList(vertices).indexOf(vertex);
		int degree = degrees[i];

		if (degree > k) {
			verticesPerDegree.get(degree).remove(vertex);
			verticesPerDegree.get(degree - 1).add(vertex);
			degrees[i] -= 1;
		}
	}

	private void removeVertex(Map<Vertex, Integer> shellIndices, List<Vertex> vertices, int k) {
		if (!vertices.isEmpty()) {
			int index = (int) (Math.random() * vertices.size());
			Vertex vertex = vertices.remove(index);
			shellIndices.put(vertex, k);
			Collection<Vertex> neighbors = graph.getNeighbors(vertex);
			neighbors.forEach(n -> changeDegree(n, k));
		}
	}

	@Override
	public Map<Vertex, Integer> decompose(Graph<Vertex, Edge> graph) {
		init(graph);
		Map<Vertex, Integer> shellIndices = new TreeMap<>();

		for (int k = 0; k <= maxDegree; k++) {
			List<Vertex> verticesOfDegree = verticesPerDegree.get(k);

			do {
				removeVertex(shellIndices, verticesOfDegree, k);
			} while (!verticesOfDegree.isEmpty());
		}

		return shellIndices;
	}

}
