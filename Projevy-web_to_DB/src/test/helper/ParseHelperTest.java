package test.helper;

import static helper.ParseHelper.*;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParseHelperTest {
    @Test
    public void removeNumberPrefixTest() {
        assertEquals(
                "With number",
                "Random text",
                removeNumberPrefix("11. Random text")
        );
        assertEquals(
                "Without number",
                "Random text",
                removeNumberPrefix("Random text")
        );
    }

    @Test
    public void translateCzechMonthToNumberTest() {
        assertEquals(
                "květen -> 4",
                4,
                translateCzechMonthToNumber("květen")
        );

        assertEquals(
                "května -> 4",
                4,
                translateCzechMonthToNumber("května")
        );

        assertEquals(
                "prosince -> 11",
                4,
                translateCzechMonthToNumber("května")
        );

        assertEquals(
                "července -> 6",
                4,
                translateCzechMonthToNumber("května")
        );

        assertEquals(
                "červenec -> 6",
                4,
                translateCzechMonthToNumber("května")
        );

        assertEquals(
                "června -> 5",
                4,
                translateCzechMonthToNumber("května")
        );
    }
}
