import java.util.List;

import edu.princeton.cs.algs4.In;


public class WordNet {

	/** 
	 * constructor takes the name of the two input files 
	 */
	public WordNet(String synsets, String hypernyms) {
		String[] lSynsets = new In(synsets).readAllLines();
		String[] lHypernyms = new In(hypernyms).readAllLines();
		
	}

	/**
	 * returns all WordNet nouns
	 */
	public Iterable<String> nouns() {
		throw new RuntimeException();
	}

	/** 
	 * is the word a WordNet noun? 
	 */
	public boolean isNoun(String word) {
		throw new RuntimeException();
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
	
	private static class Node {
		int synsetId;
		String[] synset;
		String gloss;
	}

}
