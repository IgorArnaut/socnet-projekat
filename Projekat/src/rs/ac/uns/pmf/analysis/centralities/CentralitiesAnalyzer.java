package rs.ac.uns.pmf.analysis.centralities;

import java.util.ArrayList;
import java.util.Arrays;
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

	protected Vertex[] vertices;
	protected double[] xs;
	protected double[] ys;

	protected double correlation;
	protected List<Triple<String, Double, Double>> results;

	protected CentralitiesExporter exporter = new CentralitiesExporter();

	public CentralitiesAnalyzer() {
		this.results = new ArrayList<>();
	}

	protected void insertValues() {
		double min = Arrays.stream(ys).min().getAsDouble();
		double max = Arrays.stream(ys).max().getAsDouble();

		for (int i = 0; i < vertices.length; i++) {
			ys[i] = (ys[i] - min) / (max - min);

			String id = vertices[i].getId();
			double x = xs[i];
			double y = ys[i];
			results.add(new Triple<>(id, x, y));
		}

		if (min == max)
			correlation = 0.0;
		else {
			SpearmansCorrelation sc = new SpearmansCorrelation();
			correlation = sc.correlation(xs, ys);
		}
	}

	public abstract void analyze(Graph<Vertex, Edge> graph, Map<Vertex, Integer> shellIndices);

	@Override
	public void report(String folder) {
	}

}
