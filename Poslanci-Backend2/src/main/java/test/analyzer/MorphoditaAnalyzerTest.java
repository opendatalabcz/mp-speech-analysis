package test.analyzer;

import analyzer.MorphoditaAnalyzer;
import creator.LemmaWithTag;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MorphoditaAnalyzerTest {

    @Test
    public void analyzeStringTest() {
        List<LemmaWithTag> list =  MorphoditaAnalyzer.analyzeString("Mám rád pomeranče a banány.");
        assertEquals("Test of tokenization", list.size(), 6);
        assertTrue("Test of lemmatization #0", list.get(0).getLemma().startsWith("mít"));
        assertTrue("Test of lemmatization #1", list.get(2).getLemma().startsWith("pomeranč"));

        list =  MorphoditaAnalyzer.analyzeString("");
        assertEquals("Test of tokenization", list.size(), 0);
    }
}
