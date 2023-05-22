package rs.ac.uns.pmf.graph;

public class Node implements Comparable<Node> {

	private String id;

	public Node(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return this.id;
	}

	@Override
	public int compareTo(Node other) {
		return this.id.compareToIgnoreCase(other.id);
	}

}
