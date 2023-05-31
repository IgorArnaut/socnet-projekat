package rs.ac.uns.pmf.analysis;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.plotters.ClusteringPlotter;
import rs.ac.uns.pmf.analysis.plotters.ComponentCountPlotter;
import rs.ac.uns.pmf.analysis.plotters.DensityPlotter;
import rs.ac.uns.pmf.analysis.plotters.DiameterPlotter;
import rs.ac.uns.pmf.analysis.plotters.EdgeCountPlotter;
import rs.ac.uns.pmf.analysis.plotters.PercentagePlotter;
import rs.ac.uns.pmf.analysis.plotters.SmallWorldPlotter;
import rs.ac.uns.pmf.analysis.plotters.VertexCountPlotter;
import rs.ac.uns.pmf.decomposers.Decomposer;

public class MacroscopicAnalyzer<V, E> implements Analyzer<V, E> {

	private VertexCountPlotter<V, E> vertexCountPlotter;
	private EdgeCountPlotter<V, E> edgeCountPlotter;
	private DensityPlotter<V, E> densityPlotter;
	private ComponentCountPlotter<V, E> componentCountPlotter;
	private PercentagePlotter<V, E> percentagePlotter;
	private DiameterPlotter<V, E> diameterPlotter;
	private SmallWorldPlotter<V, E> smallWorldPlotter;
	private ClusteringPlotter<V, E> clusteringPlotter;

	private void init(Graph<V, E> graph, Decomposer<V, E> decomposer) {
		decomposer.decompose(graph);
		this.vertexCountPlotter = new VertexCountPlotter<>(graph, decomposer);
		this.edgeCountPlotter = new EdgeCountPlotter<>(graph, decomposer);
		this.densityPlotter = new DensityPlotter<>(graph, decomposer);
		this.componentCountPlotter = new ComponentCountPlotter<>(graph, decomposer);
		this.percentagePlotter = new PercentagePlotter<>(graph, decomposer);
		this.diameterPlotter = new DiameterPlotter<>(graph, decomposer);
		this.smallWorldPlotter = new SmallWorldPlotter<>(graph, decomposer);
		this.clusteringPlotter = new ClusteringPlotter<>(graph, decomposer);
	}

	@Override
	public void analyze(Graph<V, E> graph, Decomposer<V, E> decomposer) {
		init(graph, decomposer);

		vertexCountPlotter.plot();
		edgeCountPlotter.plot();
		densityPlotter.plot();
		componentCountPlotter.plot();
		percentagePlotter.plot();
		diameterPlotter.plot();
		smallWorldPlotter.plot();
		clusteringPlotter.plot();
	}

}
