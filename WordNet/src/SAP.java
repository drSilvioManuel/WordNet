import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

	private final Map<String, BFS> pull = new HashMap<String, BFS>();
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
		String key;
		if (v < w)
			key = Integer.toString(v) + "_" + Integer.toString(w);
		else
			key = Integer.toString(w) + "_" + Integer.toString(v);
		if (!pull.containsKey(key))
			pull.put(key, new BFS(v, w));

		return pull.get(key);
	}

	private BFS retrieveInstance(Iterable<Integer> vs, Iterable<Integer> ws) {
		String key;
		if (vs.hashCode() < ws.hashCode())
			key = Integer.toString(vs.hashCode()) + Integer.toString(ws.hashCode());
		else
			key = Integer.toString(ws.hashCode()) + Integer.toString(vs.hashCode());
		if (!pull.containsKey(key))
			pull.put(key, new BFS(vs, ws));

		return pull.get(key);
	}

	private class BFS {

		boolean[] marked1 = new boolean[graph.V()];// marked[v] = is there an
													// s->v path?
		int[] distTo1 = new int[graph.V()];// edgeTo[v] = last edge on shortest
											// s->v path
		int[] edgeTo1 = new int[graph.V()];// distTo[v] = length of shortest
											// s->v path
		Queue<Integer> q1 = new Queue<Integer>();

		boolean[] marked2 = new boolean[graph.V()];
		int[] distTo2 = new int[graph.V()];
		int[] edgeTo2 = new int[graph.V()];
		Queue<Integer> q2 = new Queue<Integer>();

		final int ancestor;

		final Set<Integer> vertices = new HashSet<Integer>();

		BFS(int v, int w) {
			marked1[v] = true;
			distTo1[v] = 0;
			q1.enqueue(v);

			marked2[w] = true;
			distTo2[w] = 0;
			q2.enqueue(w);

			ancestor = searchOfShortestPath();
		}

		BFS(Iterable<Integer> vs, Iterable<Integer> ws) {
			for (int v : vs) {
				marked1[v] = true;
				distTo1[v] = 0;
				q1.enqueue(v);
			}

			for (int w : ws) {
				marked2[w] = true;
				distTo2[w] = 0;
				q2.enqueue(w);
			}

			ancestor = searchOfShortestPath();
		}

		int getAncestor() {
			return ancestor;
		}

		int getLength() {
			return vertices.size();
		}

		int searchOfShortestPath() {
			int ancestor = -1;

			while (!q1.isEmpty() && !q2.isEmpty() && -1 == ancestor) {
				int v1 = q1.dequeue();
				int v2 = q2.dequeue();

				ancestor = findShortestPath(q1, v1, marked1, edgeTo1, distTo1);
				if (-1 == ancestor)
					ancestor = findShortestPath(q2, v2, marked2, edgeTo2, distTo2);
			}
			return ancestor;
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
		int findShortestPath(Queue<Integer> q, int v, boolean[] marked, int[] edgeTo, int[] distTo) {
			int pathFound = -1;
			for (int w : graph.adj(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					distTo[w] = distTo[v] + 1;
					marked[w] = true;
					q.enqueue(w);
					if (vertices.contains(w))
						pathFound = w;
					else
						vertices.add(w);
				}
			}
			return pathFound;
		}
	}
}
