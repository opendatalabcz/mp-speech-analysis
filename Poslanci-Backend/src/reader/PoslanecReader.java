package reader;

import helper.Timer;
import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.service.OrganyEntityService;
import poslanciDB.service.OsobyEntityService;
import poslanciDB.service.PoslanecEntityService;

import java.util.Collection;
import java.util.List;

import static helper.StringHelper.removeUselessWhitespacesString;
import static helper.StringHelper.tryParseInt;

public class PoslanecReader {
    private static OsobyEntityService osobyEntityService = new OsobyEntityService();
    private static OrganyEntityService organyEntityService = new OrganyEntityService();
    private static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
    private static OrganyEntity kandidatkaZbytek;

    public static void readAndCreateAllPoslanec(String path) {
        System.out.println();
        System.out.println("readAndCreateAllPoslanec(" + path + ")");

        UNLFileReader UNLFileReader = new UNLFileReader(path);
        List<String> myList;
        Timer timer = new Timer();
        setKandidatkaZbytek();

        //program prochazi unl soubor a z kazdeho radku vytvori novou entitu, kterou navaze na prislusnou entitu Osoba
        while ((myList = UNLFileReader.getLineList()) != null) {
            PoslanecEntity poslanecEntity = CreatePoslanecEntityFromStringList(myList);
            OsobyEntity osobyEntity = poslanecEntity.getOsobyByIdOsoba();
            Collection<PoslanecEntity> poslanecEntities = osobyEntity.getPoslanecsByIdOsoba();
            //jestli uz poslanec s danym ID existoval, tak je nahrazen aktualni verzi
            poslanecEntities.removeIf(posl -> posl.getIdPoslanec().equals(poslanecEntity.getIdPoslanec()));
            poslanecEntities.add(poslanecEntity);
            osobyEntity.setPoslanecsByIdOsoba(poslanecEntities);
            poslanecEntityService.createOrUpdate(poslanecEntity);
            osobyEntityService.createOrUpdate(osobyEntity);
            System.out.println("POSLANEC - TIME: " + timer.getTime() + " --- CURRENT ID: " +
                    poslanecEntity.getIdPoslanec());
        }
        System.out.println("POSLANEC - FINAL TIME: " + timer.getTime());
    }

    private static PoslanecEntity CreatePoslanecEntityFromStringList(List<String> list) {
        //tato entita ma presne 15 atributu
        if(list.size() != 15) throw new IllegalArgumentException();

        Integer idPoslanec, idKraj, idKandidatka, idObdobi, foto;
        String web, ulice, obec, psc, email, telefon, fax, pspTelefon, facebook;
        Integer idOsoba;
        OsobyEntity osobyByIdOsoba;
        OrganyEntity organyByIdKraj, organyByIdKandidatka, organyByIdObdobi;

        if(tryParseInt(list.get(0))){
            idPoslanec = Integer.parseInt(list.get(0));
        } else throw new IllegalArgumentException();

        if(tryParseInt(list.get(1))){
            idOsoba = Integer.parseInt(list.get(1));
        } else throw new IllegalArgumentException();
        osobyByIdOsoba  = osobyEntityService.find(idOsoba);

        if(tryParseInt(list.get(2))){
            idKraj = Integer.parseInt(list.get(2));
        } else throw new IllegalArgumentException();
        organyByIdKraj = organyEntityService.find(idKraj);

        if(tryParseInt(list.get(3))){
            idKandidatka = Integer.parseInt(list.get(3));
        } else throw new IllegalArgumentException();
        organyByIdKandidatka = organyEntityService.find(idKandidatka);
        if(organyByIdKandidatka == null) organyByIdKandidatka = kandidatkaZbytek;

        if(tryParseInt(list.get(4))){
            idObdobi = Integer.parseInt(list.get(4));
        } else throw new IllegalArgumentException();
        organyByIdObdobi = organyEntityService.find(idObdobi);

        web = removeUselessWhitespacesString(list.get(5));
        ulice = removeUselessWhitespacesString(list.get(6));
        obec = removeUselessWhitespacesString(list.get(7));
        psc = removeUselessWhitespacesString(list.get(8));
        email = removeUselessWhitespacesString(list.get(9));
        telefon = removeUselessWhitespacesString(list.get(10));
        fax = removeUselessWhitespacesString(list.get(11));
        pspTelefon = removeUselessWhitespacesString(list.get(12));
        facebook = removeUselessWhitespacesString(list.get(13));

        if(tryParseInt(list.get(14))){
            foto = Integer.parseInt(list.get(14));
        } else foto = null;

        return new PoslanecEntity(idPoslanec, organyByIdKraj, organyByIdKandidatka, organyByIdObdobi, web, ulice, obec,
                psc, email, telefon, fax, pspTelefon, facebook, foto, osobyByIdOsoba);
    }

    public static void setKandidatkaZbytek() {
        kandidatkaZbytek = new OrganyEntity();
        kandidatkaZbytek.setIdOrgan(-1);
        kandidatkaZbytek.setZkratka("-");
        kandidatkaZbytek.setNazevOrganuCz("\"Strana neurčena\"");
        kandidatkaZbytek.setNazevOrganuEn("-");
        organyEntityService.createOrUpdate(kandidatkaZbytek);
    }
}

