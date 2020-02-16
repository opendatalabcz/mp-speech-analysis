package analyzer;

import cz.cuni.mff.ufal.morphodita.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Analyzer {

    public static String encodeEntities(String text) {
        return text.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;");
    }

    public static List<String> analyzeString(String input) {
        Tagger tagger = Tagger.load("czech-morfflex-pdt-161115\\czech-morfflex-pdt-161115-pos_only.tagger");
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
        List<String> lemmasList = new ArrayList<>();

        while (tokenizer.nextSentence(forms, tokens)) {
            tagger.tag(forms, lemmas);

            for (int i = 0; i < lemmas.size(); i++) {
                TaggedLemma lemma = lemmas.get(i);
                TokenRange token = tokens.get(i);
                int token_start = (int)token.getStart(), token_end = token_start + (int)token.getLength();
                System.out.println("Lemma: " + lemma.getLemma());
                System.out.println("Tag: " + lemma.getTag());
                System.out.println();
                lemmasList.add(lemma.getLemma());
                    /*System.out.printf("%s%s<token lemma=\"%s\" tag=\"%s\">%s</token>%s",
                            encodeEntities(text.substring(t, token_start)),
                            i == 0 ? "<sentence>" : "",
                            encodeEntities(lemma.getLemma()),
                            encodeEntities(lemma.getTag()),
                            encodeEntities(text.substring(token_start, token_end)),
                            i + 1 == lemmas.size() ? "</sentence>" : "");
                            */
                t = token_end;
            }
        }
        System.out.print(encodeEntities(input.substring(t)));
        return lemmasList;
    }

    public static void analyzeWord(String word) {
        Morpho morpho = Morpho.load("czech-morfflex-pdt-161115\\czech-morfflex-161115.dict");
        TaggedLemmas lemmas = new TaggedLemmas();
        TaggedLemmasForms lemmas_forms = new TaggedLemmasForms();

        int result = morpho.analyze(word, morpho.GUESSER, lemmas);

        String guesser = result == morpho.GUESSER ? "Guesser " : "";
        for (int i = 0; i < lemmas.size(); i++) {
            TaggedLemma lemma = lemmas.get(i);
            System.out.printf("%sLemma: %s %s\n", guesser, lemma.getLemma(), lemma.getTag());
        }
    }
}
