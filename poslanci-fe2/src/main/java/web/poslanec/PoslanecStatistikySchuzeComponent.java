package web.poslanec;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.ProjevEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PoslanecStatistikySchuzeComponent extends VerticalLayout {
    private Accordion accordion = new Accordion();
    private PoslanecEntity poslanecEntity;

    public PoslanecStatistikySchuzeComponent(PoslanecEntity poslanecEntity) {
        this.poslanecEntity = poslanecEntity;
        initializeAccordion();
        add(accordion);
    }

    private void initializeAccordion() {
        Collection<ProjevEntity> projevy = poslanecEntity.getProjevsByIdPoslanec();
        int schuzeNum = 1;
        while(!projevy.isEmpty()) {
            List<ProjevEntity> currentList = new ArrayList<>();
            int finalSchuzeNum = schuzeNum;
            projevy.forEach(projev -> {
                if(projev.getBodByIdBod().getCisloSchuze() == finalSchuzeNum) {
                    currentList.add(projev);
                }
            });
            if(!currentList.isEmpty()) {
                processOneSchuze(currentList, schuzeNum);
            }

            projevy.removeIf(projev -> (projev.getBodByIdBod().getCisloSchuze() == finalSchuzeNum));
            schuzeNum++;
        }
        accordion.setWidthFull();
        accordion.close();
    }

    private VerticalLayout getProjevLayout(ProjevEntity projevEntity) {
        Label label = new Label("Projev: " + projevEntity.getText());
        return new VerticalLayout(label);
    }

    private void processOneSchuze(List<ProjevEntity> list, Integer cisloSchuze) {
        Integer pocetSlov = 0, pocetPosSlov = 0, pocetNegSlov = 0;
        VerticalLayout listLayout = new VerticalLayout();
        for(ProjevEntity projev : list){
            pocetSlov += projev.getPocetSlov();
            pocetPosSlov += projev.getProjevStatistikyByIdProjev().getPocetPosSlov();
            pocetNegSlov += projev.getProjevStatistikyByIdProjev().getPocetNegSlov();
            listLayout.add(getProjevLayout(projev));
        }

        String summary = "Schuze č." + cisloSchuze + ", početSlov: " + pocetSlov;
        accordion.add(summary,listLayout);
    }
}
