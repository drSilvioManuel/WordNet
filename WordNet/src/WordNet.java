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
	private final Map<String, List<Integer>> mapper = new HashMap<>();
	private final Map<Integer, String> mapperReverced = new HashMap<>();
	private final SAP sap;

	/**
	 * constructor takes the name of the two input files
	 */
	public WordNet(String synsets, String hypernyms) {
		throwExceptionIfNull(synsets, hypernyms);

		String contentSynset = new In(synsets).readAll();
		String contentHypernym = new In(hypernyms).readAll();

		StringTokenizer lineTokensSynset = new StringTokenizer(contentSynset);
		StringTokenizer lineTokensHypernym = new StringTokenizer(contentHypernym);

		V = lineTokensSynset.countTokens();
		DG = new Digraph(V);
		if (V != lineTokensHypernym.countTokens())
			throw new IllegalArgumentException();

		while (lineTokensSynset.hasMoreTokens()) {

			String lineSynset = lineTokensSynset.nextToken();
			String lineHypernym = lineTokensHypernym.nextToken();

			StringTokenizer tokenizerSynset = new StringTokenizer(lineSynset, ",");
			StringTokenizer tokenizerHypernym = new StringTokenizer(lineHypernym, ",");

			int id = Integer.parseInt(tokenizerSynset.nextToken());
			int _id = Integer.parseInt(tokenizerHypernym.nextToken());

			if (id != _id)
				throw new IllegalArgumentException();

			StringTokenizer wordTokenizer = new StringTokenizer(tokenizerSynset.nextToken());
			while (wordTokenizer.hasMoreTokens()) {
				String word = wordTokenizer.nextToken();
				mapperReverced.put(id, word);
				if (mapper.containsKey(word))
					mapper.get(word).add(id);
				else
					mapper.put(word, Arrays.asList(id));
			}
			StringTokenizer hypernymTokenizer = new StringTokenizer(tokenizerHypernym.nextToken());
			while (hypernymTokenizer.hasMoreTokens()) {
				int hypernym = Integer.parseInt(hypernymTokenizer.nextToken());
				DG.addEdge(id, hypernym);
			}
		}
		sap = new SAP(DG);
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
		throwExceptionIfNull(word);
		return mapper.containsKey(word);
	}

	/**
	 * distance between nounA and nounB (defined below)
	 */
	public int distance(String nounA, String nounB) {
		throwExceptionIfNull(nounA, nounB);
		return sap.length(mapper.get(nounA), mapper.get(nounB));
	}

	/**
	 * a synset (second field of synsets.txt) that is the common ancestor of
	 * nounA and nounB in a shortest ancestral path (defined below)
	 */
	public String sap(String nounA, String nounB) {
		throwExceptionIfNull(nounA, nounB);
		return mapperReverced.get(sap.ancestor(mapper.get(nounA), mapper.get(nounB)));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private static void throwExceptionIfNull(Object ...args) {
		for (Object arg : args) if (arg == null) throw new IllegalArgumentException();
	}
}
