import poslanciDB.entity.OsobyEntity;
import poslanciDB.service.OsobyEntityService;

public class AppTest {
    public static void main(String[] args) {
        OsobyEntityService osobyEntityService = new OsobyEntityService();
        OsobyEntity osobyEntity = osobyEntityService.find(4);
        System.out.println(osobyEntity.getPrijmeni());
    }
}
