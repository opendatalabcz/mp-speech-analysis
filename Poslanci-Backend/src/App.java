import poslanciDB.entity.PoslanecEntity;
import poslanciDB.service.PoslanecEntityService;
import reader.OsobyReader;

public class App {
    public static void main(String[] args) {
        String mainPath = args[0];
        OsobyReader.readAndCreateAllOsoby(mainPath + "/osoby.unl");
    }
}
