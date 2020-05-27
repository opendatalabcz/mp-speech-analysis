package web.chart;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.palette.ColorPalette;
import web.Colors;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TagCloud {
    List<WordFrequency> wordFrequencies;
    WordCloud wordCloud;

    public TagCloud() {
        Dimension dimension = new Dimension(1000,1000);
        wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
        wordFrequencies = new ArrayList<>();
        wordCloud.setPadding(0);
        wordCloud.setBackground(new RectangleBackground(dimension));
        Colors colors = new Colors();
        wordCloud.setColorPalette(new ColorPalette(colors.getJavaColorsList()));
        wordCloud.setFontScalar(new LinearFontScalar(20, 110));
    }

    public void setTags(Map<String, Integer> map) {
        for(Map.Entry<String, Integer> entry : map.entrySet()) {
            WordFrequency wf = new WordFrequency(entry.getKey(), entry.getValue());
            wordFrequencies.add(wf);
        }
        wordCloud.build(wordFrequencies);
    }

    public BufferedImage getBufferedImage() {
        return wordCloud.getBufferedImage();
    }

}
