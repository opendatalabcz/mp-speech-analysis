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
            Notification.show("Window width=" + event.getWidth()
                    + ", height=" + event.getHeight());
            SizeUI.width = event.getWidth();
        });
        add(new SignpostTabsComponent());
    }

    @ClientCallable
    public void greet(String name) {
        System.out.println("Hi, " + name);
    }

    @ClientCallable
    private void javaFunction(int width){
        System.out.println("Width: " + width);
        SizeUI.width = width;
    }

    private Div wrapToDiv(ChartJs barChartJs)
    {
        Div div = new Div();
        div.add(barChartJs);
        div.setWidth("1200px");
        div.setHeight("10000px");
        return div;
    }

    private ChartJs getBarChart(){
        BarDataset dataset = new BarDataset()
                .setLabel("sample chart")
                .setData(65, 59, 80, 81, 56, 55, 40)
                .addBackgroundColor(Color.RED)
                .setBorderWidth(2);

        BarData data = new BarData()
                .addLabels("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
                .addDataset(dataset);

        JavaScriptFunction label = new JavaScriptFunction(
                "\"function(chart) {console.log('test legend');}\""
        );

        BarOptions barOptions = new BarOptions()
                .setResponsive(true)
                .setTitle(new Title().setText("test"))
                .setLegend(new Legend().setDisplay(true)
                        .setOnClick(label));

        return new ChartJs(new BarChart(data,barOptions).toJson());
    }
}
