package horynja2.unlreader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class UnlFile {
    private List<String> records = new ArrayList<>();
    private Integer columnCount;
    private Character separator = '|';

    public UnlFile(String path) {
        BufferedReader file;
        try {
            file = new BufferedReader(new InputStreamReader(new FileInputStream(path),Charset.forName("Windows-1250")));
        } catch (Exception e) {
            System.out.println("UnlFile(String path) - wrong path: " + path + " (" + e.toString() + ")");
            return;
        }
        String line;
        try {
            while ((line = file.readLine()) != null) {
                records.add(line);
            }
        } catch (Exception e) {
            System.out.println("UnlFile(String path) - corrupted file " + " (" + e.toString() + ")");
        }
        if (records.size() > 0) {
            Integer sum = 0;
            for(char ch:records.get(0).toCharArray()){
                if(ch == separator) sum++;
            }
            columnCount = sum + 1;
        }
    }

    public List<String> getColumn(Integer col) {
        if (col > columnCount) {
            System.out.println("getColumn(Integer col) - file has not so many columns:");
            return null;
        }
        List<String> ret = new ArrayList<>();
        records.forEach(e -> ret.add(getPosString(e,col)));
        return ret;
    }

    public Integer rowCount(){
        return records.size();
    }

    private String getPosString(String input, Integer pos){
        String[] split = input.split("\\|",0);
        return (split[pos]);
    }
}
