package test.helper;

import helper.MathHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MathHelperTest {
    @Test
    public void countSentimentTest() {
        assertEquals("Positive Test #0",
                "0.0",
                MathHelper.countSentiment(10,10).toString());

        assertEquals("Positive Test #1",
                "1.0",
                MathHelper.countSentiment(10,0).toString());

        assertEquals("Positive Test #2",
                "-1.0",
                MathHelper.countSentiment(0,10).toString());

        assertEquals("Positive Test #3",
                "0.5",
                MathHelper.countSentiment(3, 1).toString());

        assertEquals("Positive Test #4",
                "0.0",
                MathHelper.countSentiment(0, 0).toString());

        assertEquals("Negative input",
                "0.0",
                MathHelper.countSentiment(-10, 10).toString());
    }
}
