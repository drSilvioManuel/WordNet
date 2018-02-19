import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

public class WordNet {

    private final Map<String, List<Integer>> mapper = new HashMap<String, List<Integer>>();
    private final Map<Integer, String> mapperReverced = new HashMap<Integer, String>();
    private final SAP sap;

    /**
     * constructor takes the name of the two input files
     */
    public WordNet(String synsets, String hypernyms) {
        throwExceptionIfNull(synsets, hypernyms);

        String contentSynset = new In(synsets).readAll();
        StringTokenizer lineTokensSynset = new StringTokenizer(contentSynset, "\n");

        int V = lineTokensSynset.countTokens();
        Digraph DG = new Digraph(V);
        while (lineTokensSynset.hasMoreTokens()) {
            String lineSynset = lineTokensSynset.nextToken();
            StringTokenizer tokenizerSynset = new StringTokenizer(lineSynset, ",");
            int id = Integer.parseInt(tokenizerSynset.nextToken());
            StringTokenizer wordTokenizer = new StringTokenizer(tokenizerSynset.nextToken());
            while (wordTokenizer.hasMoreTokens()) {
                String word = wordTokenizer.nextToken();
                mapperReverced.put(id, word);
                if (mapper.containsKey(word))
                    mapper.get(word).add(id);
                else
                    mapper.put(word, new ArrayList<Integer>(Arrays.asList(id)));
            }
        }

        String contentHypernym = new In(hypernyms).readAll();
        StringTokenizer lineTokensHypernym = new StringTokenizer(contentHypernym, "\n");
        while (lineTokensHypernym.hasMoreTokens()) {
            String lineHypernym = lineTokensHypernym.nextToken();
            StringTokenizer tokenizerHypernym = new StringTokenizer(lineHypernym, ",");
            int id = Integer.parseInt(tokenizerHypernym.nextToken());
            while (tokenizerHypernym.hasMoreTokens()) {
                int hypernym = Integer.parseInt(tokenizerHypernym.nextToken());
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

    private static void throwExceptionIfNull(Object... args) {
        for (Object arg : args)
            if (arg == null)
                throw new IllegalArgumentException();
    }
}
