package reader;

import entity.OsobyEntity;
import service.OsobyEntityService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static helper.ParseHelper.*;

public class OsobyReader {
    static OsobyEntityService osobyEntityService = new OsobyEntityService();

    public static void main(String[] args) {
        String path = "osoby.unl";
        BufferedReader file;
        try {
            file = new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("Windows-1250")));
        } catch (Exception e) {
            System.out.println("UnlFile(String path) - wrong path: " + path + " (" + e.toString() + ")");
            return;
        }
        String line;
        long startTime = System.currentTimeMillis();

        try {

             /*
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("NewPersistenceUnit");
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            while ((line = file.readLine()) != null) {
                List<String> myList = new ArrayList<String>(Arrays.asList(line.replaceAll("\\|$","").split("\\|", -1)));
                //System.out.println(myList.size() + "---" + Arrays.toString(myList.toArray()));
                OsobyEntity osobyEntity = CreateOsobaEntityFromStringList(myList);
                entityManager.persist(osobyEntity);
                long estimatedTime = System.currentTimeMillis() - startTime;
                System.out.println("CURRENT ESTIMATED TIME: " + new java.util.Date(estimatedTime) + " (" + estimatedTime + ") --- ACTUAL ID: " +
                        osobyEntity.getIdOsoba());
            }
            entityManager.getTransaction().commit();
            entityManagerFactory.close();
*/

            while ((line = file.readLine()) != null) {
                List<String> myList = new ArrayList<String>(Arrays.asList(line.replaceAll("\\|$","").split("\\|", -1)));
                OsobyEntity osobyEntity = CreateOsobaEntityFromStringList(myList);
                osobyEntityService.createOrUpdate(osobyEntity);
                long estimatedTime = System.currentTimeMillis() - startTime;
                System.out.println("CURRENT ESTIMATED TIME: " + new java.util.Date(estimatedTime) + " (" + estimatedTime + ") --- ACTUAL ID: " +
                        osobyEntity.getIdOsoba());
            }

        } catch (Exception e) {
            System.out.println("UnlFile(String path) - corrupted file " + " (" + e.toString() + ")");
        }
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("ESTIMATED TIME: " + new java.util.Date(estimatedTime) + " (" + estimatedTime + ")");
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
        jmeno = removeUselessWhitespacesString(list.get(2));
        prijmeni = removeUselessWhitespacesString(list.get(3));
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
