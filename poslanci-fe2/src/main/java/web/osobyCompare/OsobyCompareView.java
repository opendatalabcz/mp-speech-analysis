package web.osobyCompare;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import poslanciDB.entity.OsobyEntity;

import java.util.Set;

public class OsobyCompareView extends HorizontalLayout {
    OsobyMainStatsComponent osobyMainStatsComponent = new OsobyMainStatsComponent(null);
    OsobyMainWordStatsComponent osobyMainWordStatsComponent = new OsobyMainWordStatsComponent(null);
    OsobySetComponent osobySetComponent = new OsobySetComponent(this::getSet);

    public OsobyCompareView() {

        System.out.println("Width: " + UI.getCurrent().getSession().getBrowser().getScreenWidth());
        add(osobySetComponent, new VerticalLayout(osobyMainStatsComponent, osobyMainWordStatsComponent));
    }

    private void getSet(Set<OsobyEntity> set) {
        osobyMainStatsComponent.refresh(set);
        osobyMainWordStatsComponent.refresh(set);
    }
}
