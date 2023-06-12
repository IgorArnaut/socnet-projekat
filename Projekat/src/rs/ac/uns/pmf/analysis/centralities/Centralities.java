package rs.ac.uns.pmf.analysis.centralities;

import java.util.Arrays;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public abstract class Centralities {

	private double normalize(double x, double min, double max) {
		return (x - min) / (max - min);
	}

	protected void normalizeAll(double[] xs) {
		double min = Arrays.stream(xs).min().getAsDouble();
		double max = Arrays.stream(xs).max().getAsDouble();
		xs = Arrays.stream(xs).map(x -> normalize(x, min, max)).toArray();
	}

	public abstract double[] getValues(Graph<Vertex, Edge> graph, Vertex[] vertices);

}
