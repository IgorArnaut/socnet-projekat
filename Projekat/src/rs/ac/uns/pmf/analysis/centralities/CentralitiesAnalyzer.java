package rs.ac.uns.pmf.analysis.centralities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Reporter;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.CentralitiesExporter;
import rs.ac.uns.pmf.utils.Triple;

public abstract class CentralitiesAnalyzer implements Reporter {

	protected double correlation;
	protected List<Triple<String, Integer, Double>> results;
	protected CentralitiesExporter exporter = new CentralitiesExporter();

	public CentralitiesAnalyzer() {
		this.results = new ArrayList<>();
	}

	public abstract void analyze(Graph<Vertex, Edge> graph, Map<Vertex, Integer> shellIndices);

	private double calculateCorrelation(List<Double> xs, List<Double> ys) {
		SpearmansCorrelation correlation = new SpearmansCorrelation();
		double[] xArray = xs.stream().mapToDouble(d -> d).toArray();
		double[] yArray = ys.stream().mapToDouble(d -> d).toArray();
		return correlation.correlation(xArray, yArray);
	}

	protected double getCorrelation() {
		List<Double> xs = new ArrayList<>();
		List<Double> ys = new ArrayList<>();
		results.forEach(t -> {
			xs.add(1.0 * t.second());
			ys.add(t.third());
		});
		return calculateCorrelation(xs, ys);
	}

	@Override
	public void report(String folder) {
	}

}
