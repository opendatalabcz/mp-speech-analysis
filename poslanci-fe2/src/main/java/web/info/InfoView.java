package web.info;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.material.Material;

public class InfoView extends VerticalLayout {


    public InfoView() {
        add(new ThemeChangerComponent());
    }

    private void initialize() {

    }
}
