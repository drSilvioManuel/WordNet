import java.util.HashMap;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

	private final Digraph graph;

	/**
	 * constructor takes a digraph (not necessarily a DAG)
	 */
	public SAP(Digraph G) {
		throwExceptionIfWrong(G);
		graph = new Digraph(G);

	}

	/**
	 * length of shortest ancestral path between v and w; -1 if no such path
	 */
	public int length(int v, int w) {
		throwExceptionIfWrong(v, w);
		return retrieveInstance(v, w).getLength();
	}

	/**
	 * a common ancestor of v and w that participates in a shortest ancestral
	 * path; -1 if no such path
	 */
	public int ancestor(int v, int w) {
		throwExceptionIfWrong(v, w);
		return retrieveInstance(v, w).getAncestor();
	}

	/**
	 * length of shortest ancestral path between any vertex in v and any vertex
	 * in w; -1 if no such path
	 */
	public int length(Iterable<Integer> v, Iterable<Integer> w) {
		throwExceptionIfWrong(v);
		throwExceptionIfWrong(w);
		return retrieveInstance(v, w).getLength();
	}

	/**
	 * a common ancestor that participates in shortest ancestral path; -1 if no
	 * such path
	 */
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		throwExceptionIfWrong(v);
		throwExceptionIfWrong(w);
		return retrieveInstance(v, w).getAncestor();
	}

	public static void main(String[] args) {
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

	private void throwExceptionIfWrong(Iterable<Integer> args) {
		if (args == null)
			throw new IllegalArgumentException();
		for (int arg : args) {
			if (arg < 0 || arg > graph.V() - 1)
				throw new IllegalArgumentException();
		}
	}

	private void throwExceptionIfWrong(int... args) {
		Bag<Integer> bag = new Bag<Integer>();
		for (int arg : args)
			bag.add(arg);

		throwExceptionIfWrong(bag);
	}

	private void throwExceptionIfWrong(Digraph G) {
		if (G == null)
			throw new IllegalArgumentException();
	}

	private BFS retrieveInstance(int v, int w) {
		return new BFS(v, w);
	}

	private BFS retrieveInstance(Iterable<Integer> vs, Iterable<Integer> ws) {
		return new BFS(vs, ws);
	}

	private class BFS {
		// marked[v] = is there an
		// s->v path?
		boolean[] marked1 = new boolean[graph.V()];
		
		// distTo[v] = length of shortest
		// s->v path
		int[] distTo1 = new int[graph.V()];
											
		// edgeTo[v] = last edge on shortest
		// s->v path
		int[] edgeTo1 = new int[graph.V()];
											
		Queue<Integer> q1 = new Queue<Integer>();

		boolean[] marked2 = new boolean[graph.V()];
		int[] distTo2 = new int[graph.V()];
		int[] edgeTo2 = new int[graph.V()];
		Queue<Integer> q2 = new Queue<Integer>();

		final int ancestor;

		final HashMap<Integer, Integer> vertices = new HashMap<Integer, Integer>();

		BFS(int v, int w) {
			marked1[v] = true;
			distTo1[v] = 0;
			q1.enqueue(v);
			vertices.put(v, 0);

			marked2[w] = true;
			distTo2[w] = 0;
			q2.enqueue(w);
			vertices.put(w, 0);

			ancestor = searchOfShortestPath();
		}

		BFS(Iterable<Integer> vs, Iterable<Integer> ws) {
			for (int v : vs) {
				marked1[v] = true;
				distTo1[v] = 0;
				q1.enqueue(v);
				vertices.put(v, 0);
			}

			for (int w : ws) {
				marked2[w] = true;
				distTo2[w] = 0;
				q2.enqueue(w);
				vertices.put(w, 0);
			}

			ancestor = searchOfShortestPath();
		}

		int getAncestor() {
			return ancestor;
		}

		int getLength() {
			return ancestor == -1 ? -1 : vertices.get(ancestor);
		}

		int searchOfShortestPath() {
			int foundAncestor = -1;
			
			if (1 == q2.size() && 1 == q1.size() && q1.peek() == q2.peek()) {
				foundAncestor = q1.peek();
				return foundAncestor;
			}
			
			while (!q1.isEmpty() || !q2.isEmpty()) {

				foundAncestor = findShortestPath(q1, marked1, edgeTo1, distTo1);
				if (-1 != foundAncestor) break;
				
				foundAncestor = findShortestPath(q2, marked2, edgeTo2, distTo2);
				if (-1 != foundAncestor) break;
					
			}
			return foundAncestor;
		}

		/**
		 * 
		 * @param q
		 * @param v
		 * @param marked
		 * @param edgeTo
		 * @param distTo
		 * @return
		 */
		int findShortestPath(Queue<Integer> q, boolean[] marked, int[] edgeTo, int[] distTo) {
			int pathFound = -1;
			
			if (q.isEmpty()) return pathFound;
			
			int v = q.dequeue();
			for (int w : graph.adj(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
					marked[w] = true;
					q.enqueue(w);
					if (vertices.containsKey(w)) pathFound = w;
					else vertices.put(w, distTo[w]);
				}
			}
			return pathFound;
		}
	}
}
