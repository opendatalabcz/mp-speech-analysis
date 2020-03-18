package web.poslanec;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import poslanciDB.entity.PoslanecStatistikyEntity;
import poslanciDB.entity.TopSlovaEntity;
import web.chart.TagCloud;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class PoslanecTopSlovaComponent extends HorizontalLayout {
    PoslanecStatistikyEntity poslanecStatistikyEntity;
    Image image;
    Grid<TopSlovaEntity> grid = new Grid<>(TopSlovaEntity.class);


    public PoslanecTopSlovaComponent(PoslanecStatistikyEntity poslanecStatistikyEntity) {
        this.poslanecStatistikyEntity = poslanecStatistikyEntity;
        initialize();
        add(image, grid);
    }

    private void initialize() {
        grid.addThemeVariants(GridVariant.MATERIAL_COLUMN_DIVIDERS);
        grid.setWidth("500px");
        grid.setHeight("600px");
        grid.setItems(poslanecStatistikyEntity.getTopSlovaByIdPoslanec());
        grid.removeAllColumns();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addColumn(this::getFormattedWord).setHeader("Slovo");
        grid.addColumn(TopSlovaEntity::getPocetVyskytu).setHeader("Počet výskytů");
        grid.addColumn(this::getRelativeWordUsage).setHeader("Podíl z celé slovní zásoby poslance");

        TagCloud tagCloud = new TagCloud();
        tagCloud.setTags(getWordsMap());
        BufferedImage buffImage = tagCloud.getBufferedImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(buffImage, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();
        StreamResource sr = new StreamResource("image.png", () -> new ByteArrayInputStream(bytes));
        image = new Image(sr, "WordCloud nebylo možné načíst");
        image.setHeight("600px");
    }

    private String getRelativeWordUsage(TopSlovaEntity topSlovaEntity) {
        Integer pocetSlov = poslanecStatistikyEntity.getPocetSlov();
        if(pocetSlov < 1) return "";
        Integer pocetVyskytu = topSlovaEntity.getPocetVyskytu();
        double relativniPocetVyskytu = ((double)pocetVyskytu/pocetSlov) * 100;
        DecimalFormat df = new DecimalFormat("#.###");
        return df.format(relativniPocetVyskytu) + "%";
    }

    private String getFormattedWord(TopSlovaEntity topSlovaEntity) {
        return formatWord(topSlovaEntity.getSlovo());
    }

    private Map<String, Integer> getWordsMap() {
        Map<String, Integer> map = new HashMap<>();
        for(TopSlovaEntity tse : poslanecStatistikyEntity.getTopSlovaByIdPoslanec()) {
            map.put(formatWord(tse.getSlovo()), tse.getPocetVyskytu());
        }
        return map;
    }

    private String formatWord(String word) {
        String[] parts0 = word.split("-");
        String[] parts1 = parts0[0].split("_");
        String[] parts2 = parts1[0].split("`");
        return parts2[0];
    }
}
