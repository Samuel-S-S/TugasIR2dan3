import java.util.*;

public class BM25 {
    public List<Map.Entry<Integer, Double>> calculate(String query, IndexData index, Preprocessing preprocessing, double k1, double b) {
        Map<Integer, Double> scores = new HashMap<>();
        List<String> qTokens = preprocessing.process(query);

        for (Integer docId : index.docs.keySet()) {
            double score = 0.0;
            double dl = index.docLengths.get(docId);
            Map<String, Integer> docTf = index.tf.get(docId);

            for (String term : qTokens) {
                if (docTf.containsKey(term)) {
                    double termFreq = docTf.get(term);
                    double docFreq = index.df.getOrDefault(term, 0);
                    if (termFreq > 0) {
                        double wt = Math.log((index.N - docFreq + 0.5) / (docFreq + 0.5) + 1.0);
                        double num = termFreq * (k1 + 1) * wt;
                        double den = termFreq + k1 * (1 - b + b * (dl / index.avgdl));
                        score += num / den;
                    }
                }
            }
            scores.put(docId, score);
        }
        return MathUtils.sortScores(scores);
    }
}