package horynja2.textextractor;

import horynja2.unlreader.UnlFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllMeetings {
    public static Map<Integer,String> getPoslanecCurrentMap(String path){
        HashMap<Integer,String> poslanecCurrentMap = new HashMap<>();
        UnlFile file = new UnlFile(path);
        List<String> idColumn = file.getColumn(0);
        List<String> nameColumn = file.getColumn(1);
        List<String> surnameColumn = file.getColumn(2);
        for(int i = 0; i < file.rowCount(); i++){
            String poslanecFullname = nameColumn.get(i) + " " + surnameColumn.get(i);
            Integer id = Integer.parseInt(idColumn.get(i));
            poslanecCurrentMap.put(id, poslanecFullname);
        }
        return poslanecCurrentMap;
    }

    public static void main(String[] args) throws IOException {
        Map<Integer, String> poslanecCurrentMap = getPoslanecCurrentMap("resources/newData/poslanec-current.unl");
        Integer meetingsCount = 38;
        for(int i = 1; i <= meetingsCount; i++) {
            MeetingDir.MeetingDir(String.format("%03d",i) + "schuz", poslanecCurrentMap);
        }



        //MeetingDir.MeetingDir("033" + "schuz", poslanecCurrentMap);
    }
}
