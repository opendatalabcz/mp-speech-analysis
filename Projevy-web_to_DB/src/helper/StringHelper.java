package helper;

import java.text.Normalizer;

public class StringHelper {

    public static int wordCount(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String[] words = input.split("\\s+");
        return words.length;
    }

    public static boolean equalsIgnoreCaseAndDiacritics(String s1, String s2) {
        String normalizedS1 = Normalizer.normalize(s1, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String normalizedS2 = Normalizer.normalize(s2, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        if(normalizedS2 == "stropnicky") {
            int a = 8;
        }
        return normalizedS1.equalsIgnoreCase(normalizedS2);
    }
}
