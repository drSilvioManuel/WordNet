import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

	private final WordNet wordNet;

	/**
	 * constructor takes a WordNet object
	 * 
	 * @param wordnet
	 */
	public Outcast(WordNet wordnet) {
		throwExceptionIfNull(wordnet);
		wordNet = wordnet;
	}

	/**
	 * given an array of WordNet nouns, return an outcast
	 * 
	 * @param nouns
	 * @return
	 */
	public String outcast(String[] nouns) {
		throwExceptionIfNull(nouns);
		List<Integer> distances = new ArrayList<>();
		for (int i = 0; i < nouns.length; i++) {
			distances.add(i, 0);
			for (int j = 0; j < nouns.length; j++) {
				if (i == j)
					continue;
				distances.add(i, distances.get(i) + wordNet.distance(nouns[i], nouns[j]));
			}
		}
		int index = IntStream
			.range(0, distances.size())
			.reduce((i, j) -> distances.get(i) < distances.get(j) ? j : i)
			.getAsInt();
		return nouns[index];
	}

	public static void main(String[] args) {
		WordNet wordnet = new WordNet(args[0], args[1]);
		Outcast outcast = new Outcast(wordnet);
		for (int t = 2; t < args.length; t++) {
			In in = new In(args[t]);
			String[] nouns = in.readAllStrings();
			StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		}
	}

	private static void throwExceptionIfNull(Object arg) {
		if (arg == null) throw new IllegalArgumentException();
	}
}
