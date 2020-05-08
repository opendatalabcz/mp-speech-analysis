package creator;

import helper.StringHelper;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.SlovoEntity;
import poslanciDB.entity.ZminkaEntity;
import poslanciDB.service.ZminkaEntityService;

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
        if(!basicCheck(word)) return;
        List<PoslanecEntity> list = getMentioned(word);
        for(PoslanecEntity poslanecEntity : list) {
            ZminkaEntity zminkaEntity = new ZminkaEntity(null, slovoEntity.getPocetVyskytu(),
                    slovoEntity.getProjevByIdProjev(), poslanecEntity);
            zminkaEntities.add(zminkaEntity);
        }
    }

    private boolean basicCheck(String word) {
        if(word == null || word.isEmpty()) return false;
        if(!Character.isUpperCase(word.codePointAt(0))) return false;
        return true;
    }

    private List<PoslanecEntity> getMentioned(String surname) {
        List<PoslanecEntity> list = new ArrayList<>();
        for(PoslanecEntity poslanecEntity : poslanecEntityList) {
            if(removePostfix(surname).equals(poslanecEntity.getOsobyByIdOsoba().getPrijmeni()))
                list.add(poslanecEntity);
            /*if(surname.startsWith(poslanecEntity.getOsobyByIdOsoba().getPrijmeni())) //todo opravit tahle blbost
                list.add(poslanecEntity);*/
        }
        return list;
    }

    public List<ZminkaEntity> getZminkaList() {
        return zminkaEntities;
    }
}
