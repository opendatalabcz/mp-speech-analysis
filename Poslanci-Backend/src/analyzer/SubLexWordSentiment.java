package analyzer;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import javax.inject.Singleton;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class SubLexWordSentiment {
    private static Map<String, Integer> wordsSentiment = null;

    public static void setup() {
        final String SAMPLE_CSV_FILE_PATH = "resources\\sublex_1_0.csv"; //todo relativni cesta
        wordsSentiment = new HashMap<>();
        Integer count = 0;
        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.TDF)
        ) {
            //prochazi radky slovniku a zpracovava potrebne informace
            for (CSVRecord csvRecord : csvParser) {

                String word = csvRecord.get(2);
                String sentiment = csvRecord.get(3);
                String wordProcessed = removeSuffix(word);
                Integer sentimentProcessed = getSentimentFromString(sentiment);

                //jestlize slovo uz je v seznamu, tak ho neulozi a navic smaze i to prvni
                Integer exist = wordsSentiment.get(wordProcessed);
                if(exist != null) {
                    if(!exist.equals(sentimentProcessed)) {
                        wordsSentiment.remove(wordProcessed);
                    }
                    count++;
                } else {
                    //jestli slovo v seznamu jeste neni, tak ho prida
                    wordsSentiment.put(wordProcessed, sentimentProcessed);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Integer getSentimentForWord(String word) {
        if(wordsSentiment == null) setup();
        Integer ret = wordsSentiment.get(removeSuffix(word));
        if(ret != null) {
            return ret;
        } else {
            return 0;
        }
    }

    private static Integer getSentimentFromString(String sentiment) {
        //NEG = negativni sentiment, POS - pozitivni sentiment
        if(sentiment.equals("NEG")) return -1;
        else if(sentiment.equals("POS")) return 1;
        return 0;
    }

    private static String removeSuffix(String word) {
        return word.split("_")[0].split("-")[0];
    }

    private static void printMap(){
        for(Map.Entry<String, Integer> entry : wordsSentiment.entrySet()) {
            System.out.println("Slovo: " + entry.getKey() + "\t\t\t Sentiment: " + entry.getValue());
        }
    }
}
