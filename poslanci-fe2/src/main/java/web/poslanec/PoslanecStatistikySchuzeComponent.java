package web.poslanec;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.BodEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.ProjevEntity;
import poslanciDB.entity.ProjevStatistikyEntity;
import web.Helper;

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
        accordion.close();
        accordion.setWidthFull();
        accordion.setSizeFull();
        accordion.getElement().getStyle().set("background", "#616161");
    }

    private AccordionPanel getProjevLayout(ProjevEntity projevEntity) {
        BodEntity bod = projevEntity.getBodByIdBod();
        String summary = bod.getDatum().toString() + " - " +  Helper.getShortenString(bod.getText(), 75) + "\n" +
                ", počet slov celkem: " + projevEntity.getPocetSlov();

        Label content = new Label(projevEntity.getText());
        AccordionPanel panel = new AccordionPanel(summary, content);
        panel.getElement().setProperty("title", bod.getText());
        return panel;
    }

    private void processOneSchuze(List<ProjevEntity> list, Integer cisloSchuze) {
        Integer pocetSlov = 0, pocetPosSlov = 0, pocetNegSlov = 0;
        Accordion listAccordion = new Accordion();
        for(ProjevEntity projev : list){
            pocetSlov += projev.getPocetSlov();
            pocetPosSlov += projev.getProjevStatistikyByIdProjev().getPocetPosSlov();
            pocetNegSlov += projev.getProjevStatistikyByIdProjev().getPocetNegSlov();
            listAccordion.add(getProjevLayout(projev));
        }
        listAccordion.close();
        listAccordion.getElement().getStyle().set("background", "#757575");

        String summary = "Schůze č." + cisloSchuze + ", počet slov celkem: " + pocetSlov +
                ", počet positivních slov: " + pocetPosSlov + ", počet negativních slov: " + pocetNegSlov;
        accordion.add(summary, listAccordion);
    }



}
