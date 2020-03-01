package reader;

import entity.OsobyEntity;
import entity.PoslanecEntity;
import service.OsobyEntityService;
import service.PoslanecEntityService;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static helper.ParseHelper.removeUselessWhitespacesString;
import static helper.ParseHelper.tryParseInt;

public class PoslanecReaderOld {
    static OsobyEntityService osobyEntityService = new OsobyEntityService();
    static PoslanecEntityService poslanecEntityService = new PoslanecEntityService();

    public static void main(String[] args) {
        String path = "poslanec.unl";
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
            while ((line = file.readLine()) != null) {
                List<String> myList = new ArrayList<String>(Arrays.asList(line.replaceAll("\\|$","").split("\\|", -1)));
                PoslanecEntity poslanecEntity = CreatePoslanecEntityFromStringList(myList);
                poslanecEntityService.createOrUpdate(poslanecEntity);
                long estimatedTime = System.currentTimeMillis() - startTime;
                System.out.println("CURRENT ESTIMATED TIME: " + new java.util.Date(estimatedTime) + " (" + estimatedTime + ") --- ACTUAL ID: " +
                        poslanecEntity.getIdPoslanec());
            }

        } catch (Exception e) {
            System.out.println("UnlFile(String path) - corrupted file " + " (" + e.toString() + ")");
        }
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("ESTIMATED TIME: " + new java.util.Date(estimatedTime) + " (" + estimatedTime + ")");
    }

    private static PoslanecEntity CreatePoslanecEntityFromStringList(List<String> list) {
        if(list.size() != 15) throw new IllegalArgumentException();

        Integer idPoslanec, idKraj, idKandidatka, idObdobi, foto;
        String web, ulice, obec, psc, email, telefon, fax, pspTelefon, facebook;
        Integer idOsoba;
        //

        if(tryParseInt(list.get(0))){
            idPoslanec = Integer.parseInt(list.get(0));
        } else throw new IllegalArgumentException();


        if(tryParseInt(list.get(1))){
            idOsoba = Integer.parseInt(list.get(1));
        } else throw new IllegalArgumentException();
        OsobyEntity osobyByIdOsoba = osobyEntityService.find(idOsoba);

        if(tryParseInt(list.get(2))){
            idKraj = Integer.parseInt(list.get(2));
        } else throw new IllegalArgumentException();

        if(tryParseInt(list.get(3))){
            idKandidatka = Integer.parseInt(list.get(3));
        } else throw new IllegalArgumentException();

        if(tryParseInt(list.get(4))){
            idObdobi = Integer.parseInt(list.get(4));
        } else throw new IllegalArgumentException();

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
        //else throw new IllegalArgumentException();

        return new PoslanecEntity(idPoslanec, null, null, null, web, ulice, obec, psc, email, telefon, fax,
                pspTelefon, facebook, foto, osobyByIdOsoba);
/*
        return new PoslanecEntity(idPoslanec, idKraj, idKandidatka, idObdobi, web, ulice, obec, psc, email, telefon, fax,
                pspTelefon, facebook, foto, osobyByIdOsoba);*/
    }
}

