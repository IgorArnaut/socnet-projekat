package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;
import java.util.function.ToDoubleFunction;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.GiantComponent;

public class EdgePercentage implements Macroscopic {

	private GiantComponent gc = new GiantComponent();

	private double calculate(Graph<Vertex, Edge> graph, Graph<Vertex, Edge> component) {
		if (component == null)
			return 0.0;

		double e = graph.getVertexCount();
		double ec = component.getVertexCount();
		return (100.0 * ec) / e;
	}

	private double getPercentage(Graph<Vertex, Edge> graph) {
		Graph<Vertex, Edge> component = gc.getGiantComponent(graph);
		return calculate(graph, component);
	}

	@Override
	public double[] getValues(List<Graph<Vertex, Edge>> cores) {
		ToDoubleFunction<Graph<Vertex, Edge>> mapper = c -> getPercentage(c);
		return cores.stream().mapToDouble(mapper).toArray();
	}

}
