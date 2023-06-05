package rs.ac.uns.pmf.graph;

public class Vertex implements Comparable<Vertex> {

	private String id;

	public Vertex(String id) {
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
	public int compareTo(Vertex other) {
		return Integer.parseInt(this.id) - Integer.parseInt(other.id);
	}

}
