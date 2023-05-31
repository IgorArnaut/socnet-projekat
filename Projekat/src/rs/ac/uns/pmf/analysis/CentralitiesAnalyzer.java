package rs.ac.uns.pmf.analysis;

import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.plotters.BetweennessPlotter;
import rs.ac.uns.pmf.analysis.plotters.ClosenessPlotter;
import rs.ac.uns.pmf.analysis.plotters.DegreePlotter;
import rs.ac.uns.pmf.analysis.plotters.EigenvectorPlotter;
import rs.ac.uns.pmf.decomposers.Decomposer;

public class CentralitiesAnalyzer<V, E> implements Analyzer<V, E> {

	private Map<V, Integer> shellIndices;
	private DegreePlotter<V, E> degreePlotter;
	private BetweennessPlotter<V, E> betweennessPlotter;
	private ClosenessPlotter<V, E> closenessPlotter;
	private EigenvectorPlotter<V, E> eigenvectorPlotter;

	private void init(Graph<V, E> graph, Decomposer<V, E> decomposer) {
		this.shellIndices = decomposer.decompose(graph);
		this.degreePlotter = new DegreePlotter<>(graph, shellIndices);
		this.betweennessPlotter = new BetweennessPlotter<>(graph, shellIndices);
		this.closenessPlotter = new ClosenessPlotter<>(graph, shellIndices);
		this.eigenvectorPlotter = new EigenvectorPlotter<>(graph, shellIndices);
	}

	@Override
	public void analyze(Graph<V, E> graph, Decomposer<V, E> decomposer) {
		init(graph, decomposer);

		degreePlotter.plot();
		betweennessPlotter.plot();
		closenessPlotter.plot();
		eigenvectorPlotter.plot();

		double degreeCorrelation = degreePlotter.getSpearmanCorrelation();
		double betweennessCorrelation = betweennessPlotter.getSpearmanCorrelation();
		double closenessCorrelation = closenessPlotter.getSpearmanCorrelation();
		double eigenvectorCorrelation = eigenvectorPlotter.getSpearmanCorrelation();

		System.out.format("Shell index --> degree: %.2f\n", degreeCorrelation);
		System.out.format("Shell index --> betweenness centrality: %.2f\n", closenessCorrelation);
		System.out.format("Shell index --> closeness centrality: %.2f\n", betweennessCorrelation);
		System.out.format("Shell index --> eigenvector centrality: %.2f\n", eigenvectorCorrelation);
	}

}
