package rs.ac.uns.pmf.analysis;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class EdgeCountAnalyzer extends Analyzer {

	private Decomposer decomposer;

	public EdgeCountAnalyzer(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		super(graph);
		this.decomposer = decomposer;
	}

	@Override
	public void analyze() {
		rows.add("Core;Edge count");
		Graph<Vertex, Edge> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			int y = core.getEdgeCount();
			String row = String.format("%d;%d", x, y);
			rows.add(row);
			x++;
		} while (core.getVertexCount() > 0);
	}

}
