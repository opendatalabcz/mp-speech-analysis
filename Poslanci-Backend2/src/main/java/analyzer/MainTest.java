package analyzer;

import creator.LemmaWithTag;

import javax.persistence.Persistence;
import java.util.List;

import static analyzer.MorphoditaAnalyzer.analyzeString;

public class MainTest {
    public static void main(String[] args) {
        System.out.println("Hello " + Persistence.class.getName());
        List<LemmaWithTag> list = analyzeString("auta");
        System.out.println(list.get(0).getTag());
    }
}
