package reader;

import poslanciDB.entity.OrganyEntity;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import helper.Timer;
import poslanciDB.service.OrganyEntityService;
import poslanciDB.service.OsobyEntityService;
import poslanciDB.service.PoslanecEntityService;

import java.util.Collection;
import java.util.List;

import static helper.ParseHelper.removeUselessWhitespacesString;
import static helper.ParseHelper.tryParseInt;

public class PoslanecReader {
    static OsobyEntityService osobyEntityService = new OsobyEntityService();
    static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
    static OrganyEntityService organyEntityService = new OrganyEntityService();

    public static void main(String[] args) {
        String path = "resources/poslanec.unl";
        readAndCreateAllPoslanec(path);
    }

    public static void readAndCreateAllPoslanec(String path) {
        System.out.println();
        System.out.println("readAndCreateAllPoslanec(" + path + ")");

        AbstractUNLFileReader abstractUNLFileReader = new AbstractUNLFileReader(path);
        List<String> myList;
        Timer timer = new Timer();

        while ((myList = abstractUNLFileReader.getLineList()) != null) {
            PoslanecEntity poslanecEntity = CreatePoslanecEntityFromStringList(myList);
            //poslanecEntityService.createOrUpdate(poslanecEntity);
            OsobyEntity osobyEntity = poslanecEntity.getOsobyByIdOsoba();
            Collection<PoslanecEntity> poslanecEntities = osobyEntity.getPoslanecsByIdOsoba();
            poslanecEntities.add(poslanecEntity);
            osobyEntity.setPoslanecsByIdOsoba(poslanecEntities);
            osobyEntityService.createOrUpdate(osobyEntity);
            System.out.println("POSLANEC - TIME: " + timer.getTime() + " --- CURRENT ID: " +
                    poslanecEntity.getIdPoslanec());
        }
        System.out.println("POSLANEC - FINAL TIME: " + timer.getTime());
    }

    private static PoslanecEntity CreatePoslanecEntityFromStringList(List<String> list) {
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
}

