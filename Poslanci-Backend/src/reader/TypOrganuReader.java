package reader;

import poslanciDB.entity.TypOrganuEntity;
import helper.Timer;
import poslanciDB.service.TypOrganuEntityService;

import java.util.List;

import static helper.ParseHelper.removeUselessWhitespacesString;
import static helper.ParseHelper.tryParseInt;

public class TypOrganuReader {
    static TypOrganuEntityService typOrganuEntityService = new TypOrganuEntityService();

    public static void main(String[] args) {
        String path = "resources/typ_organu.unl";
        readAndCreateAllTypOrganu(path);
    }

    public static void readAndCreateAllTypOrganu(String path) {
        System.out.println();
        System.out.println("readAndCreateAllTypOrganu(" + path + ")");

        AbstractUNLFileReader abstractUNLFileReader = new AbstractUNLFileReader(path);

        List<String> myList;
        Timer timer = new Timer();

        while ((myList = abstractUNLFileReader.getLineList()) != null) {
            TypOrganuEntity typOrganuEntity = CreateTypOrganuEntityFromStringList(myList);
            typOrganuEntityService.createOrUpdate(typOrganuEntity);
            System.out.println("TYP_ORGANU - TIME: " + timer.getTime() + " --- CURRENT ID: " +
                    typOrganuEntity.getIdTypOrg());
        }
        System.out.println("TYP_ORGANU - FINAL TIME: " + timer.getTime());
    }

    private static TypOrganuEntity CreateTypOrganuEntityFromStringList(List<String> list) {
        if(list.size() != 6) throw new IllegalArgumentException();

        Integer idTypOrg, typIdTypOrg, typOrgObecny, priorita;
        String nazevTypOrgCz, nazevTypOrgEn;

        if(tryParseInt(list.get(0))){
            idTypOrg = Integer.parseInt(list.get(0));
        } else throw new IllegalArgumentException();

        if(tryParseInt(list.get(1))){
            typIdTypOrg = Integer.parseInt(list.get(1));
        } else typIdTypOrg = null;

        nazevTypOrgCz = removeUselessWhitespacesString(list.get(2));
        nazevTypOrgEn = removeUselessWhitespacesString(list.get(3));

        if(tryParseInt(list.get(4))){
            typOrgObecny = Integer.parseInt(list.get(4));
        } else typOrgObecny = null;

        if(tryParseInt(list.get(5))){
            priorita = Integer.parseInt(list.get(5));
        } else priorita = null;

        return new TypOrganuEntity(idTypOrg, typIdTypOrg, nazevTypOrgCz, nazevTypOrgEn, typOrgObecny, priorita);
    }
}
