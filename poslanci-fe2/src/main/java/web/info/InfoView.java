package web.info;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.material.Material;

public class InfoView extends VerticalLayout {
    public InfoView() {
        Button toggleButton = new Button("Toggle dark theme", click -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList(); //

            if (themeList.contains(Material.LIGHT)) { //
                themeList.remove(Material.LIGHT);
                themeList.add(Material.DARK);
            } else {
                themeList.add(Material.LIGHT);
                themeList.remove(Material.DARK);
            }
        });

        add(toggleButton);
    }
}
