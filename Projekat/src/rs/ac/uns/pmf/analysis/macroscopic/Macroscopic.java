package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public interface Macroscopic {

	double[] getValues(List<Graph<Vertex, Edge>> cores);

}
