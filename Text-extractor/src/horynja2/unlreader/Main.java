package horynja2.unlreader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UnlFile osoby = new UnlFile("resources/posl/osoby.unl");
        UnlFile poslanec = new UnlFile("resources/posl/poslanec.unl");
        List<String> PCond = poslanec.getColumn(4);
        List<String> PIds = poslanec.getColumn(1);
        List<String> PIdsSelected = new ArrayList<>();
        List<String> OName = osoby.getColumn(3);
        List<String> OSurname = osoby.getColumn(2);
        List<String> OIds = osoby.getColumn(0);
        OutputStreamWriter poslanecCurrent = null;
        try {
            poslanecCurrent = new OutputStreamWriter(new FileOutputStream("resources/newData/poslanec-current.unl"), Charset.forName("Windows-1250"));
        } catch (Exception e){
            System.out.println("Could not create file resources/newData/poslanec-current.unl");
        }



        for(int i = 0; i < PIds.size(); i++){
            if(PCond.get(i).equals("172")) {
                PIdsSelected.add(PIds.get(i));
            }
        }
        for(int i = 0; i < OIds.size(); i++){
            if(PIdsSelected.contains(OIds.get(i))){
                String out = OIds.get(i)+"|"+OName.get(i)+"|"+OSurname.get(i)+"|";
                System.out.println(out);
                try{
                    poslanecCurrent.append(out + "\n");
                } catch (Exception e){
                    System.out.println("Could not append to file resources/newData/poslanec-current.unl");
                }
            }
        }
        try {
            poslanecCurrent.close();
        } catch (Exception e){
            System.out.println("Could not close file resources/newData/poslanec-current.unl");
        }
    }
}
