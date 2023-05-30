package rs.ac.uns.pmf.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.GraphMLWriter;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class GraphWriter {

	public static void writeGraphml(Graph<Vertex, Edge> graph, String filename) {
		GraphMLWriter<Vertex, Edge> writer = new GraphMLWriter<Vertex, Edge>();

		writer.setVertexIDs(new Transformer<Vertex, String>() {
			public String transform(Vertex node) {
				return node.getId();
			}
		});

		writer.addVertexData("label", "label", "", new Transformer<Vertex, String>() {
			public String transform(Vertex node) {
				return node.getId();
			}
		});

		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
			writer.save(graph, pw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
