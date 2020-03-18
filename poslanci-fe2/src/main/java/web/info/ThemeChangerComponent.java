package web.info;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.theme.material.Material;

import java.util.Map;

public class ThemeChangerComponent extends HorizontalLayout {
    enum Theme {
        DARK {
            public String toString() {
                return "Tmavé";
            }
        },
        LIGHT {
            public String toString() {
                return "Světlé";
            }
        }
    }

    Label label = new Label();
    Button button = new Button();
    Theme theme = Theme.DARK;

    public ThemeChangerComponent() {
        initialize();
        add(label, button);
    }

    private void initialize() {
        label.setText("Používané téma: ");

        button.setText(theme.toString());
        button.addClickListener(event -> {
            ThemeList themeList = UI.getCurrent().getElement().getThemeList();
            if(theme == Theme.LIGHT) {
                themeList.remove(Material.LIGHT);
                themeList.add(Material.DARK);
                theme = Theme.DARK;
            } else if(theme == Theme.DARK) {
                themeList.remove(Material.DARK);
                themeList.add(Material.LIGHT);
                theme = Theme.LIGHT;
            }
            button.setText(theme.toString());
        });
    }
}

