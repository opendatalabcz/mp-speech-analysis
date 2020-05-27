package web;

import be.ceau.chart.BarChart;
import be.ceau.chart.color.Color;
import be.ceau.chart.data.BarData;
import be.ceau.chart.dataset.BarDataset;
import be.ceau.chart.javascript.JavaScriptFunction;
import be.ceau.chart.options.BarOptions;
import be.ceau.chart.options.Legend;
import be.ceau.chart.options.Title;
import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.material.Material;

import java.util.ArrayList;
import java.util.List;


/**
 * The main view contains a button and a click listener.
 */
@Route
@JavaScript("frontend://script.js")
@HtmlImport("frontend://styles/shared-styles.html")
@Theme(value = Material.class, variant = Material.DARK)
@PWA(name = "My web.Application", shortName = "My web.Application")
public class MainView extends VerticalLayout {

    public MainView() {
        getElement().executeJavaScript("javascriptFunction($0)", getElement());
        UI.getCurrent().getPage().addBrowserWindowResizeListener(event ->
        {
            SizeUI.width = event.getWidth();
        });
        add(new SignpostTabsComponent());
    }

    @ClientCallable
    private void javaFunction(int width){
        System.out.println("Width: " + width);
        SizeUI.width = width;
    }
}
