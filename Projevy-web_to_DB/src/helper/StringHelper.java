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

    //https://www.geeksforgeeks.org/count-words-in-a-given-string/
    static final int OUT = 0;
    static final int IN = 1;
    @Deprecated
    public static int countWords(String str)
    {
        int state = OUT;
        int wc = 0;  // word count
        int i = 0;

        // Scan all characters one by one
        while (i < str.length())
        {
            // If next character is a separator, set the
            // state as OUT
            if (str.charAt(i) == ' ' || str.charAt(i) == '\n'
                    || str.charAt(i) == '\t')
                state = OUT;

                // If next character is not a word separator
                // and state is OUT, then set the state as IN
                // and increment word count
            else if (state == OUT)
            {
                state = IN;
                ++wc;
            }

            // Move to next character
            ++i;
        }
        return wc;
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
