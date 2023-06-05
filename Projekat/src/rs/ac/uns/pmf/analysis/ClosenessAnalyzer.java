package rs.ac.uns.pmf.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ClosenessAnalyzer extends Analyzer {

	private Map<Vertex, Integer> shellIndices;
	private ClosenessCentrality<Vertex, Edge> centrality;
	private SpearmansCorrelation correlation = new SpearmansCorrelation();

	public ClosenessAnalyzer(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		super(graph);
		this.shellIndices = decomposer.decompose(graph);
		this.centrality = new ClosenessCentrality<Vertex, Edge>(graph);
	}

	private double calculateCorrelation(List<Double> xs, List<Double> ys) {
		double[] xArray = xs.stream().mapToDouble(d -> d).toArray();
		double[] yArray = ys.stream().mapToDouble(d -> d).toArray();
		return correlation.correlation(xArray, yArray);
	}

	private double getSpearmansCorrelation() {
		List<Double> xs = new ArrayList<>();
		List<Double> ys = new ArrayList<>();
		shellIndices.forEach((k, v) -> {
			xs.add(1.0 * v);
			ys.add(centrality.getVertexScore(k));
		});
		return calculateCorrelation(xs, ys);
	}

	@Override
	public void analyze() {
		rows.add("Id;Shell index;Closeness centrality");

		for (Vertex vertex : shellIndices.keySet()) {
			int id = Integer.parseInt(vertex.getId());
			int x = shellIndices.get(vertex);
			double y = centrality.getVertexScore(vertex);
			String row = String.format("%d;%d;%.2f", id, x, y);
			rows.add(row);
		}

		double correlation = getSpearmansCorrelation();
		rows.add("Spearman's correlation;" + correlation);
	}

}
