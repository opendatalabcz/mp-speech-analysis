package web.chart;

import be.ceau.chart.color.Color;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.material.Material;


import java.util.ArrayList;
import java.util.List;

public class Colors {
    int counter = 0;
    static List<Color> darkColors = getDarkColors();
    static List<Color> lightColors = getLightColors();
    private Integer theme = 0; // 0 - dark, 1 - light


    public Colors() {
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();
        if(themeList.contains(Material.LIGHT))
            theme = 1;
        if(!themeList.contains(Material.LIGHT))
            theme = 0;
    }

    public static Color getChartLabelColor() {
        return getColorFromString("#BF360C");
    }

    public Color getGridLinesColor() {
        if(theme == 0)
            return getColorFromString("#616161");
        else
            return getColorFromString("#EEEEEE");
    }

    public Color getColor() {
        Color color = null;
        if(theme == 1 && lightColors.size() > 0)
            color = lightColors.get(counter);
        if(theme == 0 && darkColors.size() > 0)
            color = darkColors.get(counter);
        int minListSize = Math.min(lightColors.size(), darkColors.size());
        if(counter >= minListSize - 1)
            counter = 0;
        else
            counter++;

        if(color == null) return Color.RED;
        return color;
    }

    public static Color getColorFromString(String string) {
        if(string.length() != 7) throw new IllegalArgumentException();

        int r, g, b;
        String rStr = "0x" + string.substring(1,3);
        String gStr = "0x" + string.substring(3,5);
        String bStr = "0x" + string.substring(5,7);
        try {
            r = Integer.decode(rStr);
            g = Integer.decode(gStr);
            b = Integer.decode(bStr);
        } catch (Exception e) {
            return Color.RED;
        }

        return new Color(r,g,b);
    }

    //https://materialuicolors.co/ - verze 600
    private static List<Color> getDarkColors() {
        List<Color> list = new ArrayList<>();
        list.add(getColorFromString("#E53935")); //red
        list.add(getColorFromString("#43A047")); //green
        list.add(getColorFromString("#FDD835")); //yellow
        list.add(getColorFromString("#3949AB")); //indigo
        list.add(getColorFromString("#FB8C00")); //orange
        list.add(getColorFromString("#C0CA33")); //lime
        list.add(getColorFromString("#8E24AA")); //purple
        list.add(getColorFromString("#6D4C41")); //brown
        list.add(getColorFromString("#00ACC1")); //cyan
        list.add(getColorFromString("#FFB300")); //amber

        return list;
    }

    //https://materialuicolors.co/ - verze 400
    private static List<Color> getLightColors() {
        List<Color> list = new ArrayList<>();
        list.add(getColorFromString("#EF5350"));
        list.add(getColorFromString("#66BB6A"));
        list.add(getColorFromString("#FFEE58"));
        list.add(getColorFromString("#5C6BC0"));
        list.add(getColorFromString("#FFA726"));
        list.add(getColorFromString("#D4E157"));
        list.add(getColorFromString("#AB47BC"));
        list.add(getColorFromString("#8D6E63"));
        list.add(getColorFromString("#26C6DA"));
        list.add(getColorFromString("#FFCA28"));

        return list;
    }

    public Color getRed() {
        if(theme == 0)
            return getColorFromString("#E53935");
        else
            return getColorFromString("#EF5350");
    }

    public Color getGreen() {
        if(theme == 0)
            return getColorFromString("#43A047");
        else
            return getColorFromString("#66BB6A");
    }

    public Color getYellow() {
        if(theme == 0)
            return getColorFromString("#FDD835");
        else
            return getColorFromString("#FFEE58");
    }

}
