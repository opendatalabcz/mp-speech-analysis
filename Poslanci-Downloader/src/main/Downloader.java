package main;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Downloader {
    //https://www.psp.cz/eknih/2017ps/stenprot/zip/014schuz.zip
    private static String URLcompressed0Part = "https://www.psp.cz/eknih/";
    private static String URLcompressed1Part = "/stenprot/zip/";
    private static String URLcompressed2Part = "schuz.zip";

    //https://www.psp.cz/eknih/2006ps/stenprot/001schuz/s001001.htm
    private static String URL0Part = "https://www.psp.cz/eknih/";
    private static String URL1Part = "/stenprot/";
    private static String URL2Part = "schuz/";
    private static String URL3Part = ".htm";

    private static String tempZipFilePath = "temp/temp.zip";

    public static void downloadUNLFiles(String path) {
        //https://www.psp.cz/eknih/cdrom/opendata/poslanci.zip
        String URL = "https://www.psp.cz/eknih/cdrom/opendata/poslanci.zip";
        if(downloadZipToTemp(URL))
            unzipTempZipToPath(path);
    }

    private static boolean unzipTempZipToPath(String outputPath) {
        System.out.println("Extracting to - " + outputPath);
        File outputDir = new File(outputPath);
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(new File(tempZipFilePath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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
        return true;
    }

    public static void downloadOneSeason(String path, String season) {
        String pathSeason = path + "/" + season;
        int i = 1;
        while(Downloader.downloadOneMeeting(pathSeason, season, i)) {
            i++;
        }
    }

    public static boolean downloadOneMeeting(String pathSeason, String season, Integer meeting) {
        String URL = URLcompressed0Part + season + URLcompressed1Part + getNumber3Digits(meeting) + URLcompressed2Part;
        String pathMeeting = pathSeason + "/" + getNumber3Digits(meeting) + "schuz";
        if(downloadZipToTemp(URL)){
            if(unzipTempZipToPath(pathMeeting)) {
                return true;
            }
        }

        if(downloadOneMeetingNoncompressedVersion(meeting, season, pathMeeting))
            return true;

        return false;
    }

    private static boolean downloadOneMeetingNoncompressedVersion(Integer meeting, String season, String pathMeeting) {
        String meetingString = getNumber3Digits(meeting);
        String baseURL = URL0Part + season + URL1Part + meetingString + URL2Part;
        if(!downloadNoncompressedIndex(baseURL, pathMeeting)) return false;

        boolean ret = false;
        int i = 1;
        while(downloadNoncompressedMeetingPart(baseURL, i, meeting, pathMeeting)) {
            ret = true;
            i++;
        }

        return ret;
    }

    private static boolean downloadNoncompressedMeetingPart(String baseURL, int i, Integer meeting, String pathMeeting) {
        String filename = "s"+ getNumber3Digits(meeting) + getNumber3Digits(i) + URL3Part;
        String URL = baseURL + filename;
        String filepath = pathMeeting + "/" + filename;
        return downloadFileToDest(URL, filepath);
    }

    private static boolean downloadNoncompressedIndex(String baseURL, String pathMeeting) {
        String URL = baseURL + "index" + URL3Part;
        return downloadFileToDest(URL, pathMeeting + "/index.htm");
    }


    private static boolean downloadZipToTemp(String URL) {
        return downloadFileToDest(URL, tempZipFilePath);
    }

    private static boolean downloadFileToDest(String URL, String dest) {
        File file = new File(dest);
        try {
            FileUtils.copyURLToFile(new URL(URL), file);
        } catch (Exception e) {
            return false;
        }
        System.out.println("Downloading - " + URL);
        return true;
    }

    public static String getNumber3Digits(Integer meeting) {
        return String.format("%03d", meeting);
    }
}
