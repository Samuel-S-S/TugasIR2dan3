import java.util.*;

public class Preprocessing {
    private Set<String> stopwords;

    public Preprocessing() {
        stopwords = new HashSet<>(Arrays.asList(
            "a", "an", "and", "are", "as", "at", "be", "but", "by", "for",
            "if", "in", "into", "is", "it", "no", "not", "of", "on", "or",
            "such", "that", "the", "their", "then", "there", "these",
            "they", "this", "to", "was", "will", "with"
        ));
    }

    public List<String> process(String text) {
        List<String> tokens = new ArrayList<>();
        String[] words = text.toLowerCase().split("[^a-z]+");
        for (String word : words) {
            if (word.length() > 1 && !stopwords.contains(word)) {
                String stemmedWord = stem(word);
                tokens.add(stemmedWord);
            }
        }
        return tokens;
    }

    private String stem(String word) {
        if (word == null || word.length() <= 2) {
            return word;
        }
        

        if (word.endsWith("ies") && word.length() > 4) {
            word = word.substring(0, word.length() - 3) + "y";
        } else if (word.endsWith("es") && !word.endsWith("ces") && !word.endsWith("ses") && word.length() > 3) {
            word = word.substring(0, word.length() - 2); 
        } else if (word.endsWith("s") && !word.endsWith("ss") && !word.endsWith("us") && word.length() > 2) {
            word = word.substring(0, word.length() - 1);
        }
        
        
        if (word.endsWith("ing") && word.length() > 4) {
            word = word.substring(0, word.length() - 3); 
        } else if (word.endsWith("ed") && word.length() > 3) {
            word = word.substring(0, word.length() - 2);
        } else if (word.endsWith("ly") && word.length() > 3) {
            word = word.substring(0, word.length() - 2);
        }
        
        return word;
    }
}