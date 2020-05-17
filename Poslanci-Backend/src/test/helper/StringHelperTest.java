package test.helper;

import org.junit.Test;

import static helper.StringHelper.*;
import static org.junit.Assert.*;

public class StringHelperTest {
    @Test
    public void wordCountTest() {
        assertEquals("Possitive test #0", 4,
                wordCount("Místopředseda PSP Lubomír Zaorálek: "));
        assertEquals("Possitive test #1", 1,
                wordCount("Místopředseda"));
        assertEquals("Null input", 0,
                wordCount(null));
        assertEquals("Empty input", 0,
                wordCount(""));
    }

    @Test
    public void getLastNWordsInStringTest() {
        assertEquals("Possitive test #0", "Zaorálek: ",
                getLastNWordsInString("Místopředseda PSP Lubomír Zaorálek: ", 1));
        assertEquals("Possitive test #1", "PSP Lubomír Zaorálek: ",
                getLastNWordsInString("Místopředseda PSP Lubomír Zaorálek: ", 3));
        assertEquals("Possitive test #2", "Místopředseda PSP Lubomír Zaorálek: ",
                getLastNWordsInString("Místopředseda PSP Lubomír Zaorálek: ", 666));
        assertNull("Null input", getLastNWordsInString(null, 666));
        assertEquals("Empty input", "",
                getLastNWordsInString("", 666));
        assertEquals("More spaces #0", "Místopředseda PSP Lubomír Zaorálek: ",
                getLastNWordsInString("Místopředseda PSP Lubomír        Zaorálek: ", 666));
        assertEquals("More spaces #1", "",
                getLastNWordsInString("       ", 666));
    }

    @Test
    public void removeDiacriticsTest() {
        assertEquals("Possitive test #0",
                "escrzyaie" , removeDiacritics("ěščřžýáíé"));
        assertEquals("Possitive test #1",
                "stiznost" , removeDiacritics("stížnost"));
        assertEquals("Empty input",
                "" , removeDiacritics(""));
        assertNull("Null input", removeDiacritics(null));
    }

    @Test
    public void equalsIgnoreCaseAndDiacriticsTest() {
        assertTrue("Possitive test #0",
                equalsIgnoreCaseAndDiacritics("TeXtíčeK","tExtIcék"));
        assertTrue("Possitive test #1",
                equalsIgnoreCaseAndDiacritics("Já jsem mezera","ja jsém mežera"));
        assertTrue("Possitive test #2",
                equalsIgnoreCaseAndDiacritics("666 666","666 666"));
        assertTrue("Empty strings",
                equalsIgnoreCaseAndDiacritics("",""));
        assertFalse("Negative test #1",
                equalsIgnoreCaseAndDiacritics("Já jsem mezera","ja nejsém mežera"));
        assertFalse("Null strings #0",
                equalsIgnoreCaseAndDiacritics(null, null));
        assertFalse("Null strings #0",
                equalsIgnoreCaseAndDiacritics(null, "tu něco je"));
    }

    @Test
    public void removePostfixTest() {
        assertEquals("Possitive test #0",
                "strana" , removePostfix("strana-4_^(v_knize,_rukopise,...)"));
        assertEquals("Possitive test #1",
                "vyjádřit" , removePostfix("vyjádřit_:W"));
        assertEquals("Possitive test #2",
                "volba" , removePostfix("volba"));
        assertEquals("Empty word",
                "" , removePostfix(""));
        assertNull("Null word", removePostfix(null));
    }

    @Test
    public void removeUselessWhitespacesStringTest() {
        assertEquals(
                "Spaces #0",
                "Random text",
                removeUselessWhitespacesString("Random     text")
        );
        assertEquals(
                "Spaces #1",
                " Random text ",
                removeUselessWhitespacesString("   Random     text     ")
        );
        assertEquals(
                "Tabs",
                "Random text",
                removeUselessWhitespacesString("Random      text")
        );
        assertEquals(
                "Tabs + new line",
                "Random text",
                removeUselessWhitespacesString("Random   \n   text")
        );
        assertEquals(
                "Tabs + new line",
                "Random text",
                removeUselessWhitespacesString("Random   \n   text")
        );
        assertEquals(
                "Empty input",
                "",
                removeUselessWhitespacesString("")
        );
        assertNull("Null input", removeUselessWhitespacesString(null));
    }


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
                "prosince -> 11",
                11,
                translateCzechMonthToNumber("prosince")
        );
        assertEquals(
                "července -> 6",
                6,
                translateCzechMonthToNumber("července")
        );
        assertEquals(
                "červenec -> 6",
                6,
                translateCzechMonthToNumber("červenec")
        );
        assertEquals(
                "června -> 5",
                5,
                translateCzechMonthToNumber("června")
        );
    }
}
