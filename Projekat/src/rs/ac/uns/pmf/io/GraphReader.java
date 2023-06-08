package rs.ac.uns.pmf.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.common.base.Function;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.graphml.EdgeMetadata;
import edu.uci.ics.jung.io.graphml.GraphMLReader2;
import edu.uci.ics.jung.io.graphml.GraphMetadata;
import edu.uci.ics.jung.io.graphml.HyperEdgeMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class GraphReader {

	/**
	 * Reads a graph from the given file.
	 * 
	 * @param file
	 * @return
	 */
	public static Graph<Vertex, Edge> readGraphml(String file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			Function<NodeMetadata, Vertex> vt = v -> {
				return new Vertex(v.getId());
			};
			Function<EdgeMetadata, Edge> et = e -> {
				return new Edge(e.getId());
			};
			Function<HyperEdgeMetadata, Edge> het = he -> {
				return new Edge(he.getId());
			};
			Function<GraphMetadata, Graph<Vertex, Edge>> gt = g -> {
				return new UndirectedSparseGraph<>();
			};

			GraphMLReader2<Graph<Vertex, Edge>, Vertex, Edge> reader = new GraphMLReader2<Graph<Vertex, Edge>, Vertex, Edge>(
					br, gt, vt, et, het);
			return reader.readGraph();
		} catch (IOException | GraphIOException e) {
			e.printStackTrace();
			return null;
		}
	}

}