package web;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import web.obdobiCompare.ObdobiCompareView;
import web.osobyCompare.OsobyCompareView;
import web.info.InfoView;
import web.osoba.OsobaView;
import web.stranyCompare.StranyCompareView;
import web.poslanec.PoslanecView;

public class SignpostTabsComponent extends VerticalLayout {
    Tabs tabs;
    Div div = new Div();

    public SignpostTabsComponent() {
        initializeTabs();
        add(tabs, div);
    }

    private void initializeTabs() {
        div.add(new PoslanecView());
        Tab tab1 = new Tab("1 - Poslanec");


        Tab tab2 = new Tab("2 - Osoba");
        //Tab tab3 = new Tab("3 - Strana");
        Tab tab3 = new Tab("3 - Srovnání osob");
        Tab tab4 = new Tab("4 - Srovnání stran");
        Tab tab5 = new Tab("5 - Srovnání období");
        Tab tab6 = new Tab("6 - Info");


        tabs = new Tabs(tab1, tab2, tab3, tab4, tab5, tab6);
        tabs.setWidthFull();
        tabs.setFlexGrowForEnclosedTabs(1);
        tabs.addSelectedChangeListener(event -> {
           div.removeAll();
           String label = tabs.getSelectedTab().getLabel();
           if(label.startsWith("1")) div.add(new PoslanecView());
           if(label.startsWith("2")) div.add(new OsobaView());
           if(label.startsWith("3")) div.add(new OsobyCompareView());
           if(label.startsWith("4")) div.add(new StranyCompareView());
           if(label.startsWith("5")) div.add(new ObdobiCompareView());
           if(label.startsWith("6")) div.add(new InfoView());
        });
    }
}
