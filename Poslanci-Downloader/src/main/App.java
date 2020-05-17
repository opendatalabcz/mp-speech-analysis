package main;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static main.Downloader.downloadOneSeason;
import static main.Downloader.downloadUNLFiles;

public class App {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Arguments: [path] [seasons...]");
            return;
        }
        String path = args[0];

        //stahne soubory o osobach, poslancich,...
        downloadUNLFiles(path);

        String[] seasons = Arrays.copyOfRange(args, 1, args.length);
        for(String season : seasons) {
            downloadOneSeason(path, season);
        }
        //nakonec vycisti nepotrebne docasne soubory
        deleteTemp();
    }

    private static void deleteTemp() {
        File temp = new File("temp");
        try {
            System.out.println("Deleting - temp");
            FileUtils.deleteDirectory(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
