package rs.ac.uns.pmf.analysis;

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
		int n = graph.getVertexCount();
		int l = graph.getEdgeCount();

		if (n == 0)
			return 0.0;

		return (2.0 * l) / (n * (n - 1));
	}

	private int getComponentCount(Graph<V, E> graph) {
		Set<Set<V>> clusters = new WeakComponentClusterer<V, E>().transform(graph);
		return clusters.size();
	}

	private Graph<V, E> getGiantComponent(Graph<V, E> graph) {
		Set<Set<V>> clusters = new WeakComponentClusterer<V, E>().transform(graph);

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
		return l > 0 ? (100.0 * n) / l : 0.0;
	}

	private double getSmallWorldCoefficient(Graph<V, E> graph) {
		if (graph == null)
			return 0.0;

		UnweightedShortestPath<V, E> usp = new UnweightedShortestPath<V, E>(graph);
		List<V> vertices = graph.getVertices().stream().toList();
		double distanceSum = 0.0;

		for (int j = 0; j < vertices.size() - 1; j++) {
			for (int k = j + 1; k < vertices.size(); k++)
				distanceSum += usp.getDistance(vertices.get(j), vertices.get(k)).doubleValue();
		}

		int n = vertices.size();
		return n - 1 > 0 ? (1.0 * distanceSum) / (n * (n - 1)) : 0.0;
	}

	private double getAvgClusteringCoefficient(Graph<V, E> graph) {
		int n = graph.getVertexCount();
		Map<V, Double> clusteringCoefficients = Metrics.clusteringCoefficients(graph);

		if (n == 0)
			return 0.0;

		return clusteringCoefficients.values().stream().mapToDouble(d -> d).average().getAsDouble();
	}

	@Override
	public void analyze(Graph<V, E> graph, Decomposer<V, E> decomposer) {
		Graph<V, E> core = null;
		int i = 0;

		do {
			core = decomposer.getKCore(graph, i);
			System.out.format("Core: %d\n", i);

			// TODO Broj cvorova
			int n = core.getVertexCount();
			System.out.format("Vertex count: %d\n", n);

			// TODO Broj linkova
			int l = core.getEdgeCount();
			System.out.format("Link count: %d\n", l);

			// TODO Gustina
			double density = getDensity(core);
			System.out.format("Density: %.2f\n", density);

			// TODO Broj povezanih komponenti
			int componentCount = getComponentCount(core);
			System.out.format("Component count: %d\n", componentCount);

			// TODO Procenat cvorova i linkova u najvecoj komponenti
			Graph<V, E> giantComponent = getGiantComponent(core);
			double percentage = getPercentage(giantComponent);
			System.out.format("Percentage: %.2f\n", percentage);

			// TODO Small world koeficijent najvece komponente
			double swc = getSmallWorldCoefficient(giantComponent);

			System.out.format("Small world coefficient: %.2f\n", swc);
			// TODO Dijametar najvece komponente
			double diameter = giantComponent != null ? DistanceStatistics.diameter(giantComponent) : 0.0;
			System.out.format("Giant component diameter: %.2f\n", diameter);

			// TODO Prosecan koeficijent klasterisanja
			double avgClusteringCoefficient = getAvgClusteringCoefficient(core);
			System.out.format("Average clustering coefficient: %.2f\n", avgClusteringCoefficient);

			System.out.println();
			i++;
		} while (core.getVertexCount() > 0);
	}

}
