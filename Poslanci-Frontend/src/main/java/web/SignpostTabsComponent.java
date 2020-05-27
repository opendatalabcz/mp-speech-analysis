package web;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import poslanciDB.entity.OsobyEntity;
import poslanciDB.entity.PoslanecEntity;
import web.obdobiCompare.ObdobiCompareView;
import web.osobyCompare.OsobyCompareView;
import web.info.InfoView;
import web.osoba.OsobaView;
import web.strana.StranaView;
import web.stranyCompare.StranyCompareView;
import web.poslanec.PoslanecView;

//rozcestnik s kartami (obrazovkami)
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
        //cisla jsou dulezita pro navazani dalsich funkci, neni zcela idealni reseni
        Tab tab1 = new Tab("1 - Poslanec");
        poslanecView = new PoslanecView(this::switchToPoslanec);
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
                if(poslanecView == null) poslanecView = new PoslanecView(this::switchToPoslanec);
                div.add(poslanecView);
            }
            if(label.startsWith("2")) {
                if(osobaView == null) osobaView = new OsobaView(this::switchToPoslanec);
                div.add(osobaView);
            }
            if(label.startsWith("3")) {
                if(stranaView == null) stranaView = new StranaView(this::switchToPoslanec);
                div.add(stranaView);
            }
            if(label.startsWith("4")) {
                if(osobyCompareView == null) osobyCompareView = new OsobyCompareView(this::switchToOsoba);
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
        poslanecView = new PoslanecView(this::switchToPoslanec);
        osobaView = new OsobaView(this::switchToPoslanec);
        stranaView = new StranaView(this::switchToPoslanec);
        osobyCompareView = new OsobyCompareView(this::switchToOsoba);
        stranyCompareView = new StranyCompareView();
        obdobiCompareView = new ObdobiCompareView();
    }

    private void switchToPoslanec(PoslanecEntity poslanecEntity) {
        poslanecView = new PoslanecView(this::switchToPoslanec, poslanecEntity);
        if(tabs.getSelectedIndex() == 0) tabs.setSelectedIndex(1);
        tabs.setSelectedIndex(0);
    }

    private void switchToOsoba(OsobyEntity osobyEntity) {
        osobaView = new OsobaView(this::switchToPoslanec, osobyEntity);
        if(tabs.getSelectedIndex() == 1) tabs.setSelectedIndex(0);
        tabs.setSelectedIndex(1);
    }
}