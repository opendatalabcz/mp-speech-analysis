package Poslanci_Frontend;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.service.PoslanecEntityService;

public class PoslanecView extends VerticalLayout {
    PoslanecEntityService poslanecEntityService = new PoslanecEntityService();
    PoslanecEntity poslanecEntity;

    public PoslanecView(int id) {
        System.out.println("TADY0");
        //getPoslanecEntity(id);
        System.out.println("TADY1");
        //add(new Label(poslanecEntity.getOsobyByIdOsoba().getPrijmeni()));
        System.out.println("TADY2");
    }

    private void getPoslanecEntity(int id) {
        //poslanecEntity = poslanecEntityService.find(id);
    }
}
