import java.util.*;

public class EvaluasiModel {
    public static void main(String[] args) {
        DataLoader loader = new DataLoader();
        Map<Integer, String> documents = loader.loadDataset("dataset");

        if (documents.isEmpty()) {
            return;
        }

        Preprocessing preprocessor = new Preprocessing();
        IndexData indexData = new IndexData();
        indexData.buildIndex(documents, preprocessor);

        BIM bim = new BIM();
        TwoPoisson twoPoisson = new TwoPoisson();
        BM25 bm25 = new BM25();
        BM10 bm10 = new BM10();

        Map<String, Set<Integer>> groundTruth = new HashMap<>();
        
        Set<Integer> q1Docs = new HashSet<>();
        for (int i = 1; i <= 20; i++) q1Docs.add(i);
        groundTruth.put("machine learning and artificial intelligence algorithms", q1Docs);

        Set<Integer> q2Docs = new HashSet<>();
        for (int i = 21; i <= 40; i++) q2Docs.add(i);
        groundTruth.put("mars exploration and solar system planets", q2Docs);

        Set<Integer> q3Docs = new HashSet<>();
        for (int i = 41; i <= 60; i++) q3Docs.add(i);
        groundTruth.put("healthy food vitamins and balanced diet", q3Docs);

        Set<Integer> q4Docs = new HashSet<>();
        for (int i = 61; i <= 80; i++) q4Docs.add(i);
        groundTruth.put("electric cars battery charging range", q4Docs);

        Set<Integer> q5Docs = new HashSet<>();
        for (int i = 81; i <= 100; i++) q5Docs.add(i);
        groundTruth.put("internet protocols and early computers", q5Docs);

        for (Map.Entry<String, Set<Integer>> entry : groundTruth.entrySet()) {
            String q = entry.getKey();
            Set<Integer> gt = entry.getValue();
            System.out.println("Query: " + q);

            List<Map.Entry<Integer, Double>> bimRes = bim.calculate(q, indexData, preprocessor);
            List<Map.Entry<Integer, Double>> tpRes = twoPoisson.calculate(q, indexData, preprocessor);
            List<Map.Entry<Integer, Double>> bm25Res = bm25.calculate(q, indexData, preprocessor, 1.5, 0.75);
            List<Map.Entry<Integer, Double>> bm10Res = bm10.calculate(q, indexData, preprocessor);

            printEvaluation("BIM", bimRes, gt);
            printEvaluation("Two-Poisson", tpRes, gt);
            printEvaluation("BM25", bm25Res, gt);
            printEvaluation("BM10", bm10Res, gt);
            System.out.println("====================================================\n");
        }
    }

    private static void printEvaluation(String modelName, List<Map.Entry<Integer, Double>> results, Set<Integer> gt) {
        double precision = Evaluator.calculatePrecision(results, gt);
        double recall = Evaluator.calculateRecall(results, gt);
        double p5 = Evaluator.precisionAtK(results, gt, 5);
        double p10 = Evaluator.precisionAtK(results, gt, 10);
        double p15 = Evaluator.precisionAtK(results, gt, 15);
        double avgP11 = Evaluator.elevenPointAvgPrecision(results, gt);

        System.out.printf("%-12s | Precision: %.2f | Recall: %.2f | P@5: %.2f | P@10: %.2f | P@15: %.2f | 11-Pt AP: %.2f%n",
                modelName, precision, recall, p5, p10, p15, avgP11);
    }
}