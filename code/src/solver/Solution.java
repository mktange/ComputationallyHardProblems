package solver;

import java.util.ArrayList;

import parsing.UWG;

public class Solution {
	int B;
	ArrayList<Integer> edges;
	
	public Solution(int B, ArrayList<Integer> edges, int lastEdge) {
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
	
	public static Solution getBest(Solution t1, Solution t2) {
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
				UWG.getCorrectedEdges(edges)
				);
	}
}