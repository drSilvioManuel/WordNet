import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private boolean[] marked1; // marked[v] = is there an s->v path?
	private int[] edgeTo1; // edgeTo[v] = last edge on shortest s->v path
	private int[] distTo1; // distTo[v] = length of shortest s->v path
	
	private boolean[] marked2;
	private int[] edgeTo2;
	private int[] distTo2;
	
	private Set<Integer> vertices;
	
	/**
	 * constructor takes a digraph (not necessarily a DAG)
	 */
	public SAP(Digraph G) {
	}

	/**
	 * length of shortest ancestral path between v and w; -1 if no such path
	 */
	public int length(int v, int w) {
		throw new RuntimeException();
	}

	/**
	 * a common ancestor of v and w that participates in a shortest ancestral
	 * path; -1 if no such path
	 */
	public int ancestor(int v, int w) {
		throw new RuntimeException();
	}

	/**
	 * length of shortest ancestral path between any vertex in v and any vertex
	 * in w; -1 if no such path
	 */
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		throw new RuntimeException();
	}

	/**
	 * a common ancestor that participates in shortest ancestral path; -1 if no
	 * such path
	 */
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		throw new RuntimeException();
	}

	// BFS from single source
	private void bfs(Digraph G, int s1, int s2) {
		Queue<Integer> q1 = new Queue<Integer>();
		Queue<Integer> q2 = new Queue<Integer>();
		marked1[s1] = true;
		distTo1[s1] = 0;
		q1.enqueue(s1);

		marked2[s2] = true;
		distTo2[s2] = 0;
		q2.enqueue(s2);
		
		vertices = new HashSet<>();
		boolean pathFound = false;

		while (!q1.isEmpty() && !q2.isEmpty() && !pathFound) {
			int v1 = q1.dequeue();
			int v2 = q2.dequeue();

			pathFound = walkThroughAdjustedVertices(G, q1, v1, marked1, edgeTo1, distTo1);
			if (!pathFound) pathFound = walkThroughAdjustedVertices(G, q2, v2, marked2, edgeTo2, distTo2);
		}
	}

	/**
	 * 
	 * @param G
	 * @param q
	 * @param v
	 * @param marked
	 * @param edgeTo
	 * @param distTo
	 * @return
	 */
	private boolean walkThroughAdjustedVertices(
			Digraph G, Queue<Integer> q, int v, boolean[] marked, int[] edgeTo, int[] distTo) {
		boolean pathFound = false;
		for (int w : G.adj(v)) {
			if (!marked[w]) {
				edgeTo[w] = v;
				distTo[w] = distTo[v] + 1;
				marked[w] = true;
				q.enqueue(w);
				if (vertices.contains(w)) pathFound = true;
				else vertices.add(w);
			}
		}
		return pathFound;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		In in = new In(args[0]);
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);
		while (!StdIn.isEmpty()) {
			int v = StdIn.readInt();
			int w = StdIn.readInt();
			int length = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		}
	}

}
