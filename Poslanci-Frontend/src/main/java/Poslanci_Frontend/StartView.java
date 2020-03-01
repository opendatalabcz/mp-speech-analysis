package Poslanci_Frontend;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class StartView extends VerticalLayout {
    private VerticalLayout main;

    public StartView(VerticalLayout main) {
        this.main = main;
        add(getText(), getSeasonLayout());
    }

    private HorizontalLayout getSeasonLayout() {
        Button confirmSeasonButton = new Button("OK", event -> {
            main.removeAll();
            main.add(new Label("Trololo"));
        });
        ComboBox<String> comboBox = new ComboBox<>("test", "Test0", "Test1", "Test2");
        return new HorizontalLayout(comboBox, confirmSeasonButton);
    }

    private VerticalLayout getText() {
        Label label = new Label("TEXT0");
        Label text = new Label("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
        return new VerticalLayout(label, text);
    }


}
