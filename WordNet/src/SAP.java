import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private boolean[] marked;  // marked[v] = is there an s->v path?
    private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
    private int[] distTo;      // distTo[v] = length of shortest s->v path
    
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
        marked[s1] = true;
        distTo[s1] = 0;
        q1.enqueue(s1);
        
        marked[s2] = true;
        distTo[s2] = 0;
        q2.enqueue(s2);
        
        while (!q1.isEmpty() && !q2.isEmpty()) {
            int v1 = q1.dequeue();
            int v2 = q2.dequeue();
            
            if (v1 == v2) break;
            
            for (int w1 : G.adj(v1)) {
                if (!marked[w1]) {
                    edgeTo[w1] = v1;
                    distTo[w1] = distTo[v1] + 1;
                    marked[w1] = true;
                    q1.enqueue(w1);
                }
            }
            for (int w2 : G.adj(v2)) {
                if (!marked[w2]) {
                    edgeTo[w2] = v2;
                    distTo[w2] = distTo[v2] + 1;
                    marked[w2] = true;
                    q1.enqueue(w2);
                }
            }
        }
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
