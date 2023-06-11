package rs.ac.uns.pmf.io;

import java.util.ArrayList;
import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.CentralitiesWrapper;

public class CentralitiesExporter extends CSVExporter {

	private CentralitiesWrapper wrapper;

	@Override
	protected void insertHeader() {
		String[] header = new String[6];
		header[0] = "Node ID";
		header[1] = "Shell index";
		header[2] = "Degree";
		header[3] = "Betweenness centrality";
		header[4] = "Closeness centrality";
		header[5] = "Eigenvector centrality";
		data.add(header);
	}

	@Override
	protected void insertRows() {
		for (int i = 0; i < wrapper.getVertices().length; i++) {
			String[] row = new String[6];
			row[0] = wrapper.getVertices()[i].getId();
			row[1] = "" + wrapper.getIndices()[i];
			row[2] = "" + wrapper.getDegrees()[i];
			row[3] = "" + wrapper.getBetweennessCentralities()[i];
			row[4] = "" + wrapper.getClosenessCentralities()[i];
			row[5] = "" + wrapper.getEigenvectorCentralities()[i];
			data.add(row);
		}
	}

	private void insertCorrelations() {
		double dc = wrapper.getSpearmansCorrelation(wrapper.getIndices(), wrapper.getDegrees());
		double bcc = wrapper.getSpearmansCorrelation(wrapper.getIndices(), wrapper.getBetweennessCentralities());
		double ccc = wrapper.getSpearmansCorrelation(wrapper.getIndices(), wrapper.getClosenessCentralities());
		double ecc = wrapper.getSpearmansCorrelation(wrapper.getIndices(), wrapper.getEigenvectorCentralities());

		data.add(new String[] { "Spearman's correlation", "Degree", "" + dc });
		data.add(new String[] { "Spearman's correlation", "Betweenness", "" + bcc });
		data.add(new String[] { "Spearman's correlation", "Closeness", "" + ccc });
		data.add(new String[] { "Spearman's correlation", "Eigenvector", "" + ecc });
	}

	public CentralitiesExporter(Graph<Vertex, Edge> graph, Map<Vertex, Integer> shellIndices) {
		this.wrapper = new CentralitiesWrapper(graph, shellIndices);
		this.data = new ArrayList<>();
		insertData();
		insertCorrelations();
	}

}
