package rs.ac.uns.pmf.utils;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.centralities.Betweenness;
import rs.ac.uns.pmf.analysis.centralities.Centralities;
import rs.ac.uns.pmf.analysis.centralities.Closeness;
import rs.ac.uns.pmf.analysis.centralities.Degree;
import rs.ac.uns.pmf.analysis.centralities.Eigenvector;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class CentralitiesWrapper {

	private Centralities betweenness = new Betweenness();
	private Centralities closeness = new Closeness();
	private Centralities eigenvector = new Eigenvector();
	private Centralities degree = new Degree();

	private Vertex[] vertices;
	private double[] indices;
	private double[] degrees;
	private double[] betweennessCentralities;
	private double[] closenessCentralities;
	private double[] eigenvectorCentralities;

	private void init(Graph<Vertex, Edge> graph, Map<Vertex, Integer> shellIndices) {
		this.vertices = shellIndices.keySet().stream().toArray(Vertex[]::new);
		this.indices = shellIndices.keySet().stream().mapToDouble(v -> shellIndices.get(v)).toArray();
		this.degrees = degree.getValues(graph, vertices);
		
		this.betweennessCentralities = betweenness.getValues(graph, vertices);
		this.closenessCentralities = closeness.getValues(graph, vertices);
		this.eigenvectorCentralities = eigenvector.getValues(graph, vertices);
	}

	public CentralitiesWrapper(Graph<Vertex, Edge> graph, Map<Vertex, Integer> shellIndices) {
		init(graph, shellIndices);
	}

	public Vertex[] getVertices() {
		return vertices;
	}

	public double[] getIndices() {
		return indices;
	}

	public double[] getDegrees() {
		return degrees;
	}

	public double[] getBetweennessCentralities() {
		return betweennessCentralities;
	}

	public double[] getClosenessCentralities() {
		return closenessCentralities;
	}

	public double[] getEigenvectorCentralities() {
		return eigenvectorCentralities;
	}

	public double getSpearmansCorrelation(double[] xs, double[] ys) {
		double min = Arrays.stream(ys).min().getAsDouble();
		double max = Arrays.stream(ys).max().getAsDouble();

		SpearmansCorrelation sc = new SpearmansCorrelation();

		if (min == max)
			return 0.0;
		else
			return sc.correlation(xs, ys);
	}

}
