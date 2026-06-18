import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataLoader {
    public Map<Integer, String> loadDataset(String folderPath) {
        Map<Integer, String> documents = new HashMap<>();
        File folder = new File(folderPath);
        
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.startsWith("doc") && name.endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    try {
                        String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath()))).trim();
                        String fileName = file.getName();
                        int docId = Integer.parseInt(fileName.replace("doc", "").replace(".txt", ""));
                        documents.put(docId, content);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Folder dataset tidak ditemukan.");
        }
        return documents;
    }
}