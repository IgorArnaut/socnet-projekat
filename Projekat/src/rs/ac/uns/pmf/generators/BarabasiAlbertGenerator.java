package rs.ac.uns.pmf.generators;

import java.util.function.ToIntFunction;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class BarabasiAlbertGenerator extends Generator {

	private int n;
	private double p;

	public BarabasiAlbertGenerator(int n, double p) {
		this.n = n;
		this.p = p;
	}

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

	private void insertEdges(Vertex target) {
		int targetD = getDegree();
		int dSum = getDegreeSum();

		for (int j = 0; j < targetD; j++) {
			Vertex source = randomVertex();
			Edge edge = new Edge(Integer.toString(id));

			double q = graph.degree(source) / dSum;
			// double probability = graph.degree(source) / (dSum + targetD); ??
			insertEdge(edge, source, target, q);
		}
	}

	private void insertVertices(int m) {
		for (int i = m; i < n; i++) {
			Vertex newVertex = new Vertex(String.format("%03d", i));
			insertEdges(newVertex);
		}
	}

	@Override
	public Graph<Vertex, Edge> generate() {
		int m = (int) (Math.random() * (n / 2));

		ErdosRenyiGenerator generator = new ErdosRenyiGenerator(n, p);
		this.graph = generator.generate();
		this.id = graph.getEdgeCount() - 1;

		insertVertices(m);
		return graph;
	}

}
