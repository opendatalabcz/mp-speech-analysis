package reader;

import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.TypOrganuEntity;
import helper.Timer;
import poslanciDB.service.OrganyEntityService;
import poslanciDB.service.TypOrganuEntityService;

import java.sql.Date;
import java.util.List;

import static helper.ParseHelper.*;

public class OrganyReader {
    static OrganyEntityService organyEntityService = new OrganyEntityService();
    static TypOrganuEntityService typOrganuEntityService = new TypOrganuEntityService();

    public static void main(String[] args) {
        String path = "resources/organy.unl";
        readAndCreateAllOrgany(path);
    }

    public static void readAndCreateAllOrgany(String path) {
        System.out.println();
        System.out.println("readAndCreateAllOrgany(" + path + ")");

        AbstractUNLFileReader abstractUNLFileReader = new AbstractUNLFileReader(path);

        List<String> myList;
        Timer timer = new Timer();

        while ((myList = abstractUNLFileReader.getLineList()) != null) {
            OrganyEntity organyEntity = CreateOrganyEntityFromStringList(myList);
            organyEntityService.createOrUpdate(organyEntity);
            System.out.println("ORGANY - TIME: " + timer.getTime() + " --- CURRENT ID: " +
                    organyEntity.getIdOrgan());
        }
        System.out.println("ORGANY - FINAL TIME: " + timer.getTime());
    }

    private static OrganyEntity CreateOrganyEntityFromStringList(List<String> list) {
        if(list.size() != 10) throw new IllegalArgumentException();

        Integer idOrgan, organIdOrgan, idTypOrganu, priorita, clOrganBase;
        String zkratka, nazevOrganuCz, nazevOrganuEn;
        Date odOrgan, doOrgan;
        TypOrganuEntity typOrganuEntity;
        String pattern = "dd.MM.yyyy";

        if(tryParseInt(list.get(0))){
            idOrgan = Integer.parseInt(list.get(0));
        } else throw new IllegalArgumentException();

        if(tryParseInt(list.get(1))){
            organIdOrgan = Integer.parseInt(list.get(1));
        } else organIdOrgan = null;

        if(tryParseInt(list.get(2))){
            idTypOrganu = Integer.parseInt(list.get(2));
        } else throw new IllegalArgumentException();
        typOrganuEntity = typOrganuEntityService.find(idTypOrganu);

        zkratka = removeUselessWhitespacesString(list.get(3));
        nazevOrganuCz = removeUselessWhitespacesString(list.get(4));
        nazevOrganuEn = removeUselessWhitespacesString(list.get(5));
        odOrgan = getSqlDateFromString(list.get(6), pattern);
        doOrgan = getSqlDateFromString(list.get(7), pattern);

        if(tryParseInt(list.get(8))){
            priorita = Integer.parseInt(list.get(8));
        } else priorita = null;

        if(tryParseInt(list.get(9))){
            clOrganBase = Integer.parseInt(list.get(9));
        } else clOrganBase = null;

        return new OrganyEntity(idOrgan, organIdOrgan, zkratka, nazevOrganuCz, nazevOrganuEn, odOrgan, doOrgan,
                priorita, clOrganBase, typOrganuEntity);
    }
}
