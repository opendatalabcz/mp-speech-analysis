package reader;

import poslanciDB.entity.OsobyEntity;
import helper.Timer;
import poslanciDB.service.OsobyEntityService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static helper.ParseHelper.*;

public class OsobyReader {
    static OsobyEntityService osobyEntityService = new OsobyEntityService();

    public static void main(String[] args) {
        String path = "resources/osoby.unl";
        AbstractUNLFileReader abstractUNLFileReader = new AbstractUNLFileReader(path);
        List<String> myList;
        Timer timer = new Timer();

        while ((myList = abstractUNLFileReader.getLineList()) != null) {
            OsobyEntity osobyEntity = CreateOsobaEntityFromStringList(myList);
            osobyEntityService.createOrUpdate(osobyEntity);
            System.out.println("CURRENT ESTIMATED TIME: " + timer.getTime() + " --- CURRENT ID: " +
                    osobyEntity.getIdOsoba());
        }
        System.out.println("ESTIMATED TIME: " + timer.getTime());
    }

    private static OsobyEntity CreateOsobaEntityFromStringList(List<String> list){
        if(list.size() != 9) throw new IllegalArgumentException();

        Integer idOsoba;
        String pred, jmeno, prijmeni, za, pohlavi;
        Date narozeni, zmena, umrti;
        String pattern = "dd.MM.yyyy";

        if(tryParseInt(list.get(0))){
            idOsoba = Integer.parseInt(list.get(0));
        } else throw new IllegalArgumentException();

        pred = removeUselessWhitespacesString(list.get(1));
        jmeno = removeUselessWhitespacesString(list.get(3));
        prijmeni = removeUselessWhitespacesString(list.get(2));
        za = removeUselessWhitespacesString(list.get(4));
        narozeni = getSqlDateFromString(list.get(5), pattern);
        pohlavi = removeUselessWhitespacesString(list.get(6));
        zmena = getSqlDateFromString(list.get(7), pattern);
        umrti = getSqlDateFromString(list.get(8), pattern);

        return new OsobyEntity(idOsoba, pred, jmeno, prijmeni, za, narozeni, pohlavi, zmena, umrti);
    }




    public static List<String> SplitString(String input) {
        List<String> list = new ArrayList<>();
        String string1, string2;
        Pattern pattern = Pattern.compile("\\| *");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            string1 = input.substring(0, matcher.start());
            string2 = input.substring(matcher.end());
            matcher = pattern.matcher(string2);
            list.add(string1);
        }
        return list;
    }
}