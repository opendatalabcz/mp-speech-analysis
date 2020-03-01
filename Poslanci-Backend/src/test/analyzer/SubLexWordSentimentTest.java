package test.analyzer;

import analyzer.SubLexWordSentiment;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SubLexWordSentimentTest {
    @Test
    public void WordTest() {
        SubLexWordSentiment.setup();

        assertEquals("Positive word: doufat",
                SubLexWordSentiment.getSentimentForWord("doufat"),
                Integer.valueOf(1));

        assertEquals("Positive word: hezky",
                SubLexWordSentiment.getSentimentForWord("hezky"),
                Integer.valueOf(1));

        assertEquals("Positive word: fanaticky",
                SubLexWordSentiment.getSentimentForWord("fanaticky"),
                Integer.valueOf(-1));

        assertEquals("Positive word: hádka",
                SubLexWordSentiment.getSentimentForWord("hádka"),
                Integer.valueOf(-1));

        assertEquals("Positive word: Karel",
                SubLexWordSentiment.getSentimentForWord("Karel"),
                Integer.valueOf(0));
    }
}
