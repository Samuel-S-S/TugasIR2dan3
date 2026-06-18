import java.util.*;
public class MathUtils {
    public static double factorial(int n) {
        if (n <= 1) return 1;
        double fact = 1;
        for (int i = 2; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    public static List<Map.Entry<Integer, Double>> sortScores(Map<Integer, Double> scores) {
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(scores.entrySet());
        list.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
        return list;
    }
}