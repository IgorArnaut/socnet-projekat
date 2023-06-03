package rs.ac.uns.pmf.analysis.exporters;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public abstract class Exporter {

	protected Graph<Vertex, Edge> graph;

	public Exporter(Graph<Vertex, Edge> graph) {
		this.graph = graph;
	}

	protected abstract void saveToCSV(String folder, String file);
	
}
