package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;
import java.util.function.ToDoubleFunction;

import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.GiantComponent;

public class Diameter implements Macroscopic {

	private GiantComponent gc = new GiantComponent();

	private double calculate(Graph<Vertex, Edge> graph) {
		if (graph == null)
			return 0.0;

		return DistanceStatistics.diameter(graph);
	}

	private double getDiameter(Graph<Vertex, Edge> graph) {
		Graph<Vertex, Edge> component = gc.getGiantComponent(graph);
		return calculate(component);
	}

	public double[] getValues(List<Graph<Vertex, Edge>> cores) {
		ToDoubleFunction<Graph<Vertex, Edge>> mapper = c -> getDiameter(c);
		return cores.stream().mapToDouble(mapper).toArray();
	}

}
