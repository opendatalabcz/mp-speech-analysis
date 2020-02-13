package helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileHelper {

    public static long GetMeetingsCount(String inPath) {
        long count = -1;
        try (Stream<Path> files = Files.list(Paths.get(inPath))) {
            count = files.count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}
