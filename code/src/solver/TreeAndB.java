package solver;

import java.util.ArrayList;

public class TreeAndB {
	int B;
	ArrayList<Integer> edges;
	
	public TreeAndB(int B, ArrayList<Integer> edges, int lastEdge) {
		this.B = B;
		this.edges = new ArrayList<Integer>(edges);
		this.edges.add(lastEdge);
	}
	
	public ArrayList<Integer> getEdges() {
		return edges;
	}
	
	public int getB() {
		return B;
	}
	
	public static TreeAndB getBest(TreeAndB t1, TreeAndB t2) {
		if (t1 == null) return t2;
		if (t2 == null) return t1;
		if (t1.B < t2.B) {
			return t1;
		} else {
			return t2;
		}
	}
	
	
	@Override
	public String toString() {
		return String.format(
				"B: %d\nEdges: %s",
				B,
				getEdgeString()
				);
	}
	
	private String getEdgeString() {
		String output = "[";
		for (Integer i : edges) {
			output += (i+1)+", ";
		}
		return output.substring(0,output.length()-2)+"]";
	}
}