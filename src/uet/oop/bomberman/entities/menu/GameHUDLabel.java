package uet.oop.bomberman.entities.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class GameHUDLabel extends Label {
    /**
     * Creates Label with supplied text.
     *
     * @param text null text is treated as the empty string
     */
    public GameHUDLabel(String text) {
        setPrefHeight(50);
        setPrefWidth(13 * 20);
        BackgroundFill fill = new BackgroundFill(Color.BLACK, null, null);
        setBackground(new Background(fill));
        setAlignment(Pos.CENTER_RIGHT);
        setPadding(new Insets(10, 10, 10, 10));
        setText("Label");
    }
}
