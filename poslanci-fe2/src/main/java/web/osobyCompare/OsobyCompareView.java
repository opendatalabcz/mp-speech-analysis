package web.osobyCompare;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;

import java.util.Set;
import java.util.function.Consumer;

public class OsobyCompareView extends VerticalLayout {
    OsobyMainStatsComponent osobyMainStatsComponent = new OsobyMainStatsComponent(null);
    OsobyMainWordStatsComponent osobyMainWordStatsComponent = new OsobyMainWordStatsComponent(null);
    OsobyStatsBySeasonsComponent osobyStatsBySeasonsComponent = new OsobyStatsBySeasonsComponent(null);
    OsobySetComponent osobySetComponent;


    public OsobyCompareView(Consumer<OsobyEntity> switchToOsoba) {
        osobySetComponent = new OsobySetComponent(this::getSet, switchToOsoba);
        add(new HorizontalLayout(osobySetComponent, new VerticalLayout(osobyMainStatsComponent, osobyMainWordStatsComponent)),
                osobyStatsBySeasonsComponent);
    }

    private void getSet(Set<OsobyEntity> set) {
        osobyMainStatsComponent.refresh(set);
        osobyMainWordStatsComponent.refresh(set);
        osobyStatsBySeasonsComponent.refresh(set);
    }
}
