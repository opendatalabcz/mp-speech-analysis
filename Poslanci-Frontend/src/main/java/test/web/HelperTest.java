package test.web;

import org.junit.Test;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HelperTest {
    @Test
    public void getShortenStringTest() {
        assertEquals(
                "Normal positive test",
                "abcde...",
                web.Helper.getShortenString("abcdefgh", 5)
        );
        assertEquals(
                "Too large limit",
                "abcdefgh",
                web.Helper.getShortenString("abcdefgh", 50)
        );
        assertEquals(
                "Negative limit (default limit = 10)",
                "abcdefghij...",
                web.Helper.getShortenString("abcdefghijk", -50)
        );
        assertEquals(
                "Input = null",
                null,
                web.Helper.getShortenString(null, 5)
        );
        assertEquals(
                "Input is empty",
                "",
                web.Helper.getShortenString("", 5)
        );
    }

    @Test
    public void getRoundDoubleTest() {
        assertEquals(
                "Normal positive test",
                "9,354",
                web.Helper.getRoundDouble(9.3541)
        );
        assertEquals(
                "Round up #0",
                "9,112",
                web.Helper.getRoundDouble(9.1119)
        );
        assertEquals(
                "Round up #1",
                "10",
                web.Helper.getRoundDouble(9.9999)
        );
        assertEquals(
                "No action #0",
                "9,999",
                web.Helper.getRoundDouble(9.999)
        );
        assertEquals(
                "No action #1",
                "9",
                web.Helper.getRoundDouble(9.0)
        );
    }

    PoslanecEntity poslanec0 = new PoslanecEntity();
    PoslanecEntity poslanec1 = new PoslanecEntity();
    PoslanecEntity poslanec2 = new PoslanecEntity();
    PoslanecStatistikyEntity pse0 = new PoslanecStatistikyEntity(null, 5000, 0.6);
    PoslanecStatistikyEntity pse1 = new PoslanecStatistikyEntity(null, 0, 0.0);
    PoslanecStatistikyEntity pse2 = new PoslanecStatistikyEntity(null, 1000, -0.3);
    List<PoslanecEntity> list = new ArrayList<>();

    public void initializeList(){
        list = new ArrayList<>();
        poslanec0.setPoslanecStatistikyByIdPoslanec(pse0);
        poslanec1.setPoslanecStatistikyByIdPoslanec(pse1);
        poslanec2.setPoslanecStatistikyByIdPoslanec(pse2);
        list.add(poslanec0);
        list.add(poslanec1);
        list.add(poslanec2);
    }

    @Test
    public void getAveragePocetSlovRoundTest() {
        initializeList();
        assertEquals("Positive Test #0",
                "2000",
                web.Helper.getAveragePocetSlovRound(list));

        list.remove(poslanec0);
        assertEquals("Positive Test #1",
                "500",
                web.Helper.getAveragePocetSlovRound(list));

        assertEquals("Empty list",
                "0",
                web.Helper.getAveragePocetSlovRound(new ArrayList<>()));
    }

    @Test
    public void getMedianPocetSlovTest() {
        initializeList();
        assertEquals("Positive Test #0",
                "1000",
                web.Helper.getMedianPocetSlov(list).toString());

        list.remove(poslanec0);
        assertEquals("Positive Test #1",
                "500",
                web.Helper.getMedianPocetSlov(list).toString());

        assertEquals("Empty list",
                "0",
                web.Helper.getMedianPocetSlov(new ArrayList<>()).toString());
    }

    @Test
    public void getAverageSentimentRoundTest() {
        initializeList();
        assertEquals("Positive Test #0",
                "0,1",
                web.Helper.getAverageSentimentRound(list));

        list.remove(poslanec0);
        assertEquals("Positive Test #1",
                "-0,15",
                web.Helper.getAverageSentimentRound(list));

        assertEquals("Empty list",
                "0",
                web.Helper.getAverageSentimentRound(new ArrayList<>()));
    }

    @Test
    public void getMedianSentimentRoundTest() {
        initializeList();
        assertEquals("Positive Test #0",
                "0",
                web.Helper.getMedianSentimentRound(list));

        list.remove(poslanec0);
        assertEquals("Positive Test #1",
                "-0,15",
                web.Helper.getMedianSentimentRound(list));

        assertEquals("Empty list",
                "0",
                web.Helper.getMedianSentimentRound(new ArrayList<>()));
    }

    @Test
    public void countSentimentTest() {
        assertEquals("Positive Test #0",
                "0.0",
                web.Helper.countSentiment(10,10).toString());

        assertEquals("Positive Test #1",
                "1.0",
                web.Helper.countSentiment(10,0).toString());

        assertEquals("Positive Test #2",
                "-1.0",
                web.Helper.countSentiment(0,10).toString());

        assertEquals("Positive Test #3",
                "0.5",
                web.Helper.countSentiment(3, 1).toString());

        assertEquals("Positive Test #4",
                "0.0",
                web.Helper.countSentiment(0, 0).toString());

        assertEquals("Negative input",
                "0.0",
                web.Helper.countSentiment(-10, 10).toString());
    }


}
