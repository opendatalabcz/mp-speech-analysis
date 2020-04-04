package creator;

import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.entity.PoslanecStatistikyZminkyEntity;
import poslanciDB.entity.ZminkaEntity;
import poslanciDB.service.PoslanecStatistikyZminkyEntityService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoslanecStatistikyZminkyCreator {
    PoslanecStatistikyZminkyEntityService poslanecStatistikyZminkyEntityService = new PoslanecStatistikyZminkyEntityService();
    Map<PoslanecEntity, Integer> map = new HashMap<>();

    public PoslanecStatistikyZminkyCreator() {
    }

    public void addZminka(ZminkaEntity zminkaEntity) {
        Integer value = map.get(zminkaEntity.getPoslanecByIdPoslanec());
        if (value == null)
            value = 0;
        value += zminkaEntity.getPocet();
        map.put(zminkaEntity.getPoslanecByIdPoslanec(), value);
    }

    /*public void saveAllZminkyToDB() {
        poslanecStatistikyZminkyEntityService.multiBegin();
        for(Map.Entry<PoslanecEntity, Integer> entry : map.entrySet()){
            PoslanecStatistikyZminkyEntity zminky = new PoslanecStatistikyZminkyEntity(entry.getValue(),
                    poslanecStatistikyEntity, entry.getKey());
            poslanecStatistikyZminkyEntityService.multiCreate(zminky);
        }
        poslanecStatistikyZminkyEntityService.multiCommit();
    }*/

    public List<PoslanecStatistikyZminkyEntity> getAllZminky(PoslanecStatistikyEntity poslanecStatistikyEntity) {
        List<PoslanecStatistikyZminkyEntity> list = new ArrayList<>();
        for(Map.Entry<PoslanecEntity, Integer> entry : map.entrySet()){
            PoslanecStatistikyZminkyEntity zminky = new PoslanecStatistikyZminkyEntity(entry.getValue(),
                    poslanecStatistikyEntity, entry.getKey());
            list.add(zminky);
        }
        return list;
    }
}
