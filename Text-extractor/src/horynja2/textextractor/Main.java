package horynja2.textextractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.text.html.HTML;
import java.io.File;
import java.io.IOException;

public class Main {

    private static boolean validSpeechPart(Element elem){
        if(!elem.tagName().equals("p")) return false;
        Attributes atrs = elem.attributes();
        boolean ret = false;
        for (Attribute atr:atrs) {
            if(atr.getKey().equals("align") && atr.getValue().equals("justify")){
                ret = true;
                break;
            }
        }
        return ret;
    }

    private static boolean validSpeaker(Element elem){
        if(!elem.tagName().equals("a")) return false;
        Attributes atrs = elem.attributes();
        boolean ret = false;
        for (Attribute atr:atrs) {
            if(atr.getKey().equals("href") && atr.getValue().contains("/sqw/detail.sqw?id=")){
                ret = true;
                break;
            }
        }
        return ret;
    }

    public static void main(String args[]) throws IOException {
        File input = new File("033schuz/s033001.htm");
        Document doc = Jsoup.parse(input, "Windows-1250");
        Elements elements = doc.body().select("*");
        String currentSpeech = "";
        String currentSpeaker = "";

        for (Element element : elements) {
            Attributes atrs = element.attributes();
            for (Attribute atr:atrs) {
                //System.out.println(atr.getKey());
                //System.out.println(atr.getValue());
                // if(atr.)
            }
            if(validSpeaker(element)){
                if(currentSpeaker != ""){
                    System.out.println(currentSpeaker + ": " + currentSpeech);
                }
                currentSpeaker = element.ownText();
                currentSpeech = "";
            }
            if(validSpeechPart(element)){
                currentSpeech += element.ownText();
            }

           /* System.out.println();
            System.out.println(element.tagName());
            System.out.println("speech part: " + validSpeechPart(element));
            System.out.println("speaker: " + validSpeaker(element));*/
            //System.out.println(element.ownText());
        }
        //System.out.println("sdfsdf");
    }
}
