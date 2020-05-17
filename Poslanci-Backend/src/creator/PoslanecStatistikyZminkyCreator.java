package creator;

import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.entity.PoslanecStatistikyZminkyEntity;
import poslanciDB.entity.ZminkaEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoslanecStatistikyZminkyCreator {
    Map<PoslanecEntity, Integer> map = new HashMap<>();

    public PoslanecStatistikyZminkyCreator() {
    }

    public void addZminka(ZminkaEntity zminkaEntity) {
        //prida zminku do mapy zminek, jestli tam uz je, tak je zvysi pocet jejich vyskytu
        Integer value = map.get(zminkaEntity.getPoslanecByIdPoslanec());
        if (value == null)
            value = 0;
        value += zminkaEntity.getPocet();
        map.put(zminkaEntity.getPoslanecByIdPoslanec(), value);
    }

    public List<PoslanecStatistikyZminkyEntity> getAllZminky(PoslanecStatistikyEntity poslanecStatistikyEntity) {
        //mapu zminek prevede na seznam zminek ve formatu instanci entitni tridy
        List<PoslanecStatistikyZminkyEntity> list = new ArrayList<>();
        for(Map.Entry<PoslanecEntity, Integer> entry : map.entrySet()){
            PoslanecStatistikyZminkyEntity zminky = new PoslanecStatistikyZminkyEntity(entry.getValue(),
                    poslanecStatistikyEntity, entry.getKey());
            list.add(zminky);
        }
        return list;
    }
}
