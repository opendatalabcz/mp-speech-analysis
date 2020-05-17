package web.monthYear;

import java.sql.Date;
import java.util.Calendar;

public class Helper {
    public static int getMonthFromSQLDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month;
    }

    public static int getYearFromSQLDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }
}
