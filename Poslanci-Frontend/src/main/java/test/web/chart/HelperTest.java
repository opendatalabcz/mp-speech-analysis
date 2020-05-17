package test.web.chart;

import org.junit.Test;
import web.monthYear.MonthYear;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static web.chart.Helper.*;

public class HelperTest {
    @Test
    public void tryParseIntTest() {
        assertTrue("Positive test #0", tryParseInt("10"));
        assertTrue("Positive test #1", tryParseInt("-10"));
        assertTrue("Positive test #2", tryParseInt("0"));
        assertFalse("Nonnumerical characters", tryParseInt("afda"));
        assertFalse("Empty input", tryParseInt(""));
        assertFalse("Null input", tryParseInt(null));
        assertFalse("Decimal number #0", tryParseInt("1.1"));
        assertFalse("Decimal number #1", tryParseInt("1,1"));
    }

    @Test
    public void getMonthLabelsListTest() {
        MonthYear begin0 = new MonthYear(10,100);
        MonthYear end0 = new MonthYear(2,101);
        MonthYear end1 = new MonthYear(1,99);
        List<String> list0 = new ArrayList<>();
        list0.add(new MonthYear(10,100).toString());
        list0.add(new MonthYear(11,100).toString());
        list0.add(new MonthYear(0,101).toString());
        list0.add(new MonthYear(1,101).toString());
        list0.add(new MonthYear(2,101).toString());

        assertEquals("Positive test #0", getMonthLabelsList(begin0, end0), list0);

        List<String> list1 = new ArrayList<>();
        assertEquals("End date before begin date", getMonthLabelsList(begin0, end1), list1);

        assertNull("Null value #0", getMonthLabelsList(null, end0));
        assertNull("Null value #1", getMonthLabelsList(begin0, null));
        assertNull("Null value #2", getMonthLabelsList(null, null));
    }

    @Test
    public void getPeriodLabelListTest() {
        Integer begin0 = 4;
        Integer end0 = 7;
        Integer end1 = 2;
        List<String> list0 = new ArrayList<>();
        list0.add("PSP4");
        list0.add("PSP5");
        list0.add("PSP6");
        list0.add("PSP7");

        assertEquals("Positive test #0", getPeriodLabelList(begin0, end0), list0);

        List<String> list1 = new ArrayList<>();
        assertEquals("End before begin", getPeriodLabelList(begin0, end1), list1);

        assertNull("Null value #0", getPeriodLabelList(null, end0));
        assertNull("Null value #1", getPeriodLabelList(begin0, null));
        assertNull("Null value #2", getPeriodLabelList(null, null));
    }
}
