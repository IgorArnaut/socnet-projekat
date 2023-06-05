package rs.ac.uns.pmf.analysis;

import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.GiantComponent;

public class DiameterAnalyzer extends Analyzer {

	private Decomposer decomposer;
	private GiantComponent component = new GiantComponent();

	public DiameterAnalyzer(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		super(graph);
		this.decomposer = decomposer;
	}

	private double getDiameter(Graph<Vertex, Edge> graph) {
		if (graph == null)
			return 0.0;

		return DistanceStatistics.diameter(graph);
	}

	@Override
	public void analyze() {
		rows.add("Core;Giant component diameter");
		Graph<Vertex, Edge> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			Graph<Vertex, Edge> giantComponent = component.getGiantComponent(graph);
			double y = getDiameter(giantComponent);
			String row = String.format("%d;%.2f", x, y);
			rows.add(row);
			x++;
		} while (core.getVertexCount() > 0);
	}

}
