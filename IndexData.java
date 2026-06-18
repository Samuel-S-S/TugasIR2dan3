import java.util.*;
public class IndexData {
    public Map<Integer, String> docs = new HashMap<>();
    public Map<Integer, Integer> docLengths = new HashMap<>();
    public Map<String, Integer> df = new HashMap<>();
    public Map<Integer, Map<String, Integer>> tf = new HashMap<>();
    public int N = 0;
    public double avgdl = 0.0;

    public void buildIndex(Map<Integer, String> documents, Preprocessing preprocessing) {
        this.N = documents.size();
        double totalLength = 0;

        for (Map.Entry<Integer, String> entry : documents.entrySet()) {
            int docId = entry.getKey();
            String text = entry.getValue();
            this.docs.put(docId, text);
            
            List<String> tokens = preprocessing.process(text);
            this.docLengths.put(docId, tokens.size());
            totalLength += tokens.size();

            Map<String, Integer> termCounts = new HashMap<>();
            for (String token : tokens) {
                termCounts.put(token, termCounts.getOrDefault(token, 0) + 1);
            }

            this.tf.put(docId, termCounts);
            for (String term : termCounts.keySet()) {
                this.df.put(term, this.df.getOrDefault(term, 0) + 1);
            }
        }
        this.avgdl = this.N > 0 ? totalLength / this.N : 0.0;
    }
}