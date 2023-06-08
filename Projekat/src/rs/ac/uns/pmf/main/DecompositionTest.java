package rs.ac.uns.pmf.main;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.BatageljZaversnikDecomposer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.decomposers.StraightforwardDecomposer;
import rs.ac.uns.pmf.generators.BarabasiAlbertGenerator;
import rs.ac.uns.pmf.generators.CorePeripheryGenerator;
import rs.ac.uns.pmf.generators.ErdosRenyiGenerator;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.io.GraphReader;

public class DecompositionTest {

	private Map<String, Graph<Vertex, Edge>> graphs;
	private final Decomposer sfDecomposer = new StraightforwardDecomposer();
	private final Decomposer bzDecomposer = new BatageljZaversnikDecomposer();

	public DecompositionTest() {
		this.graphs = new LinkedHashMap<>();
		graphs.put("Graph 1", GraphReader.readGraphml("resources/graph1.graphml"));
		graphs.put("Graph 2", GraphReader.readGraphml("resources/graph2.graphml"));
		graphs.put("Graph 3", GraphReader.readGraphml("resources/graph3.graphml"));

		graphs.put("ER Graph", new ErdosRenyiGenerator(200, 0.05).generate());
		graphs.put("BA Graph", new BarabasiAlbertGenerator(200, 0.05).generate());
		graphs.put("Core Periphery Graph", new CorePeripheryGenerator(200, 0.1, 0.05).generate());
	}

	public void test() {
		graphs.forEach((k, v) -> {
			System.out.println(k);
			// System.out.println(v);
			Map<Vertex, Integer> sfIndices = sfDecomposer.decompose(v);
			Map<Vertex, Integer> bzIndices = bzDecomposer.decompose(v);

			System.out.println("SF: " + sfIndices);
			System.out.println("BZ: " + bzIndices);

			System.out.println("Equal shell indices: " + sfIndices.equals(bzIndices));
			System.out.println();
		});
	}

}
