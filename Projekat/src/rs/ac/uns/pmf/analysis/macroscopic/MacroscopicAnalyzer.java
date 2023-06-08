package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Reporter;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.MacroscopicExporter;

public abstract class MacroscopicAnalyzer implements Reporter {

	protected Map<Integer, Double> results;
	protected MacroscopicExporter exporter = new MacroscopicExporter();

	public MacroscopicAnalyzer() {
		this.results = new LinkedHashMap<>();
	}

	public abstract void analyze(List<Graph<Vertex, Edge>> cores);

	@Override
	public void report(String folder) {
	}

}
