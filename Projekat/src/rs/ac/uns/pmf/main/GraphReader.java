package rs.ac.uns.pmf.main;

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
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class GraphReader {

	private static final String LINE = "--";

	public static Graph<Node, Link> readGraphml(String file) throws GraphIOException {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			Transformer<GraphMetadata, UndirectedSparseGraph<Node, Link>> gt = new Transformer<GraphMetadata, UndirectedSparseGraph<Node, Link>>() {
				public UndirectedSparseGraph<Node, Link> transform(GraphMetadata metadata) {
					return new UndirectedSparseGraph<Node, Link>();
				}
			};

			Transformer<NodeMetadata, Node> nt = new Transformer<NodeMetadata, Node>() {
				public Node transform(NodeMetadata metadata) {
					return new Node(metadata.getId());
				}
			};

			Transformer<EdgeMetadata, Link> lt = new Transformer<EdgeMetadata, Link>() {
				public Link transform(EdgeMetadata metadata) {
					String source = metadata.getSource();
					String target = metadata.getTarget();
					return new Link(source + LINE + target);
				}
			};

			Transformer<HyperEdgeMetadata, Link> ht = new Transformer<HyperEdgeMetadata, Link>() {
				public Link transform(HyperEdgeMetadata metadata) {
					String source = metadata.getEndpoints().get(0).getId();
					String target = metadata.getEndpoints().get(1).getId();
					return new Link(source + LINE + target);
				}
			};

			GraphMLReader2<UndirectedSparseGraph<Node, Link>, Node, Link> reader = new GraphMLReader2<UndirectedSparseGraph<Node, Link>, Node, Link>(
					br, gt, nt, lt, ht);
			return reader.readGraph();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}