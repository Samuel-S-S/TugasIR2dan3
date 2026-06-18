import java.util.*;

public class BIM {
    public List<Map.Entry<Integer, Double>> calculate(String query, IndexData index, Preprocessing preprocessing) {
        Map<Integer, Double> scores = new HashMap<>();
        List<String> qTokens = preprocessing.process(query);

        for (Integer docId : index.docs.keySet()) {
            double score = 0.0;
            Map<String, Integer> docTf = index.tf.get(docId);
            for (String term : qTokens) {
                if (docTf.containsKey(term) && docTf.get(term) > 0) {
                    double docFreq = index.df.getOrDefault(term, 0);
                    double wt = Math.log((index.N - docFreq + 0.5) / (docFreq + 0.5) + 1.0);
                    score += wt;
                }
            }
            scores.put(docId, score);
        }
        return MathUtils.sortScores(scores);
    }
}