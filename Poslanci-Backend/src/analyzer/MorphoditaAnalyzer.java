package analyzer;

import creator.LemmaWithTag;
import cz.cuni.mff.ufal.morphodita.*;

import java.util.ArrayList;
import java.util.List;

public class MorphoditaAnalyzer {
    private static Tagger tagger = Tagger.load("resources\\czech-morfflex-pdt-161115.tagger"); //todo relativni cesta, i kdyz tady mozna ani ne

    //funkce upravena z prikladu pouziti knihovny, ktery je na jejich oficialnich strankach
    public static List<LemmaWithTag> analyzeString(String input) {
        if (tagger == null) {
            System.err.println("Cannot load tagger from file");
            return null;
        }
        Forms forms = new Forms();
        TaggedLemmas lemmas = new TaggedLemmas();
        TokenRanges tokens = new TokenRanges();
        Tokenizer tokenizer = tagger.newTokenizer();
        if (tokenizer == null) {
            System.err.println("No tokenizer is defined for the supplied model!");
            return null;
        }

        tokenizer.setText(input);
        int t = 0;
        List<LemmaWithTag> lemmasList = new ArrayList<>();

        //tokenizer prochazi text vetu po vete a jednotlive tokeny se pote prevadi na dvojici lemmatu a tagu
        while (tokenizer.nextSentence(forms, tokens)) {
            tagger.tag(forms, lemmas);

            for (int i = 0; i < lemmas.size(); i++) {
                TaggedLemma lemma = lemmas.get(i);
                TokenRange token = tokens.get(i);
                int token_start = (int)token.getStart(), token_end = token_start + (int)token.getLength();
                lemmasList.add(new LemmaWithTag(lemma.getLemma(), lemma.getTag()));
                t = token_end;
            }
        }
        return lemmasList;
    }
}
