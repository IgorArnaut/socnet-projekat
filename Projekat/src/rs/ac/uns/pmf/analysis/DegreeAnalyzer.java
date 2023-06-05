package rs.ac.uns.pmf.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class DegreeAnalyzer extends Analyzer {

	private Map<Vertex, Integer> shellIndices;
	private SpearmansCorrelation correlation = new SpearmansCorrelation();

	public DegreeAnalyzer(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		super(graph);
		this.shellIndices = decomposer.decompose(graph);
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
			ys.add(1.0 * graph.degree(k));
		});
		return calculateCorrelation(xs, ys);
	}

	@Override
	public void analyze() {
		rows.add("Id;Shell index;Degree");

		for (Vertex vertex : shellIndices.keySet()) {
			int id = Integer.parseInt(vertex.getId());
			int x = shellIndices.get(vertex);
			double y = graph.degree(vertex);
			String row = String.format("%d;%d;%.2f", id, x, y);
			rows.add(row);
		}

		double correlation = getSpearmansCorrelation();
		rows.add("Spearman's correlation;" + correlation);
	}

}