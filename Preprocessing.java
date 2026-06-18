import java.util.*;
import java.util.regex.*;
public class Preprocessing {
    public List<String> process(String text) {
        String lowerText = text.toLowerCase();
        Matcher m = Pattern.compile("\\b[a-z]+\\b").matcher(lowerText);
        Set<String> stopwords = new HashSet<>(Arrays.asList("the", "is", "in", "and", "to", "of", "a", "for", "on", "with"));
        List<String> tokens = new ArrayList<>();
        while (m.find()) {
            String token = m.group();
            if (!stopwords.contains(token)) {
                tokens.add(token);
            }
        }
        return tokens;
    }
}