package solver;

import java.util.ArrayList;
import java.util.HashSet;

import parsing.UWG;

public class Solver {

	public static TreeAndB solve(UWG graph) {
		return recursiveSolve(graph, 0, new ArrayList<Integer>(), 0, 0);
	}
	
	private static TreeAndB recursiveSolve(UWG graph, int k, ArrayList<Integer> contracted, int st, int mot) {
		// If only two nodes are left
		if (contracted.size() >= graph.getN()-2) {
			return findBestLink(graph, k, contracted, st, mot);
		}
		
		if (!isConnected(graph, k, contracted)) {
			return null;
		}
		
		// Split to the left and right and find best of these two sets of spanning trees
		TreeAndB bestLeft = recursiveSolve(graph, k+1, new ArrayList<Integer>(contracted), st, mot);
		contracted.add(k);
		TreeAndB bestRight = recursiveSolve(graph, k+1, contracted, st+graph.getW(k), mot+graph.getMirrorW(k));
		
		return TreeAndB.getBest(bestLeft, bestRight);
	}

	private static boolean isConnected(UWG graph, int k, ArrayList<Integer> contracted) {	
		HashSet<Integer> explored = new HashSet<Integer>();
		recursiveDFS(graph, k, contracted, explored, 0);
		
		return explored.size() == graph.getN();
	}
	
	private static void recursiveDFS(UWG graph, int k, ArrayList<Integer> contracted, HashSet<Integer> explored, int node) {
		for (int other : graph.getNeighbours(node)) {
			// Skip node if already explored
			if (explored.contains(other)) continue;
			
			// Skip edge to this node if it has been removed (not contracted)
			if (other < k && !contracted.contains(other)) continue;
			
			explored.add(other);
			recursiveDFS(graph, k, contracted, explored, other);
		}
	}

	private static TreeAndB findBestLink(UWG graph, int k, ArrayList<Integer> contracted, int st, int mot) {
		// In case no edges are left, return the current st/mot
		if (k >= graph.countEdges()) return null;
		
		// Find optimal spanning tree/mirror of tree pair
		int best = Integer.MAX_VALUE;
		int current;
		int bestEdge = -1;
		for (int i = k; i < graph.countEdges(); i++) {
			current = Math.max(
					st+graph.getEdge(i).getWeight(),
					mot+graph.getMirrorEdge(i).getWeight());
			
			if (current < best)	{
				best = current;
				bestEdge = i;
			}
		}
		
		return new TreeAndB(best, contracted, bestEdge);
	}
	

}