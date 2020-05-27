package helper;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

    public static int wordCount(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }
        String[] words = input.split("\\s+");
        return words.length;
    }

    //inspirace z https://stackoverflow.com/questions/29859698/copy-the-first-n-words-in-a-string-in-java
    public static String getLastNWordsInString(String text, Integer N) {
        if(text == null) return null;
        if(text.isEmpty()) return "";
        String [] arr = text.split("\\s+");
        //Splits words & assign to the arr[]  ex : arr[0] -> Copying ,arr[1] -> first
        String nWords="";

        Integer begin = arr.length - N;
        if(begin < 0) begin = 0;
        // concatenating number of words that you required
        for(int i=arr.length-1; i>=begin; i--){
            nWords = arr[i] + " " + nWords;
        }
        return nWords;
    }

    public static String removeDiacritics(String text) {
        if(text == null) return null;
        return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static boolean equalsIgnoreCaseAndDiacritics(String s1, String s2) {
        String normalizedS1 = removeDiacritics(s1);
        String normalizedS2 = removeDiacritics(s2);
        if(normalizedS1 == null || normalizedS2 == null) return false;
        return normalizedS1.equalsIgnoreCase(normalizedS2);
    }

    public static String removePostfix(String word) {
        if(word == null) return null;
        String[] parts0 = word.split("-");
        String[] parts1 = parts0[0].split("_");
        String[] parts2 = parts1[0].split("`");
        return parts2[0];
    }

    //--------------------------

    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String removeUselessWhitespacesString(String input) {
        if(input == null) return null;
        return input.replaceAll("\\s+", " ");
    }

    public static String removeNumberPrefix(String input) {
        return input.replaceAll("^[0-9]+\\. ", "");
    }

    public static String removePrefix(String s, String prefix)
    {
        if (s != null && prefix != null && s.startsWith(prefix)){
            return s.substring(prefix.length());
        }
        return s;
    }

    public static String getStringDateFromBod (String input) {
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find())
        {
            return matcher.group(1);
        }
        return null;
    }

    public static java.sql.Date getSqlDateFromString (String input, String pattern) {
        if(input == null || input.equals("") || input.equals(" ")) return null;
        Date date;
        try {
            date = new SimpleDateFormat(pattern).parse(input);
        } catch (ParseException e) {
            return null;
        }

        final long hours12 = 12L * 60L * 60L * 1000L;
        java.sql.Date sqlDate = new java.sql.Date(date.getTime() + hours12);
        return sqlDate;
    }

    public static java.sql.Date getSqlDateFromBod (String input) {
        return getSqlDateFromString(getStringDateFromBod(input), "dd. MMMM yyyy");
    }

    public static int translateCzechMonthToNumber(String input) {
        Locale locale = new Locale("cs", "CZ");
        Date date;
        try {
            date = new SimpleDateFormat("MMMM", locale).parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }
}
