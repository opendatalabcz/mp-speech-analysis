package web.chart;

import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.html.Div;

public class Helper {
    public static Div wrapToDiv(ChartJs barChartJs)
    {
        Div div = new Div();
        div.add(barChartJs);
        div.setWidth("1200px");
        return div;
    }

    public static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
