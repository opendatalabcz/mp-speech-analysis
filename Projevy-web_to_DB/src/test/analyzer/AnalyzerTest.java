package test.analyzer;

import analyzer.Analyzer;
import org.junit.Test;

public class AnalyzerTest {
    @Test
    public void analyzeWordTest() {
        Analyzer.analyzeWord("autem");
    }

    @Test
    public void analyzeStringTest() {
        Analyzer.analyzeString("Mám rád pomeranče a banány.");
    }
}
