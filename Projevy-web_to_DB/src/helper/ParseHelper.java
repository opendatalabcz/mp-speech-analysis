package helper;

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
}
