package reader;

import entity.BodEntity;
import helper.ParseHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.BodEntityService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class BodReader {
    private static BodEntityService bodEntityService = new BodEntityService();
    private static String charset = "Windows-1250";
    private static String inPath = "resources/Schuze/";

    public static long GetMeetingsCount(String inPath) {
        long count = -1;
        try (Stream<Path> files = Files.list(Paths.get(inPath))) {
            count = files.count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void ProcessOneMeeting(File file, Integer meetingNumber) {
        Document doc;
        try {
            doc = Jsoup.parse(file, charset);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Elements elements = doc.body().select("*");
        for (Element element : elements) {
            if(element.tagName().equals("p")) {
                for(Element child : element.children()){
                    if(child.tagName().equals("b")) {
                        BodEntity bodEntityAlreadyExists = bodEntityService.checkTextExists(ParseHelper.removeNumberPrefix(child.ownText()), meetingNumber);
                        BodEntity bodEntity;
                        if(bodEntityAlreadyExists == null) {
                            bodEntity = new BodEntity(
                                    null,
                                    ParseHelper.removeNumberPrefix(child.ownText()),
                                    meetingNumber,
                                    ParseHelper.getSqlDateFromBod(element.ownText())
                            );
                        } else {
                            bodEntity = new BodEntity(
                                    bodEntityAlreadyExists.getIdBod(),
                                    ParseHelper.removeNumberPrefix(child.ownText()),
                                    meetingNumber,
                                    ParseHelper.getSqlDateFromBod(element.ownText())
                            );
                        }
                        bodEntityService.createOrUpdate(bodEntity);
                    }
                }
            }
        }
    }

    public static void ProcessAllMeetings() {
        long meetingsCount = GetMeetingsCount(inPath);
        String dirName, completePath;
        if(meetingsCount < 0) {
            System.out.println("Neplatny pocet schuzi");
            return;
        }
        for(int i = 1; i <= meetingsCount; i++) {
            dirName = String.format("%03d",i) + "schuz";
            completePath = inPath + dirName + "/index.htm";
            if(new File(completePath).exists())
            {
                File currentFile = new File(completePath);
                ProcessOneMeeting(currentFile, i);
            }
        }
    }

    public static void main(String[] args) {
        ProcessAllMeetings();
    }
}
