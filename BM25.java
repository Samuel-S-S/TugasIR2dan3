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
                    if (termFreq > 0) {
                        double docFreq = index.df.getOrDefault(term, 0);
                        
                        double R = 0.0;
                        double rt = 0.0;
                        
                        double num = (rt + 0.5) * (index.N - R + 1.0);
                        double den = (R + 1.0) * (docFreq - rt + 0.5);
                        double wt = Math.log10(num / den);
                        
                        double numBM = termFreq * (k1 + 1) * wt;
                        double denBM = termFreq + k1 * (1 - b + b * (dl / index.avgdl));
                        score += numBM / denBM;
                    }
                }
            }
            scores.put(docId, score);
        }
        return MathUtils.sortScores(scores);
    }
}