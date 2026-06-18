import java.util.*;

public class Evaluator {

    public static double calculatePrecision(List<Map.Entry<Integer, Double>> results, Set<Integer> groundTruth) {
        int retrieved = 0;
        int truePositives = 0;
        for (Map.Entry<Integer, Double> entry : results) {
            if (entry.getValue() > 0) {
                retrieved++;
                if (groundTruth.contains(entry.getKey())) {
                    truePositives++;
                }
            }
        }
        return retrieved == 0 ? 0.0 : (double) truePositives / retrieved;
    }

    public static double calculateRecall(List<Map.Entry<Integer, Double>> results, Set<Integer> groundTruth) {
        int truePositives = 0;
        for (Map.Entry<Integer, Double> entry : results) {
            if (entry.getValue() > 0 && groundTruth.contains(entry.getKey())) {
                truePositives++;
            }
        }
        return groundTruth.isEmpty() ? 0.0 : (double) truePositives / groundTruth.size();
    }

    public static double precisionAtK(List<Map.Entry<Integer, Double>> results, Set<Integer> groundTruth, int k) {
        int truePositives = 0;
        int limit = Math.min(k, results.size());
        for (int i = 0; i < limit; i++) {
            if (groundTruth.contains(results.get(i).getKey())) {
                truePositives++;
            }
        }
        return limit == 0 ? 0.0 : (double) truePositives / limit;
    }

    public static double elevenPointAvgPrecision(List<Map.Entry<Integer, Double>> results, Set<Integer> groundTruth) {
        List<Double> precisions = new ArrayList<>();
        List<Double> recalls = new ArrayList<>();
        int truePositives = 0;
        
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getValue() > 0) {
                if (groundTruth.contains(results.get(i).getKey())) {
                    truePositives++;
                }
                precisions.add((double) truePositives / (i + 1));
                recalls.add((double) truePositives / groundTruth.size());
            }
        }

        double sumInterpolatedPrecision = 0.0;
        double[] recallLevels = {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};

        for (double level : recallLevels) {
            double maxPrecision = 0.0;
            for (int i = 0; i < recalls.size(); i++) {
                if (recalls.get(i) >= level) {
                    maxPrecision = Math.max(maxPrecision, precisions.get(i));
                }
            }
            sumInterpolatedPrecision += maxPrecision;
        }

        return sumInterpolatedPrecision / 11.0;
    }
}