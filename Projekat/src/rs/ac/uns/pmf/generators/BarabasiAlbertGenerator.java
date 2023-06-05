package rs.ac.uns.pmf.generators;

import java.util.function.ToIntFunction;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class BarabasiAlbertGenerator extends Generator {

	private int getDegreeSum() {
		ToIntFunction<Vertex> function = v -> graph.degree(v);
		return graph.getVertices().stream().mapToInt(function).sum();
	}

	private int getDegree() {
		ToIntFunction<Vertex> function = v -> graph.degree(v);
		int maxDegree = graph.getVertices().stream().mapToInt(function).max().getAsInt();
		return (int) Math.round(Math.sqrt(maxDegree));
	}

	private Vertex randomVertex() {
		int index = (int) (Math.random() * graph.getVertexCount());
		return graph.getVertices().toArray(Vertex[]::new)[index];
	}

	private void insertEdge(Edge edge, Vertex source, Vertex target, double probability) {
		if (RANDOM.nextDouble() <= probability)
			graph.addEdge(edge, source, target);
	}

	private void insertEdges(Vertex target, int i) {
		String targetId = "" + target;

		int degree = getDegree();
		int degreeSum = getDegreeSum();

		for (int j = 0; j < degree; j++) {
			Vertex source = randomVertex();
			String sourceId = "" + source;

			Edge edge = new Edge(sourceId, targetId);

			double probability = graph.degree(source) / degreeSum;
			insertEdge(edge, source, target, probability);
		}
	}

	private void insertVertices(int erVertexCount, int vertexCount) {
		for (int i = erVertexCount; i < vertexCount; i++) {
			Vertex newVertex = new Vertex(String.format("%03d", i));
			insertEdges(newVertex, i);
		}
	}

	public Graph<Vertex, Edge> generate(int vertexCount, double probability) {
		int erVertexCount = (int) (Math.random() * (vertexCount / 2));

		ErdosRenyiGenerator generator = new ErdosRenyiGenerator();
		this.graph = generator.generate(erVertexCount, probability);

		insertVertices(erVertexCount, vertexCount);
		return graph;
	}

}
