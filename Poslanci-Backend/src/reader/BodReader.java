package reader;

import helper.FileHelper;
import helper.StringHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import poslanciDB.entity.BodEntity;
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
    private static String genericPointText = "---Provozní úkony---";

    public static BodEntity createGenericPoint(String unformatedDateString, Integer meetingNumber, OrganyEntity season) {
        String day = null, month = null, year = null, stringDate;

        //vytvori datum pro specialni jednaci datum, coz je datum prvniho jednani schuze, takze prvni den, mesci a rok, co program najde
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

        Date sqlDate = StringHelper.getSqlDateFromString(stringDate, "dd. MMMM yyyy");
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
                genericPointTextCreated = true;
            }
            //element s tagem "a" znaci potencialne (musi byt splneny jeste dalsi podminky) tag,
            //kde je uchovano datum projednavani
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
            //element s tagem "p" znaci potencialne (musi byt splneny jeste dalsi podminky) tag,
            //kde je uchovano zneni jednaciho bodu
            if(element.tagName().equalsIgnoreCase("p")) {
                for(Element child : element.children()){
                    if(child.tagName().equalsIgnoreCase("b")) {
                        //kontroluje se jestli stejny bod uz neni v databazi
                        BodEntity bodEntityAlreadyExists =
                                bodEntityService.checkTextExists(child.ownText(),
                                        meetingNumber, season.getIdOrgan()
                                );
                        BodEntity bodEntity;
                        //jestli v databazi neni, tak se vytvori, nezname ID, proto null
                        if(bodEntityAlreadyExists == null) {
                            bodEntity = new BodEntity(
                                    null,
                                    child.ownText(),
                                    meetingNumber,
                                    StringHelper.getSqlDateFromBod(element.ownText()),
                                    season
                            );
                        } else {
                            //jestli v databazi je, tak se upravi a ID zustane stejne
                            bodEntity = new BodEntity(
                                    bodEntityAlreadyExists.getIdBod(),
                                    child.ownText(),
                                    meetingNumber,
                                    StringHelper.getSqlDateFromBod(element.ownText()),
                                    season
                            );
                        }
                        if(bodEntity.getDatum() != null)
                            //zmeny v bodu nebo novy bod se ulozi na databazi
                            bodEntityService.createOrUpdate(bodEntity);
                    }
                }
            }
        }

        //vytvori se a ulozi na databazi specialni jednaci bod pro ruzne provozni ukony a pripadne chyby
        BodEntity bodEntity = createGenericPoint(genericPointDateString, meetingNumber, season);
        if(bodEntity.getDatum() != null)
            bodEntityService.createOrUpdate(bodEntity);
    }

    public static void ProcessAllMeetings(String inPath, String seasonString) {
        System.out.println("----BodReader----");
        OrganyEntity season = helper.EntityHelper.getSeason(seasonString);
        if(season == null) return;

        long meetingsCount = FileHelper.GetMeetingsCount(inPath);
        String dirName, completePath;
        if(meetingsCount < 0) {
            System.out.println("Neplatny pocet schuzi");
            return;
        }
        //prochazi index.htm u vsech schuzi a ziskava z nich seznamy projednavanych bodu
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
