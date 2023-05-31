package rs.ac.uns.pmf.generators;

import java.util.function.ToIntFunction;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class BarabasiAlbertGenerator extends Generator {

	private int getMaxDegree() {
		ToIntFunction<Vertex> function = vertex -> graph.degree(vertex);
		return graph.getVertices().stream().mapToInt(function).max().getAsInt();
	}

	private int getNewDegree() {
		int maxDegree = getMaxDegree();
		return (int) Math.round(Math.sqrt(maxDegree));
	}

	private Vertex getRandomVertex() {
		int index = (int) (Math.random() * graph.getVertexCount());
		return graph.getVertices().toArray(Vertex[]::new)[index];
	}

	private int getDegreeSum() {
		ToIntFunction<Vertex> function = vertex -> graph.degree(vertex);
		return graph.getVertices().stream().mapToInt(function).sum();
	}

	private void insertEdge(Vertex vertex, Vertex newVertex, double probability) {
		if (RANDOM.nextDouble() <= probability) {
			Edge edge = new Edge();
			graph.addEdge(edge, vertex, newVertex);
		}
	}

	private void insertEdges(Vertex newVertex, int newDegree) {
		for (int j = 0; j < newDegree; j++) {
			Vertex vertex = getRandomVertex();
			int degree = graph.degree(vertex);
			int degreeSum = getDegreeSum();
			double probability = 1.0 * degree / degreeSum;
			insertEdge(vertex, newVertex, probability);
		}
	}

	private void insertNewVertices(int vertexCount, int vertexCountER) {
		for (int i = vertexCountER; i < vertexCount; i++) {
			Vertex newVertex = new Vertex(String.format("%03d", i));
			int newDegree = getNewDegree();
			insertEdges(newVertex, newDegree);
		}
	}

	public Graph<Vertex, Edge> generate(int vertexCount, double probability) {
		int vertexCountER = (int) (Math.random() * (vertexCount / 2));
		ErdosRenyiGenerator generator = new ErdosRenyiGenerator();
		this.graph = generator.generate(vertexCountER, probability);
		insertNewVertices(vertexCount, vertexCountER);
		return graph;
	}

}
