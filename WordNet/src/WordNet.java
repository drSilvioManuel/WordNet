import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;


public class WordNet {

	private final int V;
	private final Digraph DG;
	private final Map<String, List<Integer>> mapper;
	
	/** 
	 * constructor takes the name of the two input files 
	 */
	public WordNet(String synsets, String hypernyms) {
		
		String contentSynset = new In(synsets).readAll();
		String contentHypernym = new In(hypernyms).readAll();
		
		StringTokenizer lineTokensSynset = new StringTokenizer(contentSynset);
		StringTokenizer lineTokensHypernym = new StringTokenizer(contentHypernym);
		
		V = lineTokensSynset.countTokens();
		DG = new Digraph(V);
		mapper = new HashMap<String, List<Integer>>();
		if (V != lineTokensHypernym.countTokens()) throw new IllegalArgumentException();
		
		while (lineTokensSynset.hasMoreTokens()) {
			
			String lineSynset = lineTokensSynset.nextToken();
			String lineHypernym = lineTokensHypernym.nextToken();
			
			StringTokenizer tokenizerSynset = new StringTokenizer(lineSynset, ",");
			StringTokenizer tokenizerHypernym = new StringTokenizer(lineHypernym, ",");
			
			int id = Integer.parseInt(tokenizerSynset.nextToken());
			int _id = Integer.parseInt(tokenizerHypernym.nextToken());
			
			if (id != _id) throw new IllegalArgumentException();
			
			StringTokenizer wordTokenizer = new StringTokenizer(tokenizerSynset.nextToken());
			while (wordTokenizer.hasMoreTokens()) {
				String word = wordTokenizer.nextToken();
				if (mapper.containsKey(word)) mapper.get(word).add(id);
				else mapper.put(word, Arrays.asList(id));
			}
			StringTokenizer hypernymTokenizer = new StringTokenizer(tokenizerHypernym.nextToken());
			while (hypernymTokenizer.hasMoreTokens()) {
				int hypernym = Integer.parseInt(hypernymTokenizer.nextToken());
				DG.addEdge(id, hypernym);
			}
	     }
	}

	/**
	 * returns all WordNet nouns
	 */
	public Iterable<String> nouns() {
		return mapper.keySet();
	}

	/** 
	 * is the word a WordNet noun? 
	 */
	public boolean isNoun(String word) {
		return mapper.containsKey(word);
	}

	/** 
	 * distance between nounA and nounB (defined below)
	 */
	public int distance(String nounA, String nounB) {
		throw new RuntimeException();
	}

	/**
	 * a synset (second field of synsets.txt) that is the common ancestor of
	 * nounA and nounB in a shortest ancestral path (defined below)
	 */
	public String sap(String nounA, String nounB) {
		throw new RuntimeException();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private class Node {
		int synsetId;
		String[] synset;
		int hash;
		
		@Override
		public int hashCode() {
			int hashCode = hash;
			if (hashCode == 0) {
				for(int i = 0; i < synset.length; i++){
					hashCode = 31*hashCode + synset[i].hashCode();
				}
				hash = hashCode;
			}
			return hashCode;
		}
		
		@Override
		public boolean equals(Object that) {
			if (that == null) return false;
			if (this.getClass() != that.getClass()) return false;
			Node thatNode =  (Node)that;
			if (hashCode() == thatNode.hashCode() && synsetId == thatNode.synsetId) return true;
			return false;
		}
	}

}
