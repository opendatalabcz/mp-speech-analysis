package web;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import poslanciDB.entity.PoslanecEntity;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collection;

public class Helper {
    public static HorizontalLayout getValueWithLabelComponent(String label, String value) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Label labelLabel = new Label(label);
        Label valueLabel = new Label(value);
        horizontalLayout.add(labelLabel, valueLabel);
        return horizontalLayout;
    }

    public static String getShortenString(String s, int limit) {
        if(limit < 0) limit = 10;
        if(limit > s.length())
            return s;
        else
            s = s.substring(0, limit) + "...";
        return s;
    }

    public static String getRoundDouble(Double num) {
        DecimalFormat df3 = new DecimalFormat("#.###");
        return df3.format(num);
    }

    public static Integer getAveragePocetSlov(Collection<PoslanecEntity> poslanecEntities) {
        Integer count = 0;
        for(PoslanecEntity poslanecEntity : poslanecEntities) {
            if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                count += poslanecEntity.getPoslanecStatistikyByIdPoslanec().getPocetSlov();
            }
        }
        if(poslanecEntities.size() > 0) {
            return count / poslanecEntities.size();
        }
        return 0;
    }

    public static String getAverageSentimentRound(Collection<PoslanecEntity> poslanecEntities) {
        return getRoundDouble(getAverageSentiment(poslanecEntities));
    }

    public static Double getAverageSentiment(Collection<PoslanecEntity> poslanecEntities) {
        Double sentiment = 0.0;
        for(PoslanecEntity poslanecEntity : poslanecEntities) {
            if(poslanecEntity.getPoslanecStatistikyByIdPoslanec() != null) {
                sentiment += poslanecEntity.getPoslanecStatistikyByIdPoslanec().getSentiment();
            }
        }
        if(poslanecEntities.size() > 0) {
            return sentiment / poslanecEntities.size();
        }
        return 0.0;
    }
}
