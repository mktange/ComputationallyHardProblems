package solver;

import java.util.ArrayList;
import java.util.HashSet;

import parsing.Edge;
import parsing.UWG;

public class Solver {
	
	public static Solution solve(UWG graph) {
		return recursiveSolve(graph, 0, new ArrayList<Integer>(), 0, 0);
	}
	
	private static Solution recursiveSolve(UWG graph, int k, ArrayList<Integer> contracted, int st, int mot) {
		System.out.println("======================================");
		System.out.println("Current edge: "+(k+1));
		System.out.println("Contracted: "+UWG.getCorrectedEdges(contracted));
		System.out.println("ST: "+st);
		System.out.println("MOT: "+mot);
		System.out.println();
		
		// If only two nodes are left
		if (contracted.size() >= graph.getN()-2) {
			System.out.println("Only two nodes left!");
			return findBestLink(graph, k, contracted, st, mot);
		}
		
		if (!isConnected(graph, k, contracted)) {
			System.out.println("Not a connected graph!");
			return null;
		}
		
		// Split to the left and right and find best of these two sets of spanning trees
		Solution bestLeft = recursiveSolve(graph, k+1, new ArrayList<Integer>(contracted), st, mot);
		contracted.add(k);
		Solution bestRight = recursiveSolve(graph, k+1, contracted, st+graph.getW(k), mot+graph.getMirrorW(k));
		
		return Solution.getBest(bestLeft, bestRight);
	}

	private static boolean isConnected(UWG graph, int k, ArrayList<Integer> contracted) {	
		HashSet<Integer> explored = new HashSet<Integer>();
		recursiveDFS(graph, k, contracted, explored, 0);
		
//		System.out.println("End explored: "+explored.size());
		return explored.size() == graph.getN();
	}
	
	private static void recursiveDFS(UWG graph, int k, ArrayList<Integer> contracted, HashSet<Integer> explored, int node) {
//		System.out.println("Explored "+(node+1));
		explored.add(node);
		
		int other;
		for (Edge edge : graph.getNeighbours(node)) {
			other = edge.getOther(node);
			
//			System.out.printf("Trying %d -> %d", (node+1), (other+1));
			// Skip node if already explored
			if (explored.contains(other)) {
//				System.out.println(", explored");
				continue;
			}
			
			// Skip edge to this node if it has been removed (not contracted)
			if (graph.getEdges().indexOf(edge) < k && !contracted.contains(other)) {
//				System.out.println(", WAS REMOVED");
				continue;
			}
//			System.out.println();
			
			recursiveDFS(graph, k, contracted, explored, other);
		}
	}

	private static Solution findBestLink(UWG graph, int k, ArrayList<Integer> contracted, int st, int mot) {
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
		
		System.out.println("Best link found to be "+(bestEdge+1)+" with the value "+best);
		return new Solution(best, contracted, bestEdge);
	}
	

}