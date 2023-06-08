package rs.ac.uns.pmf.analysis.macroscopic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Analyzer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.CSVExporter;

public class MacroscopicAnalyzer implements Analyzer, CSVExporter {

	protected Map<Integer, Double> results;

	public MacroscopicAnalyzer() {
		this.results = new LinkedHashMap<>();
	}

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
	}

	@Override
	public void exportToCSV(String folder, String file, String header) {
		new File(folder).mkdirs();

		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(new FileWriter(folder + file)))) {
			bw.write(header);
			bw.newLine();

			for (Integer r : results.keySet()) {
				bw.append(String.format("%d;%.2f", r, results.get(r)));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void report(String folder) {
	}

}
