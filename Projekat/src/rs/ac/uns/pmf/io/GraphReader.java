package rs.ac.uns.pmf.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.collections15.Transformer;

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

	public static Graph<Vertex, Edge> readGraphml(String file) throws GraphIOException {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			Transformer<GraphMetadata, UndirectedSparseGraph<Vertex, Edge>> gt = new Transformer<GraphMetadata, UndirectedSparseGraph<Vertex, Edge>>() {
				public UndirectedSparseGraph<Vertex, Edge> transform(GraphMetadata metadata) {
					return new UndirectedSparseGraph<Vertex, Edge>();
				}
			};

			Transformer<NodeMetadata, Vertex> nt = new Transformer<NodeMetadata, Vertex>() {
				public Vertex transform(NodeMetadata metadata) {
					return new Vertex(metadata.getId());
				}
			};

			Transformer<EdgeMetadata, Edge> lt = new Transformer<EdgeMetadata, Edge>() {
				public Edge transform(EdgeMetadata metadata) {
					return new Edge();
				}
			};

			Transformer<HyperEdgeMetadata, Edge> ht = new Transformer<HyperEdgeMetadata, Edge>() {
				public Edge transform(HyperEdgeMetadata metadata) {
					return new Edge();
				}
			};

			GraphMLReader2<UndirectedSparseGraph<Vertex, Edge>, Vertex, Edge> reader = new GraphMLReader2<UndirectedSparseGraph<Vertex, Edge>, Vertex, Edge>(
					br, gt, nt, lt, ht);
			return reader.readGraph();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}