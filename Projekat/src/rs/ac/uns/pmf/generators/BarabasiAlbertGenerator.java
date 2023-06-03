package rs.ac.uns.pmf.generators;

import java.util.function.ToIntFunction;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class BarabasiAlbertGenerator extends Generator {

	// 1.
	private int getMaxDegree() {
		ToIntFunction<Vertex> function = vertex -> graph.degree(vertex);
		return graph.getVertices().stream().mapToInt(function).max().getAsInt();
	}

	// 2.
	private int getNewDegree() {
		// 1.
		int maxDegree = getMaxDegree();
		return (int) Math.round(Math.sqrt(maxDegree));
	}

	// 3.
	private Vertex getRandomVertex() {
		int index = (int) (Math.random() * graph.getVertexCount());
		return graph.getVertices().toArray(Vertex[]::new)[index];
	}

	// 4.
	private int getDegreeSum() {
		ToIntFunction<Vertex> function = vertex -> graph.degree(vertex);
		return graph.getVertices().stream().mapToInt(function).sum();
	}

	// 5.
	private void insertEdge(Vertex vertex, Vertex newVertex, double probability) {
		if (RANDOM.nextDouble() <= probability) {
			Edge edge = new Edge();
			graph.addEdge(edge, vertex, newVertex);
		}
	}

	// 6.
	private void insertEdges(Vertex newVertex, int newDegree) {
		for (int j = 0; j < newDegree; j++) {
			// 3.
			Vertex vertex = getRandomVertex();
			int degree = graph.degree(vertex);

			// 4.
			int degreeSum = getDegreeSum();

			double probability = 1.0 * degree / degreeSum;
			// 5.
			insertEdge(vertex, newVertex, probability);
		}
	}

	// 7.
	private void insertNewVertices(int vertexCount, int vertexCountER) {
		for (int i = vertexCountER; i < vertexCount; i++) {
			Vertex newVertex = new Vertex(String.format("%03d", i));

			// 2.
			int newDegree = getNewDegree();

			// 6.
			insertEdges(newVertex, newDegree);
		}
	}

	public Graph<Vertex, Edge> generate(int vertexCount, double probability) {
		int vertexCountER = (int) (Math.random() * (vertexCount / 2));

		ErdosRenyiGenerator generator = new ErdosRenyiGenerator();
		this.graph = generator.generate(vertexCountER, probability);

		// 7.
		insertNewVertices(vertexCount, vertexCountER);
		return graph;
	}

}
