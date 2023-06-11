package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;
import java.util.function.ToDoubleFunction;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class VertexCount implements Macroscopic {

	@Override
	public double[] getValues(List<Graph<Vertex, Edge>> cores) {
		ToDoubleFunction<Graph<Vertex, Edge>> mapper = c -> c.getVertexCount();
		return cores.stream().mapToDouble(mapper).toArray();
	}

}
