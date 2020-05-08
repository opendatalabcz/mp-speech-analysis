package web.poslanec;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.BodEntity;
import poslanciDB.entity.PoslanecEntity;
import poslanciDB.entity.ProjevEntity;
import web.Colors;
import web.Helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PoslanecProjevyComponent extends VerticalLayout {
    private Accordion accordion = new Accordion();
    private PoslanecEntity poslanecEntity;

    public PoslanecProjevyComponent(PoslanecEntity poslanecEntity) {
        this.poslanecEntity = poslanecEntity;
        Label label = new Label("Projevy poslance:");
        label.getElement().getStyle().set("color", Colors.getHighlightColorString()).set("text-decoration", "underline");
        add(label);
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
                ", počet slov: " + projevEntity.getPocetSlov() +
                ", sentiment: " + Helper.getRoundDouble(projevEntity.getSentiment());

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
            pocetPosSlov += projev.getPocetPosSlov();
            pocetNegSlov += projev.getPocetNegSlov();
            listAccordion.add(getProjevLayout(projev));
        }
        listAccordion.close();
        listAccordion.getElement().getStyle().set("background", "#757575");

        Double sentiment = Helper.countSentiment(pocetPosSlov, pocetNegSlov);

        String summary = "Schůze č." + cisloSchuze + ", počet slov celkem: " + pocetSlov +
                ", sentiment: " + Helper.getRoundDouble(sentiment);
                //", počet positivních slov: " + pocetPosSlov + ", počet negativních slov: " + pocetNegSlov;
        accordion.add(summary, listAccordion);
    }



}
