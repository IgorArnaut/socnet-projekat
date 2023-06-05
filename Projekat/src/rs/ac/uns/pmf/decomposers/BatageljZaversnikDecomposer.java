package rs.ac.uns.pmf.decomposers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class BatageljZaversnikDecomposer extends Decomposer {

	private Vertex[] vertices;
	private int[] degrees;
	private Map<Integer, List<Vertex>> verticesPerDegree;

	private int maxDegree;

	private void init(Graph<Vertex, Edge> graph) {
		this.vertices = graph.getVertices().stream().toArray(Vertex[]::new);
		this.degrees = Arrays.stream(vertices).mapToInt(v -> graph.degree(v)).toArray();
		this.maxDegree = Arrays.stream(degrees).max().getAsInt();
		this.verticesPerDegree = new TreeMap<>();
		this.shellIndices = new TreeMap<>();

		for (int i = 0; i <= maxDegree; i++)
			verticesPerDegree.put(i, new ArrayList<>());

		for (Vertex v : vertices) {
			int degree = graph.degree(v);
			verticesPerDegree.get(degree).add(v);
		}
	}

	private boolean isEmpty(List<Vertex> vertices) {
		if (vertices == null)
			return true;

		if (vertices.isEmpty())
			return true;

		return false;
	}

	private void changeDegree(Vertex neighbor, int i) {
		int index = Arrays.asList(vertices).indexOf(neighbor);
		int degree = degrees[index];

		if (degree > i) {
			verticesPerDegree.get(degree).remove(neighbor);
			verticesPerDegree.get(degree - 1).add(neighbor);
			degrees[index] -= 1;
		}
	}

	private Vertex removeVertex(Graph<Vertex, Edge> graph, List<Vertex> verticesOfDegree, int i) {
		Vertex vertex = verticesOfDegree.remove(0);
		Collection<Vertex> neighbors = graph.getNeighbors(vertex);

		for (Vertex neighbor : neighbors)
			changeDegree(neighbor, i);

		return vertex;
	}

	@Override
	public Map<Vertex, Integer> decompose(Graph<Vertex, Edge> graph) {
		init(graph);

		for (int i = 0; i <= maxDegree; i++) {
			if (!isEmpty(verticesPerDegree.get(i))) {
				List<Vertex> verticesOfDegree = verticesPerDegree.get(i);

				do {
					Vertex vertex = removeVertex(graph, verticesOfDegree, i);
					shellIndices.put(vertex, i);
				} while (!verticesOfDegree.isEmpty());
			}
		}

		return shellIndices;
	}

}
