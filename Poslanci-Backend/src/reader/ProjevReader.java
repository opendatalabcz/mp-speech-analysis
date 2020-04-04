package reader;

import creator.SlovoCreatorData;
import creator.ZminkaCreator;
import poslanciDB.entity.*;
import helper.FileHelper;
import helper.ParseHelper;
import helper.StringHelper;
import helper.Timer;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import poslanciDB.service.BodEntityService;
import poslanciDB.service.OrganyEntityService;
import poslanciDB.service.PoslanecEntityService;
import poslanciDB.service.ProjevEntityService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ProjevReader {
    private static String charset = "Windows-1250";
    private static String snemovna = "/sqw/detail.sqw?id=";
    private static String vladaHttps = "https://www.vlada.cz/cz/clenove-vlady/";
    private static String vladaHttp = "http://www.vlada.cz/cz/clenove-vlady/";
    private static Integer period = 172;
    private static Collection poslanecEntityList = null;
    private static List bodEntityList = null;
    private static OrganyEntityService organyEntityService = new OrganyEntityService();
    private static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
    private static BodEntityService bodEntityService = new BodEntityService();
    private static ProjevEntityService projevEntityService = new ProjevEntityService();

    public static void main(String[] args) {
        String inPath = "resources/Schuze/";
        OrganyEntity season = organyEntityService.find(172);
        ProcessAllMeetings(inPath, "PSP8");
    }

    public static void ProcessAllMeetings(String inPath, String seasonString) {
        System.out.println("----ProjevReader----");
        OrganyEntity season = helper.EntityHelper.getSeason(seasonString); //TODO season == null

        if(season == null) return;
        long meetingsCount = FileHelper.GetMeetingsCount(inPath);
        String dirName;
        if(meetingsCount < 0) {
            System.out.println("Neplatny pocet schuzi");
            return;
        }
        poslanecEntityList = season.getPoslanecsObdobiByIdOrgan();
        Timer timer = new Timer();
        for(int i = 1; i <= meetingsCount; i++) {
            System.out.println("ProjevReader - TIME: " + timer.getTime() + ", CURRENT MEETING: " +
                    i);
            dirName = inPath + String.format("%03d",i) + "schuz";
            bodEntityList = getAllPointsInMeeting(season, i);
            projevEntityService.multiBegin();
            ProcessOneMeeting(dirName, i);
            projevEntityService.multiCommit();
        }
        System.out.println("ProjevReader - FINAL TIME: " + timer.getTime());
    }

    private static List<BodEntity> getAllPointsInMeeting(OrganyEntity season, Integer meetingNumber) {
        List<BodEntity> bodEntityList = new ArrayList<>();
        for(Object obj : season.getBodsByIdOrgan()) {
            BodEntity bodEntity;
            try {
                bodEntity = (BodEntity)obj;
            } catch (Exception e) {
                continue;
            }
            if(bodEntity.getCisloSchuze().equals(meetingNumber)) bodEntityList.add(bodEntity);
        }
        return bodEntityList;
    }

    public static void ProcessOneMeeting(String dir, Integer meetingNumber) {
        Integer meetingPartNum = 1;
        Integer poradi = 0;

        String projevText = null;
        String currentBod = null;
        Integer currentOsobaId = null;
        String completePath = dir + "/s" + String.format("%03d",meetingNumber) + String.format("%03d",meetingPartNum) + ".htm";
        while(new File(completePath).exists()){
            File currentFile = new File(completePath);
            System.out.println("----File: " + currentFile.getName());
            Document doc = null;
            try {
                doc = Jsoup.parse(currentFile, charset);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            Elements elements = doc.body().select("*");

            for (Element element : elements) {
                if(element.childNodeSize() == 2) {
                    if(validSpeaker(element.child(0)) != null) {
                        if(currentOsobaId != null ) {
                            if(currentOsobaId == 443) {
                                int a = 8;
                            }
                            processAndSaveProjevtoDB(currentOsobaId, projevText, poradi, currentBod);
                            poradi++;
                            projevText = null;
                        }
                        currentOsobaId = validSpeaker(element.child(0));
                    }
                }
                if(validSpeechPart(element)){
                    if(projevText == null || projevText.isEmpty()) {
                        projevText = element.ownText();
                    } else {
                        projevText = projevText.concat(" " + element.ownText());
                    }
                } else if(validTheme(element)) {
                    currentBod = element.child(0).ownText();
                    if(projevText == null || projevText.isEmpty()) {
                        projevText = element.child(0).ownText();
                    } else {
                        projevText = projevText.concat(" " + element.child(0).ownText());
                    }
                }
            }
            meetingPartNum++;
            completePath = dir + "/s" + String.format("%03d",meetingNumber) + String.format("%03d",meetingPartNum) + ".htm";
        }
        processAndSaveProjevtoDB(currentOsobaId, projevText, poradi, currentBod);
    }

    private static Integer getPoslanecIdFromVlada(String vladaString) {
        //how could look vladaString - https://www.vlada.cz/cz/clenove-vlady/pri-uradu-vlady/jan_chvojka/zivotopis/jan-chvojka-151262/
        String[] firstSplit = vladaString.split("/");
        String[] secondSplit = firstSplit[firstSplit.length - 1].split("-");
        if(secondSplit.length < 2) return null;
        String name = secondSplit[0];
        String surname = secondSplit[1];
        for(Object object : poslanecEntityList) {
            PoslanecEntity poslanecEntity = (PoslanecEntity)object;
            OsobyEntity osobyEntity = poslanecEntity.getOsobyByIdOsoba();
            if(StringHelper.equalsIgnoreCaseAndDiacritics(osobyEntity.getJmeno(), name) &&
                    StringHelper.equalsIgnoreCaseAndDiacritics(osobyEntity.getPrijmeni(), surname)){
                return osobyEntity.getIdOsoba();
            }
        }
        return null;
    }

    private static Integer validSpeaker(Element elem){
        if(!elem.tagName().equals("a")) return null;
        Attributes atrs = elem.attributes();
        Integer id = null;

        for (Attribute atr:atrs) {
            if(atr.getKey().equals("href")){
                if(atr.getValue().startsWith(snemovna)){
                    if(ParseHelper.tryParseInt(atr.getValue().substring(snemovna.length()))) {
                        id = Integer.parseInt(atr.getValue().substring(snemovna.length()));
                        return id;
                    }
                }
            }
        }
        for (Attribute atr:atrs) {
            if(atr.getKey().equals("href")){
                if(atr.getValue().startsWith(vladaHttp) || atr.getValue().startsWith(vladaHttps)){
                    id = getPoslanecIdFromVlada(atr.getValue());
                    return id;
                }
            }
        }
        return id;
    }

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

    private static PoslanecEntity findRightPoslanecEntity(Integer osobaId) {
        for(Object object : poslanecEntityList) {
            PoslanecEntity poslanecEntity = (PoslanecEntity)object;
            if(poslanecEntity.getOsobyByIdOsoba().getIdOsoba().equals(osobaId))
                return poslanecEntity;
        }
        return null;
    }

    private static BodEntity findRightBodEntity(String tema) {
        if(tema == null || tema.isEmpty())
            tema = "---Provozní úkony---";
        BodEntity ret = null;
        int similarity = Integer.MAX_VALUE;
        if(bodEntityList.size() > 0) ret = (BodEntity)bodEntityList.get(0);
        for(Object object : bodEntityList) {
            BodEntity bodEntity = (BodEntity)object;
            int newSimilarity = StringUtils.getLevenshteinDistance(tema, bodEntity.getText());
            if(newSimilarity < similarity) {
                similarity = newSimilarity;
                ret = bodEntity;
            }
        }
        return ret;
    }

    private static String formatProjevText(String projevText) {
        String finalProjevText = projevText.replaceAll("\\(.*?\\)", "");
        finalProjevText = ParseHelper.removeUselessWhitespacesString(finalProjevText);
        finalProjevText = ParseHelper.removePrefix(finalProjevText, ": ");
        finalProjevText = ParseHelper.removePrefix(finalProjevText, " : ");
        return finalProjevText;
    }

    private static void processAndSaveProjevtoDB(Integer currentOsobaId, String projevText,
                                                 Integer poradi, String currentBod) {
        projevText = formatProjevText(projevText);
        PoslanecEntity poslanecEntity = findRightPoslanecEntity(currentOsobaId);
        BodEntity bodEntity = findRightBodEntity(currentBod);
        if(poslanecEntity != null){
            ProjevEntity projevEntity = new ProjevEntity(null, projevText, StringHelper.wordCount(projevText),
                    poradi, poslanecEntity, bodEntity);

            SlovoCreatorData slovoCreatorData = new SlovoCreatorData(projevEntity);
            slovoCreatorData.analyze();
            List<SlovoEntity> slovoEntities = slovoCreatorData.getSlovoEntities();
            projevEntity.setSlovosByIdProjev(slovoEntities);

            ZminkaCreator zminkaCreator = new ZminkaCreator(poslanecEntityList);
            for(SlovoEntity slovoEntity : slovoEntities) {
                zminkaCreator.processSlovoToZminka(slovoEntity);
            }
            List<ZminkaEntity> zminkaEntities = zminkaCreator.getZminkaList();
            projevEntity.setZminkasByIdProjev(zminkaEntities);

            projevEntity = processOneSpeechStats(projevEntity);

            //projevEntityService.createOrUpdate(projevEntity);
            projevEntityService.multiCreate(projevEntity);
        }
    }

    private static ProjevEntity processOneSpeechStats(ProjevEntity projevEntity) {
        //System.out.println("---Projev id: " + projevEntity.getIdProjev());
        //Integer idProjev = projevEntity.getIdProjev();
        Integer pocetPosSlov = 0, pocetNegSlov = 0;
        double sentiment = 0.0;
        for(SlovoEntity slovoEntity : projevEntity.getSlovosByIdProjev()) {
            if(slovoEntity.getSentiment() == 1) pocetPosSlov += slovoEntity.getPocetVyskytu();
            if(slovoEntity.getSentiment() == -1) pocetNegSlov += slovoEntity.getPocetVyskytu();
        }
        if(pocetPosSlov + pocetNegSlov != 0)
            sentiment = ((pocetPosSlov * 1.0) + (pocetNegSlov * (-1.0))) / (pocetPosSlov + pocetNegSlov);
        projevEntity.setPocetPosSlov(pocetPosSlov);
        projevEntity.setPocetNegSlov(pocetNegSlov);
        projevEntity.setSentiment(sentiment);
        return projevEntity;
    }
}
