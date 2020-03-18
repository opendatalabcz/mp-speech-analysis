import poslanciDB.service.OrganyEntityService;
import web.chart.TagCloud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppTest {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("Karel", 10);
        map.put("Ondra", 20);
        map.put("Vasek", 19);
        map.put("Petr", 3);
        map.put("Aneta", 13);
        map.put("Asus", 10);
        map.put("Brno", 1);

        TagCloud tagCloud = new TagCloud();
        tagCloud.setTags(map);
        tagCloud.savePicturePNG();
    }
}
