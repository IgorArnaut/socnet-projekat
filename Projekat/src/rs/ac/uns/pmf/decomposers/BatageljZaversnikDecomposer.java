package rs.ac.uns.pmf.decomposers;

import java.util.Map;

import edu.uci.ics.jung.graph.Graph;

public class BatageljZaversnikDecomposer<V, E> extends Decomposer<V, E> {

	@Override
	public Map<V, Integer> decompose(Graph<V, E> graph) {
		sortShellIndices();
		return shellIndices;
	}

}
