package rs.ac.uns.pmf.generators;

import java.util.function.ToIntFunction;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class BarabasiAlbertGenerator extends Generator {

	private int getDegreeSum() {
		ToIntFunction<Vertex> function = v -> graph.degree(v);
		return graph.getVertices().stream().mapToInt(function).sum();
	}

	private int getMaxDegree() {
		ToIntFunction<Vertex> function = v -> graph.degree(v);
		return graph.getVertices().stream().mapToInt(function).max().getAsInt();
	}

	private int getNewDegree() {
		int maxDegree = getMaxDegree();
		return (int) Math.round(Math.sqrt(maxDegree));
	}

	private Vertex randomVertex() {
		int index = (int) (Math.random() * graph.getVertexCount());
		return graph.getVertices().toArray(Vertex[]::new)[index];
	}

	private void insertEdge(Pair<Vertex> pair, Edge edge, double probability) {
		if (RANDOM.nextDouble() <= probability)
			graph.addEdge(edge, pair);
	}

	private void insertEdges(Vertex newVertex, int i) {
		int newDegree = getNewDegree();
		int degreeSum = getDegreeSum();

		for (int j = 0; j < newDegree; j++) {
			Vertex vertex = randomVertex();

			Edge edge = new Edge();
			Pair<Vertex> pair = new Pair<>(newVertex, vertex);

			double probability = graph.degree(vertex) / degreeSum;
			insertEdge(pair, edge, probability);
		}
	}

	private void insertNewVertices(int erVertexCount, int vertexCount) {
		for (int i = erVertexCount; i < vertexCount; i++) {
			Vertex newVertex = new Vertex(String.format("%03d", i));
			insertEdges(newVertex, i);
		}
	}

	public Graph<Vertex, Edge> generate(int vertexCount, double probability) {
		int erVertexCount = (int) (Math.random() * (vertexCount / 2));

		ErdosRenyiGenerator generator = new ErdosRenyiGenerator();
		this.graph = generator.generate(erVertexCount, probability);

		insertNewVertices(erVertexCount, vertexCount);
		return graph;
	}

}
