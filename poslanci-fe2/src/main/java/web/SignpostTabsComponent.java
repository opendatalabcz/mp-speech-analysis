package web;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import web.obdobiCompare.ObdobiCompareView;
import web.osobyCompare.OsobyCompareView;
import web.info.InfoView;
import web.osoba.OsobaView;
import web.strana.StranaView;
import web.stranyCompare.StranyCompareView;
import web.poslanec.PoslanecView;

public class SignpostTabsComponent extends VerticalLayout {
    private Tabs tabs;
    private Div div = new Div();
    private PoslanecView poslanecView;
    private OsobaView osobaView;
    private StranaView stranaView;
    private OsobyCompareView osobyCompareView;
    private StranyCompareView stranyCompareView;
    private ObdobiCompareView obdobiCompareView;
    private InfoView infoView;

    public SignpostTabsComponent() {
        initializeTabs();
        add(tabs, div);
    }

    private void initializeTabs() {
        Tab tab1 = new Tab("1 - Poslanec");
        poslanecView = new PoslanecView();
        div.add(poslanecView);

        Tab tab2 = new Tab("2 - Osoba");
        Tab tab3 = new Tab("3 - Strana");
        Tab tab4 = new Tab("4 - Srovnání osob");
        Tab tab5 = new Tab("5 - Srovnání stran");
        Tab tab6 = new Tab("6 - Srovnání období");
        Tab tab7 = new Tab("7 - Info");


        tabs = new Tabs(tab1, tab2, tab3, tab4, tab5, tab6, tab7);
        tabs.setWidthFull();
        tabs.setFlexGrowForEnclosedTabs(1);
        tabs.addSelectedChangeListener(event -> {
            div.removeAll();
            String label = tabs.getSelectedTab().getLabel();
            if(label.startsWith("1")) {
                if(poslanecView == null) poslanecView = new PoslanecView();
                div.add(poslanecView);
            }
            if(label.startsWith("2")) {
                if(osobaView == null) osobaView = new OsobaView();
                div.add(osobaView);
            }
            if(label.startsWith("3")) {
                if(stranaView == null) stranaView = new StranaView();
                div.add(stranaView);
            }
            if(label.startsWith("4")) {
                if(osobyCompareView == null) osobyCompareView = new OsobyCompareView();
                div.add(osobyCompareView);
            }
            if(label.startsWith("5")) {
                if(stranyCompareView == null) stranyCompareView = new StranyCompareView();
                div.add(stranyCompareView);
            }
            if(label.startsWith("6")) {
                if(obdobiCompareView == null) obdobiCompareView = new ObdobiCompareView();
                div.add(obdobiCompareView);
            }
            if(label.startsWith("7")) {
                if(infoView == null) infoView = new InfoView(this::refresh);
                div.add(infoView);
            }
        });
    }

    private void refresh() {
        poslanecView = new PoslanecView();
        osobaView = new OsobaView();
        stranaView = new StranaView();
        osobyCompareView = new OsobyCompareView();
        stranyCompareView = new StranyCompareView();
        obdobiCompareView = new ObdobiCompareView();
    }
}

/*package web;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import web.obdobiCompare.ObdobiCompareView;
import web.osobyCompare.OsobyCompareView;
import web.info.InfoView;
import web.osoba.OsobaView;
import web.strana.StranaView;
import web.stranyCompare.StranyCompareView;
import web.poslanec.PoslanecView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SignpostTabsComponent extends VerticalLayout {

    public SignpostTabsComponent() {
        initializeTabs();
        //add(tabs, divPoslanec);
    }

    private void initializeTabs() {
        Tab tab1 = new Tab("1 - Poslanec");
        Div div1Poslanec = new Div(new PoslanecView());

        Tab tab2 = new Tab("2 - Osoba");
        Div div2Osoba = new Div(new OsobaView());
        div2Osoba.setVisible(false);

        Tab tab3 = new Tab("3 - Strana");
        Div div3Strana = new Div(new StranaView());
        div3Strana.setVisible(false);

        Tab tab4 = new Tab("4 - Srovnání osob");
        Div div4SrovnaniOsob = new Div(new OsobyCompareView());
        div4SrovnaniOsob.setVisible(false);

        Tab tab5 = new Tab("5 - Srovnání stran");
        Div div5SrovnaniStran = new Div(new StranyCompareView());
        div5SrovnaniStran.setVisible(false);

        Tab tab6 = new Tab("6 - Srovnání období");
        Div div6SrovnaniObdobi = new Div(new ObdobiCompareView());
        div6SrovnaniObdobi.setVisible(false);

        Tab tab7 = new Tab("7 - Info");
        Div div7Info = new Div(new InfoView());
        div7Info.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(tab1, div1Poslanec);
        tabsToPages.put(tab2, div2Osoba);
        tabsToPages.put(tab3, div3Strana);
        tabsToPages.put(tab4, div4SrovnaniOsob);
        tabsToPages.put(tab5, div5SrovnaniStran);
        tabsToPages.put(tab6, div6SrovnaniObdobi);
        tabsToPages.put(tab7, div7Info);

        Tabs tabs = new Tabs(tab1, tab2, tab3, tab4, tab5, tab6, tab7);
        Div views = new Div(div1Poslanec, div2Osoba, div3Strana, div4SrovnaniOsob, div5SrovnaniStran, div6SrovnaniObdobi, div7Info);
        Set<Component> viewsShown = Stream.of(div1Poslanec)
                .collect(Collectors.toSet());

        tabs.setWidthFull();
        tabs.setFlexGrowForEnclosedTabs(1);
        tabs.addSelectedChangeListener(event -> {
            viewsShown.forEach(page -> page.setVisible(false));
            viewsShown.clear();
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
            viewsShown.add(selectedPage);
        });
        add(tabs, views);
    }
}

 */
