package test.web;

import be.ceau.chart.color.Color;
import org.junit.Test;

import static web.Colors.getColorFromString;

public class ColorsTest {
    @Test
    public void getColorFromStringTest() {
        Color wrongColor = getColorFromString("#GG0000");

        Color color0 = getColorFromString("#E53935");
        Color color1 = getColorFromString("#039BE5");
        Color color2 = getColorFromString("#FFB300");
        Color color4 = getColorFromString("#43A047");
    }

}
