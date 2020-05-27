package creator;

import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.SlovoEntity;
import poslanciDB.entity.ZminkaEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static helper.StringHelper.removePostfix;

public class ZminkaCreator {
    Collection<PoslanecEntity> poslanecEntityList;
    List<ZminkaEntity> zminkaEntities = new ArrayList<>();


    public ZminkaCreator(Collection<PoslanecEntity> poslanecEntityList) {
        this.poslanecEntityList = poslanecEntityList;
    }

    public void processSlovoToZminka(SlovoEntity slovoEntity) {
        String word = slovoEntity.getSlovo();
        //jmeno zacina vzdy velkym pismenem
        if(!checkUpperCaseFirstLetter(word)) return;
        //ziska list poslancu, kteri byli ve slove zmineni (muze jich byt vice se stejnym prijmenim)
        List<PoslanecEntity> list = getMentioned(word);
        for(PoslanecEntity poslanecEntity : list) {
            ZminkaEntity zminkaEntity = new ZminkaEntity(null, slovoEntity.getPocetVyskytu(),
                    slovoEntity.getProjevByIdProjev(), poslanecEntity);
            zminkaEntities.add(zminkaEntity);
        }
    }

    private boolean checkUpperCaseFirstLetter(String word) {
        if(word == null || word.isEmpty()) return false;
        if(!Character.isUpperCase(word.codePointAt(0))) return false;
        return true;
    }

    private List<PoslanecEntity> getMentioned(String surname) {
        List<PoslanecEntity> list = new ArrayList<>();
        for(PoslanecEntity poslanecEntity : poslanecEntityList) {
            if(removePostfix(surname).equals(poslanecEntity.getOsobyByIdOsoba().getPrijmeni()))
                list.add(poslanecEntity);
        }
        return list;
    }

    public List<ZminkaEntity> getZminkaList() {
        return zminkaEntities;
    }
}
