package rs.ac.uns.pmf.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.algorithms.metrics.Metrics;
import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;

public class MacroscopicAnalyzer<V, E> implements Analyzer<V, E> {

	private double getDensity(Graph<V, E> graph) {
		int vertexCount = graph.getVertexCount();
		int edgeCount = graph.getEdgeCount();

		if (vertexCount == 0)
			return 0.0;

		return 2.0 * edgeCount / (vertexCount * (vertexCount - 1));
	}

	private int getComponentCount(Graph<V, E> graph) {
		Set<Set<V>> clusters = new WeakComponentClusterer<V, E>().apply(graph);
		return clusters.size();
	}

	private Graph<V, E> getGiantComponent(Graph<V, E> graph) {
		Set<Set<V>> clusters = new WeakComponentClusterer<V, E>().apply(graph);

		if (clusters.isEmpty())
			return null;

		Set<V> cluster = clusters.stream().toList().get(0);
		return FilterUtils.createInducedSubgraph(cluster, graph);
	}

	private double getPercentage(Graph<V, E> graph) {
		if (graph == null)
			return 0.0;

		int n = graph.getVertexCount();
		int l = graph.getEdgeCount();

		if (l == 0)
			return 0;

		return (100.0 * n) / l;
	}

	private double getSmallWorldCoefficient(Graph<V, E> graph) {
		if (graph == null)
			return 0.0;

		UnweightedShortestPath<V, E> usp = new UnweightedShortestPath<>(graph);
		List<V> vertices = new ArrayList<>();
		graph.getVertices().forEach(v -> vertices.add(v));
		double distanceSum = 0.0;

		for (int j = 0; j < vertices.size() - 1; j++) {
			for (int k = j + 1; k < vertices.size(); k++)
				distanceSum += usp.getDistance(vertices.get(j), vertices.get(k)).doubleValue();
		}

		int n = vertices.size();

		if (n - 1 == 0)
			return 0.0;

		return (1.0 * distanceSum) / (n * (n - 1));
	}

	private double getAvgClusteringCoefficient(Graph<V, E> graph) {
		int n = graph.getVertexCount();

		if (n == 0)
			return 0.0;

		Map<V, Double> clusteringCoefficients = Metrics.clusteringCoefficients(graph);
		return clusteringCoefficients.values().stream().mapToDouble(d -> d).average().getAsDouble();
	}

	private void analyzeGiantComponent(Graph<V, E> graph) {
		// TODO Procenat cvorova i linkova u najvecoj komponenti
		Graph<V, E> giantComponent = getGiantComponent(graph);
		double percentage = getPercentage(giantComponent);
		System.out.format("Giant component percentage: %.2f%%\n", percentage);

		// TODO Small world koeficijent najvece komponente
		double l = getSmallWorldCoefficient(giantComponent);
		System.out.format("Giant component small world coefficient: %.2f\n", l);

		// TODO Dijametar najvece komponente
		double diameter = giantComponent != null ? DistanceStatistics.diameter(giantComponent) : 0.0;
		System.out.format("Giant component diameter: %.2f\n", diameter);
	}

	@Override
	public void analyze(Graph<V, E> graph, Decomposer<V, E> decomposer) {
		Graph<V, E> core = null;
		int i = 0;

		do {
			core = decomposer.getKCore(graph, i);
			System.out.format("Core: %d\n", i);

			// TODO Broj cvorova
			int vertexCount = core.getVertexCount();
			System.out.format("Core vertex count: %d\n", vertexCount);

			// TODO Broj linkova
			int edgeCount = core.getEdgeCount();
			System.out.format("Core link count: %d\n", edgeCount);

			// TODO Gustina
			double d = getDensity(core);
			System.out.format("Core density: %.2f\n", d);

			// TODO Broj povezanih komponenti
			int componentCount = getComponentCount(core);
			System.out.format("Core component count: %d\n", componentCount);

			analyzeGiantComponent(core);
			
			// TODO Prosecan koeficijent klasterisanja
			double avgClusteringCoefficient = getAvgClusteringCoefficient(core);
			System.out.format("Core average clustering coefficient: %.2f\n", avgClusteringCoefficient);

			i++;
			System.out.println();
		} while (core.getVertexCount() > 0);
	}

}
