package main;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Downloader {
    private static String URL0Part = "https://www.psp.cz/eknih/";
    private static String URL1Part = "/stenprot/zip/";
    private static String URL2Part = "schuz.zip";

    private static String tempZipFilePath = "temp/temp.zip";
    //"https://www.psp.cz/eknih/2017ps/stenprot/zip/001schuz.zip"

    public static void downloadUNLFiles(String path) {
        //https://www.psp.cz/eknih/cdrom/opendata/poslanci.zip
        String URL = "https://www.psp.cz/eknih/cdrom/opendata/poslanci.zip";
        if(downloadZipToTemp(URL))
            unzipTempZipToPath(path);
    }

    private static boolean downloadZipToTemp(String URL) {
        File file = new File(tempZipFilePath);
        try {
            FileUtils.copyURLToFile(new URL(URL), file);
        } catch (Exception e) {
            return false;
        }
        System.out.println("Downloading - " + URL);
        return true;
    }

    private static void unzipTempZipToPath(String outputPath) {
        System.out.println("Extracting to - " + outputPath);
        File outputDir = new File(outputPath);
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(new File(tempZipFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Enumeration<? extends ZipEntry> entries = null;
            if (zipFile != null) {
                entries = zipFile.entries();
            }
            if (entries != null) {
                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    File entryDestination = new File(outputDir,  entry.getName());
                    if (entry.isDirectory()) {
                        entryDestination.mkdirs();
                    } else {
                        entryDestination.getParentFile().mkdirs();
                        InputStream in = zipFile.getInputStream(entry);
                        OutputStream out = new FileOutputStream(entryDestination);
                        IOUtils.copy(in, out);
                        in.close();
                        out.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (zipFile != null) {
                    zipFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void downloadOneSeason(String path, String season) {
        String pathSeason = path + "/" + season;
        int i = 1;
        while(Downloader.downloadOneMeeting(pathSeason, season, i)) {
            i++;
        }
    }

    public static boolean downloadOneMeeting(String pathSeason, String season, Integer meeting) {
        String URL = URL0Part + season + URL1Part + getMeetingNumber3Digits(meeting) + URL2Part;
        String pathMeeting = pathSeason + "/" + String.format("%03d", meeting) + "schuz";
        if(downloadZipToTemp(URL)){
            unzipTempZipToPath(pathMeeting);
            return true;
        }
        return false;
    }

    public static String getMeetingNumber3Digits(Integer meeting) {
        return String.format("%03d", meeting);
    }
}
