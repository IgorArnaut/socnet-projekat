package rs.ac.uns.pmf.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.GraphMLWriter;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class GraphWriter {

	public static void writeGraphml(Graph<Node, Link> graph, String filename) {
		GraphMLWriter<Node, Link> writer = new GraphMLWriter<Node, Link>();

		writer.setVertexIDs(new Transformer<Node, String>() {
			public String transform(Node node) {
				return node.getId();
			}
		});

		writer.setEdgeIDs(new Transformer<Link, String>() {
			public String transform(Link link) {
				return link.getLabel();
			}
		});

		writer.addVertexData("label", "label", "", new Transformer<Node, String>() {
			public String transform(Node node) {
				return node.getId();
			}
		});

		writer.addEdgeData("label", "label", "", new Transformer<Link, String>() {
			public String transform(Link node) {
				return node.getLabel();
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
