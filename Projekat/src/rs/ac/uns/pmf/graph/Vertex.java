package rs.ac.uns.pmf.graph;

public class Vertex {

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

}
