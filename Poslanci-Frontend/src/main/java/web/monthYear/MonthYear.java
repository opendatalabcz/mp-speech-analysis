package web.monthYear;

import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

import static web.monthYear.Helper.getMonthFromSQLDate;
import static web.monthYear.Helper.getYearFromSQLDate;
//pomocna trida pro praci s datumem ve forme mesice a roku
public class MonthYear {
    private int month;
    private int year;

    public MonthYear(int month, int year) {
        if(month > 11) throw new IllegalArgumentException();
        this.month = month;
        this.year = year;
    }

    public MonthYear(MonthYear that) {
        this.month = that.month;
        this.year = that.year;
    }

    public MonthYear(Date date) {
        if(date == null) {
            date = new Date(Calendar.getInstance().getTime().getTime());
        }
        this.month = getMonthFromSQLDate(date);
        this.year = getYearFromSQLDate(date);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void increase() {
        if(month < 11) {
            month++;
        } else {
            month = 0;
            year++;
        }
    }

    public void decrease() {
        if(month > 0) {
            month--;
        } else {
            month = 11;
            year--;
        }
    }

    public boolean greaterThan(MonthYear that) {
        if(this.year > that.year) return true;
        if(this.year == that.year && this.month > that.month) return true;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MonthYear monthYear = (MonthYear) o;
        return month == monthYear.month &&
                year == monthYear.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year);
    }

    @Override
    public String toString() {
        return month+1 + " - " + year;
    }
}
