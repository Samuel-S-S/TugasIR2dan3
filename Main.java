
import java.util.*;

public class Main {
    public static void main(String[] args) {
        DataLoader loader = new DataLoader();
        Map<Integer, String> documents = loader.loadDataset("dataset");

        if (documents.isEmpty()) {
            System.out.println("Dataset kosong atau tidak ditemukan.");
            return;
        }

        Preprocessing preprocessor = new Preprocessing();
        IndexData indexData = new IndexData();
        indexData.buildIndex(documents, preprocessor);

        BIM bim = new BIM();
        TwoPoisson twoPoisson = new TwoPoisson();
        BM25 bm25 = new BM25();
        BM10 bm10 = new BM10();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Masukkan kueri pencarian (atau ketik 'exit' untuk keluar): ");
            String q = scanner.nextLine();

            if (q.trim().equalsIgnoreCase("exit")) {
                break;
            }

            if (q.trim().isEmpty()) {
                continue;
            }

            System.out.println("\nMenampilkan hasil untuk: \"" + q + "\"");

            List<Map.Entry<Integer, Double>> bimResults = bim.calculate(q, indexData, preprocessor);
            System.out.println("Top 5 BIM:");
            for (int i = 0; i < Math.min(5, bimResults.size()); i++) {
                System.out.println("Doc " + bimResults.get(i).getKey() + " -> Score: " + bimResults.get(i).getValue());
            }

            List<Map.Entry<Integer, Double>> twoPoissonResults = twoPoisson.calculate(q, indexData, preprocessor);
            System.out.println("\nTop 5 Two-Poisson:");
            for (int i = 0; i < Math.min(5, twoPoissonResults.size()); i++) {
                System.out.println("Doc " + twoPoissonResults.get(i).getKey() + " -> Score: " + twoPoissonResults.get(i).getValue());
            }

            List<Map.Entry<Integer, Double>> bm25Results = bm25.calculate(q, indexData, preprocessor, 1.5, 0.75);
            System.out.println("\nTop 5 BM25:");
            for (int i = 0; i < Math.min(5, bm25Results.size()); i++) {
                System.out.println("Doc " + bm25Results.get(i).getKey() + " -> Score: " + bm25Results.get(i).getValue());
            }

            List<Map.Entry<Integer, Double>> bm10Results = bm10.calculate(q, indexData, preprocessor);
            System.out.println("\nTop 5 BM10:");
            for (int i = 0; i < Math.min(5, bm10Results.size()); i++) {
                System.out.println("Doc " + bm10Results.get(i).getKey() + " -> Score: " + bm10Results.get(i).getValue());
            }

            System.out.println("------------------------------\n");
        }
        
        scanner.close();
    }
}