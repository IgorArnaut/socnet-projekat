package rs.ac.uns.pmf.analysis.centralities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import edu.uci.ics.jung.algorithms.scoring.EigenvectorCentrality;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Analyzer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class EigenvectorAnalyzer implements Analyzer {

	private Map<Vertex, Integer> shellIndices;
	private EigenvectorCentrality<Vertex, Edge> centrality;
	private SpearmansCorrelation correlation = new SpearmansCorrelation();

	private double calculateCorrelation(List<Double> xs, List<Double> ys) {
		double[] xArray = xs.stream().mapToDouble(d -> d).toArray();
		double[] yArray = ys.stream().mapToDouble(d -> d).toArray();
		return correlation.correlation(xArray, yArray);
	}

	public double getSpearmansCorrelation() {
		List<Double> xs = new ArrayList<>();
		List<Double> ys = new ArrayList<>();
		shellIndices.forEach((k, v) -> {
			xs.add(1.0 * v);
			ys.add(centrality.getVertexScore(k));
		});
		return calculateCorrelation(xs, ys);
	}

	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		this.shellIndices = decomposer.decompose(graph);
		this.centrality = new EigenvectorCentrality<Vertex, Edge>(graph);

		for (Vertex vertex : shellIndices.keySet()) {
			double x = shellIndices.get(vertex);
			double y = centrality.getVertexScore(vertex);
		}

		double correlation = getSpearmansCorrelation();
	}

}