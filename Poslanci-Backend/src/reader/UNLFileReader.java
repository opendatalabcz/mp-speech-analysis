package reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UNLFileReader {
    BufferedReader file = null;

    public UNLFileReader(String path) {
        try {
            file = new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("Windows-1250")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getLineList() {
        if(file != null) {
            String line;
            try {
                if((line = file.readLine()) != null) {
                    return new ArrayList<String>(Arrays.asList(line.replaceAll("\\|$","").split("\\|", -1)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
