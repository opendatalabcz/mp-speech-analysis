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
        final String SAMPLE_CSV_FILE_PATH = "./sublex_1_0.csv";
        wordsSentiment = new HashMap<>();
        Integer count = 0;
        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.TDF)
        ) {
            for (CSVRecord csvRecord : csvParser) {

                String word = csvRecord.get(2);
                String sentiment = csvRecord.get(3);
                String wordProcessed = removeSuffix(word);
                Integer sentimentProcessed = getSentimentFromString(sentiment);

                /*System.out.println("Record No - " + csvRecord.getRecordNumber());
                System.out.println("---------------");
                System.out.println("Word : " + wordProcessed);
                System.out.println("Sentiment: " + sentiment);
                System.out.println("---------------\n\n");*/

                Integer exist = wordsSentiment.get(wordProcessed);
                if(exist != null) {
                    if(!exist.equals(sentimentProcessed)) {
                        wordsSentiment.remove(wordProcessed);
                    }
                    //System.out.println("-------DUPLICITA--------: " + wordProcessed);
                    count++;
                    //break;
                } else {
                    wordsSentiment.put(wordProcessed, sentimentProcessed);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        /*printMap();
        System.out.println("Pocet Duplicit: " + count);
        System.out.println("Velikost mapy: " + wordsSentiment.size());*/
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
