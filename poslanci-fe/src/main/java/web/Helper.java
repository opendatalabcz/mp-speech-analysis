package web;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Helper {
    public static HorizontalLayout getValueWithLabelComponent(String label, String value) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        Label labelLabel = new Label(label);

        //labelLabel.setWidth(labelWidth.toString());
        Label valueLabel = new Label(value);

        horizontalLayout.add(labelLabel, valueLabel);
        return horizontalLayout;
    }
}
