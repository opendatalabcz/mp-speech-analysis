package horynja2.textextractor;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Map;

public class MeetingDir2 {
    private static Map<Integer, String> poslanecCurrentMap;
    private static String charset = "Windows-1250";

    private static String inPath = "resources/Schuze/";

    private static String outPath = "resources/Schuze-format-txt/";
    private static String outSuffix = "-format-txt.txt";

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

    private static Integer validSpeaker(Element elem){
        if(!elem.tagName().equals("a")) return null;
        Attributes atrs = elem.attributes();
        String snemovna = "/sqw/detail.sqw?id=";
        String vlada = "https://www.vlada.cz/cz/clenove-vlady/";
        Integer id = null;

        for (Attribute atr:atrs) {
            if(atr.getKey().equals("href")){
                if(atr.getValue().startsWith(snemovna)){
                    id = Integer.parseInt(atr.getValue().substring(snemovna.length()));
                    //System.out.println(elem.ownText() + " (" + id + ")");
                    break;
                }
                if(true){ //atr.getValue().startsWith(vlada)
                    for (Map.Entry<Integer, String> entry : poslanecCurrentMap.entrySet()) {
                        if (elem.ownText().endsWith(entry.getValue())) {
                            //System.out.println(elem.ownText() + " (" + entry.getKey() + ")");
                            return entry.getKey();
                        }
                    }
                    id = -1;
                    //System.out.println(elem.ownText() + " (" + id + ")");
                    break;
                }
            }
        }
        return id;
    }


    private static boolean validTheme(Element elem) {
        if(!elem.tagName().equals("p")) return false;
        Attributes atrs = elem.attributes();
        boolean ret = false;

        for (Attribute atr:atrs) {
            if(atr.getKey().equals("align") && atr.getValue().equals("center")){
                if(!elem.child(0).ownText().isEmpty())  ret = true;
                break;
            }
        }
        return ret;
    }




    public static void Themes(String dirName, Map<Integer, String> inMap) throws IOException {
        Integer meetingNum = Integer.parseInt(dirName.substring(0,3));
        Integer meetingPartNum = 1;
        Integer speechNum = 0;
        //OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outPath + dirName + outSuffix), Charset.forName(charset));
        //System.out.println(meetingNum);
        poslanecCurrentMap = inMap;
        Speech currentSpeech = null;
        //System.out.println();
        while(new File(inPath + dirName + "/s" + String.format("%03d",meetingNum) + String.format("%03d",meetingPartNum) + ".htm").exists()){
            File currentFile = new File(inPath + dirName + "/s" + String.format("%03d",meetingNum) + String.format("%03d",meetingPartNum) + ".htm");
            System.out.println(currentFile.getName());
            //out.append(currentFile.getName() + "\n");
            Document doc = Jsoup.parse(currentFile, charset);
            Elements elements = doc.body().select("*");


            for (Element element : elements) {

                if(validTheme(element)) {
                    System.out.println(element.child(0).ownText());
                }

/*
                if(element.childNodeSize() == 2) {
                    Integer speakerID = validSpeaker(element.child(0));
                    if(speakerID == null) continue;
                    if (currentSpeech != null) {
                        System.out.println(currentSpeech.toString());
                        out.append(currentSpeech.toString() + "\n");
                        currentSpeech = null;
                    }
                    currentSpeech = new Speech(element.child(0).ownText(), speakerID, meetingNum, speechNum);
                    speechNum++;
                }
                if(currentSpeech != null && validSpeechPart(element)){
                    currentSpeech.addSpeechPart(new SpeechPart(element.ownText()));
                }

 */
            }
            meetingPartNum++;
        }
        //System.out.println(currentSpeech.toString());
        //out.append(currentSpeech.toString() + "\n");
        //out.close();

        /*
        File dir = new File(dirName);
        for (File file : dir.listFiles()) {
            if(file.getName().matches("^s.*"))
                System.out.println(file.getName());
        }*/
    }
}
