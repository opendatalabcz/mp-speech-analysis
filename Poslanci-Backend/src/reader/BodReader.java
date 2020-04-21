package reader;

import org.hibernate.id.uuid.Helper;
import poslanciDB.entity.BodEntity;
import helper.FileHelper;
import helper.ParseHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.service.BodEntityService;
import poslanciDB.service.OrganyEntityService;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BodReader {
    private static BodEntityService bodEntityService = new BodEntityService();
    private static OrganyEntityService organyEntityService = new OrganyEntityService();
    private static String charset = "Windows-1250";
    //private static String inPath = "resources/Schuze/";
    private static String genericPointText = "---Provozní úkony---";

    public static void main(String[] args) {
        String path = "resources/Schuze/";
        OrganyEntity season = organyEntityService.find(172);
        ProcessAllMeetings(path, "PSP8");
    }

    public static BodEntity createGenericPoint(String unformatedDateString, Integer meetingNumber, OrganyEntity season) {
        String day = null, month = null, year = null, stringDate;
        day = unformatedDateString.split("\\.")[0];
        if(day.length() == 1) day = "0".concat(day);

        Pattern patternMonth = Pattern.compile("\\p{javaLetter}+");
        Matcher matcherMonth = patternMonth.matcher(unformatedDateString);
        if (matcherMonth.find())
        {
            month = matcherMonth.group(0);
        }

        Pattern patternYear = Pattern.compile("\\d{4}");
        Matcher matcherYear = patternYear.matcher(unformatedDateString);
        if (matcherYear.find())
        {
            year = matcherYear.group(0);
        }

        stringDate = day + ". " + month + " " + year;
        System.out.println("Unformated Date String: " + unformatedDateString);
        System.out.println("Complete Date: " + stringDate);

        Date sqlDate = ParseHelper.getSqlDateFromString(stringDate, "dd. MMMM yyyy");
        System.out.println("Sql Date: " + sqlDate);
        System.out.println();
        BodEntity bodEntityAlreadyExists = bodEntityService.checkTextExists(genericPointText, meetingNumber, season.getIdOrgan());
        BodEntity bodEntity;
        if(bodEntityAlreadyExists == null) {
            bodEntity = new BodEntity(
                    null,
                    genericPointText,
                    meetingNumber,
                    sqlDate,
                    season
            );
        } else {
            bodEntity = new BodEntity(
                    bodEntityAlreadyExists.getIdBod(),
                    genericPointText,
                    meetingNumber,
                    sqlDate,
                    season
            );
        }
        return bodEntity;
    }

    public static void ProcessOneMeeting(File file, Integer meetingNumber, OrganyEntity season) {
        Document doc;
        boolean genericPointTextCreated = false;
        String genericPointDateString = "";
        try {
            doc = Jsoup.parse(file, charset);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Elements elements = doc.body().select("*");
        for (Element element : elements) {
            if(element.tagName().equalsIgnoreCase("b") && !genericPointTextCreated) {
                //System.out.println(element.ownText());
                genericPointTextCreated = true;
            }
            if(element.tagName().equalsIgnoreCase("a")) {
                Attributes atrs = element.attributes();
                boolean ret = false;
                for (Attribute atr:atrs) {
                    if(atr.getKey().equalsIgnoreCase("href") &&
                            (atr.getValue().matches(".*?htm") || atr.getValue().matches(".*?html")) &&
                            !(atr.getValue().matches(".*?ndex.htm") || atr.getValue().matches(".*?ndex.html"))){
                        if(element.ownText().matches("^[0-9].*$") && !genericPointDateString.matches(".*[0-9]{4}$")) {
                            System.out.println(element.ownText());
                            genericPointDateString = genericPointDateString.concat(element.ownText());
                        }
                    }
                }
            }
            if(element.tagName().equalsIgnoreCase("p")) {
                for(Element child : element.children()){
                    if(child.tagName().equalsIgnoreCase("b")) {
                        BodEntity bodEntityAlreadyExists =
                                bodEntityService.checkTextExists(ParseHelper.removeNumberPrefix(child.ownText()),
                                        meetingNumber, season.getIdOrgan()
                                );
                        BodEntity bodEntity;
                        if(bodEntityAlreadyExists == null) {
                            bodEntity = new BodEntity(
                                    null,
                                    ParseHelper.removeNumberPrefix(child.ownText()),
                                    meetingNumber,
                                    ParseHelper.getSqlDateFromBod(element.ownText()),
                                    season
                            );
                        } else {
                            bodEntity = new BodEntity(
                                    bodEntityAlreadyExists.getIdBod(),
                                    ParseHelper.removeNumberPrefix(child.ownText()),
                                    meetingNumber,
                                    ParseHelper.getSqlDateFromBod(element.ownText()),
                                    season
                            );
                        }
                        if(bodEntity.getDatum() != null)
                            bodEntityService.createOrUpdate(bodEntity);
                    }
                }
            }
        }

        BodEntity bodEntity = createGenericPoint(genericPointDateString, meetingNumber, season);
        if(bodEntity.getDatum() != null)
            bodEntityService.createOrUpdate(bodEntity);
    }

    public static void ProcessAllMeetings(String inPath, String seasonString) {
        System.out.println("----BodReader----");
        OrganyEntity season = helper.EntityHelper.getSeason(seasonString); //TODO season == null

        long meetingsCount = FileHelper.GetMeetingsCount(inPath);
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
                System.out.println("BodReader - meeting: " + i);
                ProcessOneMeeting(currentFile, i, season);
            }
        }
    }
}
