package test.analyzer;

import analyzer.MorphoditaAnalyzer;
import org.junit.Test;

public class MorphoditaAnalyzerTest {
    @Test
    public void analyzeWordTest() {
        MorphoditaAnalyzer.analyzeWord("autem");
    }

    @Test
    public void analyzeStringTest() {
        MorphoditaAnalyzer.analyzeString("Mám rád pomeranče a banány.");
    }
}
