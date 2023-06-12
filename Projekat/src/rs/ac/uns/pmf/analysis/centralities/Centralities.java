package rs.ac.uns.pmf.analysis.centralities;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public abstract class Centralities {

	public abstract double[] getValues(Graph<Vertex, Edge> graph, Vertex[] vertices);

}
