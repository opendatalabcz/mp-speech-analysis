package helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseHelper {
    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String parseDateString(String input) {
        String regex = "^([0-2][0-9]|(3)[0-1])(\\.)(((0)[0-9])|((1)[0-2]))(\\.)\\d{4}$";
        if(input.matches(regex))
            return input;
        else
            return null;
    }

    public static String removeUselessWhitespacesString(String input) {
        return input.replaceAll("\\s+", " ");
    }

    public static String removeNumberPrefix(String input) {
        return input.replaceAll("^[0-9]+\\. ", "");
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
        String stringDate = getStringDateFromBod(input);
        if(stringDate == null) return null;
        Date date;
        try {
            date = new SimpleDateFormat(pattern).parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
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
