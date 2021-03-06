package parsing;

public class Edge {
	private int id, n1, n2, w;

	public Edge(int id, int n1, int n2, int w) {
		this.id = id;
		this.n1 = n1;
		this.n2 = n2;
		this.w = w;
	}
	
	public boolean connectsTo(int n) {
		return n==n1 || n==n2;
	}
	
	public int getOther(int n) {
		if (n == n1) return n2;
		if (n == n2) return n1;
		return -1;
	}
	
	public int getN1() {
		return n1;
	}
	
	public int getN2() {
		return n2;
	}
	
	public int getWeight() {
		return w;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return String.format(
				"e%d = {%d,%d}, w(e%d) = %d",
				id+1, getN1()+1, getN2()+1, id+1, getWeight()
				);
	}
}
