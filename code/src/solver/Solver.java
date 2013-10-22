package solver;

import java.util.HashSet;
import java.util.Set;

import parsing.Edge;
import parsing.UWG;

public class Solver {
	public static Solution best = new Solution(Integer.MAX_VALUE);
	
	public static boolean test(UWG graph) {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < 37; i++) {
			set.add(i);
		}
		set.remove(3);
		set.remove(8);
		set.remove(10);
		set.remove(22);
		set.remove(25);
		set.remove(27);
		set.remove(29);
		set.remove(34);
		set.remove(37);
		return hasLoop(graph, 37, set);
	}
	
	public static Solution solve(UWG graph) {
		recursiveSolve(graph, 0, new HashSet<Integer>(), 0, 0);
		return best;
	}
	
	private static void recursiveSolve(UWG graph, int k, Set<Integer> contracted, int st, int mot) {
		// Stop if the current st/mot are too big compared to the current best
		if (best.isBetterThan(Math.max(st,mot))) {
			return;
		}
		
		// Stop if the current graph contains a loop
		if (hasLoop(graph, k, contracted)) {
			return;
		}
		
		// If only one nodes are left, is a solution
		if (contracted.size() >= graph.getN()-1) {
			best = new Solution(Math.max(st, mot), contracted);
			return;
		}
		
		// Check if graph is still connected
		if (!isConnected(graph, k, contracted)) {
			return;
		}
		
		// Cut an edge and continue
		recursiveSolve(graph, k+1, new HashSet<Integer>(contracted), st, mot);
		
		// Contract an edge and continue
		HashSet<Integer> newContracted = new HashSet<Integer>(contracted);
		newContracted.add(k);
		recursiveSolve(graph, k+1, newContracted, st+graph.getW(k), mot+graph.getMirrorW(k));
	}

	private static boolean isConnected(UWG graph, int k, Set<Integer> contracted) {	
		HashSet<Integer> explored = new HashSet<Integer>();
		recursiveDFS(graph, k, contracted, explored, 0);
		
		return explored.size() == graph.getN();
	}
	
	private static void recursiveDFS(UWG graph, int k, Set<Integer> contracted, HashSet<Integer> explored, int node) {
		explored.add(node);
		
		int other;
		for (Edge edge : graph.getNeighbours(node)) {
			other = edge.getOther(node);
			
			// Skip node if already explored
			if (explored.contains(other)) {
				continue;
			}
			
			// Skip edge to this node if it has been removed (not contracted)
			if (graph.getEdges().indexOf(edge) < k && !contracted.contains(edge.getId())) {
				continue;
			}
			
			recursiveDFS(graph, k, contracted, explored, other);
		}
	}
	
	private static boolean hasLoop(UWG graph, int k, Set<Integer> contracted) {
		if (contracted.isEmpty()) return false;
		
		HashSet<Integer> explored = new HashSet<Integer>();
		HashSet<Integer> exploredEdges = new HashSet<Integer>();
		Edge e = graph.getEdge(contracted.iterator().next());

		return recursiveDFSloop(graph, k, contracted, explored, exploredEdges, e.getN1());
	}
	
	private static boolean recursiveDFSloop(UWG graph, int k, Set<Integer> contracted, HashSet<Integer> explored, 
			HashSet<Integer> exploredEdges, int node) {
		explored.add(node);
		
		int other;
		for (Edge e : graph.getNeighbours(node)) {
			if (!contracted.contains(e.getId())) continue;
			if (exploredEdges.contains(e.getId())) continue;
			
			exploredEdges.add(e.getId());
			
			other = e.getOther(node);
			if (explored.contains(other)) {
				return true;
			}
			
			if (recursiveDFSloop(graph, k, contracted, explored, exploredEdges, other)){
				return true;
			}
		}
		
		return false;
	}	

}