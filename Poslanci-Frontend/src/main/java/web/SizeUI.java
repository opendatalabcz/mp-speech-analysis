package web;

import com.syndybat.chartjs.ChartJs;
import com.vaadin.flow.component.html.Div;

//pomocna trida pro praci s velikostmi
//reaguje na zmenu velikosti prohlizece a umi zabalit grafy di div elementu, ktere maji pomery relativni a ne absolutni,
//takze vizualne jsou stejne velke vzdy
public class SizeUI {
    public static Integer width = -1;

    private SizeUI() {}

    private static String getWidthString(Integer divident, Integer divisor) {
        return getWidthFromFraction(divident, divisor) + "px";
    }

    private static Integer getWidthFromFraction(Integer divident, Integer divisor) {
        if(width > 0)
            return (width * divident) / divisor;
        else
            return 500;
    }

    public static Div wrapToBigDiv(ChartJs barChartJs)
    {
        if(barChartJs == null) return null;
        Div div = new Div();
        div.add(barChartJs);
        div.setWidth(getWidthString(7,10)); //1200px
        return div;
    }

    public static Div wrapToMediumDiv(ChartJs barChartJs)
    {
        if(barChartJs == null) return null;
        Div div = new Div();
        div.add(barChartJs);
        div.setWidth(getWidthString(7,20)); //700px
        return div;
    }

    public static Div wrapToSmallDiv(ChartJs barChartJs)
    {
        if(barChartJs == null) return null;
        Div div = new Div();
        div.add(barChartJs);
        div.setWidth(getWidthString(1,4)); //600px
        return div;
    }
}
