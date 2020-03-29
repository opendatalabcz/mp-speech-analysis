package web.osobyCompare;

import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.Colors;
import web.chart.OsobaData;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OsobyStatsBySeasonsComponent extends VerticalLayout {
    Set<OsobyEntity> set = null;
    private Accordion accordion = new Accordion();

    public OsobyStatsBySeasonsComponent(Set<OsobyEntity> set) {
        this.set = set;
        initialize();
    }

    private void initialize() {
        removeAll();
        accordion = new Accordion();
        add(accordion);
        if (set != null) {
            Integer beginPeriod = 1;
            Integer endPeriod = OsobaData.getEndSessonNumber(set);

            for(int i = beginPeriod; i <= endPeriod; i++) {
                List<PoslanecEntity> list = new ArrayList<>();
                for(OsobyEntity osobyEntity : set) {
                    PoslanecEntity poslanecEntity = OsobaData.getPoslanecByPeriodNumber(osobyEntity, i);
                    if(poslanecEntity != null) {
                        list.add(poslanecEntity);
                    }
                }
                if(!list.isEmpty()) {
                    Label label = new Label("OBDOBÍ: " + list.get(0).getOrganyByIdObdobi());
                    label.getElement().getStyle().set("color", Colors.getHighlightColorString()).set("font-weight", "bold");
                    accordion.add(new AccordionPanel(label, new PoslanciCompareStatsComponent(list)));
                }
            }
        }
        accordion.close();
        accordion.setWidthFull();
        accordion.setSizeFull();
    }

    public void refresh(Set<OsobyEntity> set) {
        this.set = set;
        initialize();
    }
}
