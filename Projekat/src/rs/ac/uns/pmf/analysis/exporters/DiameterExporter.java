package rs.ac.uns.pmf.analysis.exporters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.GiantComponent;

public class DiameterExporter extends Exporter {

	private Decomposer decomposer;
	private GiantComponent component = new GiantComponent();

	public DiameterExporter(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		super(graph);
		this.decomposer = decomposer;
	}

	private double getDiameter(Graph<Vertex, Edge> graph) {
		if (graph == null)
			return 0.0;

		return DistanceStatistics.diameter(graph);
	}

	@Override
	public void saveToCSV(String folder, String file) {
		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(new FileWriter("src/" + folder + "/" + file)))) {
			bw.append("Core;Giant component diameter\n");
			Graph<Vertex, Edge> core = null;
			int x = 0;

			do {
				core = decomposer.getKCore(graph, x);
				Graph<Vertex, Edge> giantComponent = component.getGiantComponent(graph);
				double y = getDiameter(giantComponent);
				String row = String.format("%d;%.2f\n", x, y);
				bw.append(row);
				x++;
			} while (core.getVertexCount() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
