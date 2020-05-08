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

    public static String getLastNWordsInString(String text, Integer N) {
        String [] arr = text.split("\\s+");
        //Splits words & assign to the arr[]  ex : arr[0] -> Copying ,arr[1] -> first
        String nWords="";


        Integer count = arr.length - N;
        if(count < 0) count = 0;
        // concatenating number of words that you required
        for(int i=arr.length-1; i>=count; i--){
            nWords = arr[i] + " " + nWords;
        }
        return nWords;
    }

    public static boolean equalsIgnoreCaseAndDiacritics(String s1, String s2) {
        String normalizedS1 = Normalizer.normalize(s1, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String normalizedS2 = Normalizer.normalize(s2, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalizedS1.equalsIgnoreCase(normalizedS2);
    }

    public static String removePostfix(String word) {
        String[] parts0 = word.split("-");
        String[] parts1 = parts0[0].split("_");
        String[] parts2 = parts1[0].split("`");
        return parts2[0];
    }
}
