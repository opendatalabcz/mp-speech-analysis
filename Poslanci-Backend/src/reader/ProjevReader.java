package reader;

import creator.SlovoCreatorData;
import creator.ZminkaCreator;
import helper.*;
import poslanciDB.entity.*;
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
        OrganyEntity season = helper.EntityHelper.getSeason(seasonString);

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
        BodEntity currentBod = null;
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
                if(validSpeechPart(element)){
                    if(element.childrenSize() > 0 && validSpeaker(element.child(0)) != null) {
                        if(currentOsobaId != null ) {
                            processAndSaveProjevtoDB(currentOsobaId, projevText, poradi, currentBod);
                            poradi++;
                        }
                        projevText = null;
                        currentOsobaId = validSpeaker(element.child(0));
                    }
                    if(projevText == null || projevText.isEmpty()) {
                        projevText = element.ownText();
                    } else {
                        projevText = projevText.concat(" " + element.ownText());
                    }
                }
                BodEntity newBod = getCurrentBod(element, currentBod);
                if(newBod != null && !newBod.equals(currentBod)) {
                    currentBod = newBod;
                    if(projevText == null || projevText.isEmpty()) {
                        projevText = currentBod.getText();
                    } else {
                        projevText = projevText.concat(" " + currentBod.getText());
                    }
                }
            }
            meetingPartNum++;
            completePath = dir + "/s" + String.format("%03d",meetingNumber) + String.format("%03d",meetingPartNum) + ".htm";
        }
        processAndSaveProjevtoDB(currentOsobaId, projevText, poradi, currentBod);
    }

    private static Integer getOsobaIdFromVlada(String vladaString) {
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
        while(elem.tagName().equalsIgnoreCase("b") && elem.childrenSize() > 0)
            elem = elem.child(0);

        if(!elem.tagName().equalsIgnoreCase("a")) return null;
        Attributes atrs = elem.attributes();
        Integer id = null;

        for (Attribute atr:atrs) {
            if(atr.getKey().equalsIgnoreCase("href")){
                if(atr.getValue().startsWith(snemovna)){
                    if(ParseHelper.tryParseInt(atr.getValue().substring(snemovna.length()))) {
                        id = Integer.parseInt(atr.getValue().substring(snemovna.length()));
                        return id;
                    }
                }
            }
        }
        for (Attribute atr:atrs) {
            if(atr.getKey().equalsIgnoreCase("href")){
                if(atr.getValue().startsWith(vladaHttp) || atr.getValue().startsWith(vladaHttps)){
                    id = getOsobaIdFromVlada(atr.getValue());
                    return id;
                }
            }
        }
        //System.out.println("Mluvi: " + elem.ownText());
        id = getOsobyIdFromName(elem.ownText());
        return id;
    }

    private static Integer getOsobyIdFromName(String text) {
        if(poslanecEntityList == null) return null;
        if(text == null || text.isEmpty()) return null;

        List<Integer> osobyIdList = new ArrayList<>();
        for(Object obj : poslanecEntityList) {
            PoslanecEntity poslanecEntity;
            try {
                poslanecEntity = (PoslanecEntity)obj;
            } catch (Exception e) {
                continue;
            }
            OsobyEntity osobyEntity = poslanecEntity.getOsobyByIdOsoba();
            String name = osobyEntity.getJmeno() + " " + osobyEntity.getPrijmeni();
            if(text.endsWith(name))
                osobyIdList.add(osobyEntity.getIdOsoba());
        }
        if(osobyIdList.size() > 1) {
            Exception exception = new Exception("Vice poslancu se stejnym jmenem");
            exception.printStackTrace();
            return null;
        }
        if(osobyIdList.size() == 1) return osobyIdList.get(0);

        Integer ret = null;
        int similarity = Integer.MAX_VALUE;
        for(Object obj : poslanecEntityList) {
            PoslanecEntity poslanecEntity;
            try {
                poslanecEntity = (PoslanecEntity)obj;
            } catch (Exception e) {
                continue;
            }
            OsobyEntity osobyEntity = poslanecEntity.getOsobyByIdOsoba();
            String name = osobyEntity.getJmeno() + " " + osobyEntity.getPrijmeni();
            Integer nameWordsCount = helper.StringHelper.wordCount(name);
            String textCut = helper.StringHelper.getLastNWordsInString(text, nameWordsCount);

            int newSimilarity = StringUtils.getLevenshteinDistance(name, textCut);
            if(newSimilarity < similarity) {
                similarity = newSimilarity;
                ret = osobyEntity.getIdOsoba();
            }
        }
        if(similarity > 2) {
            Exception exception = new Exception("Pouzil jsem editacni vzdalenost > 2, text: '" + text + "'");
            //exception.printStackTrace();
            return null;
        }

        return ret;
    }

    private static boolean validSpeechPart(Element elem){
        if(!elem.tagName().equalsIgnoreCase("p")) return false;
        boolean ret = false;
        if(!elem.ownText().isEmpty())
            ret = true;
        return ret;
    }

    private static BodEntity getCurrentBod(Element elem, BodEntity oldBod) {
        if(oldBod == null) oldBod = findRightBodEntity(null);
        String textBod = null;
        if(elem.tagName().equalsIgnoreCase("p")) {
            Attributes atrs = elem.attributes();
            for(Attribute atr:atrs){
                if(atr.getKey().equalsIgnoreCase("align") && atr.getValue().equalsIgnoreCase("center")) {
                    if(elem.childrenSize() > 0 && elem.child(0).tagName().equalsIgnoreCase("b"))
                        textBod = elem.child(0).ownText();
                    else
                        textBod = elem.ownText();

                    if(textBod == null || textBod.isEmpty() || textBod.length() < 5)
                        return oldBod;
                    else
                        return findRightBodEntity(textBod);
                }
            }
        } else if(elem.tagName().equalsIgnoreCase("center")) {
            if(elem.childrenSize() > 0 && elem.child(0).tagName().equalsIgnoreCase("b"))
                textBod = elem.child(0).ownText();
            if(textBod == null || textBod.isEmpty() || textBod.length() < 5)
                return oldBod;
            else
                return findRightBodEntity(textBod);
        }
        return oldBod;
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
        if(similarity < 10)
            return ret;

        tema = "---Provozní úkony---";
        similarity = Integer.MAX_VALUE;
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
                                                 Integer poradi, BodEntity bodEntity) {
        projevText = formatProjevText(projevText);
        PoslanecEntity poslanecEntity = findRightPoslanecEntity(currentOsobaId);
        if(poslanecEntity != null && bodEntity != null && bodEntity.getDatum() != null){
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
            sentiment = MathHelper.countSentiment(pocetPosSlov, pocetNegSlov);
        projevEntity.setPocetPosSlov(pocetPosSlov);
        projevEntity.setPocetNegSlov(pocetNegSlov);
        projevEntity.setSentiment(sentiment);
        return projevEntity;
    }
}
