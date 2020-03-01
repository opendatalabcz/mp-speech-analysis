package web;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import web.party.PartyView;
import web.poslanec.PoslanecView;

public class SignpostTabsComponent extends VerticalLayout {
    Tabs tabs;
    Div div = new Div();

    public SignpostTabsComponent() {
        initializeTabs();
        add(tabs, div);
    }

    private void initializeTabs() {
        Tab tab0 = new Tab("1 - Poslanec");
        div.add(new PoslanecView());

        Tab tab1 = new Tab("2 - Strana");
        Tab tab2 = new Tab("3 - Celkové statistiky");
        Tab tab3 = new Tab("4 - Info");

        tabs = new Tabs(tab0, tab1, tab2, tab3);
        tabs.setWidthFull();
        tabs.addSelectedChangeListener(event -> {
           div.removeAll();
           String label = event.getSelectedTab().getLabel();
           if(label.startsWith("1")) div.add(new PoslanecView());
           if(label.startsWith("2")) div.add(new PartyView());
        });
    }
}
