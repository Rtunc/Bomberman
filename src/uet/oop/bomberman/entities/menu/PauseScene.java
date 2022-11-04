package uet.oop.bomberman.entities.menu;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;

public class PauseScene extends SceneManager {

    public PauseScene() {
        Text textPause = new Text();
        textPause.setText("GAME PAUSED");
        textPause.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
        textPause.setFill(Color.WHITE);
        textPause.setX(32*6 - textPause.getLayoutBounds().getWidth()/2);
        textPause.setY(50);
        Text textResume = new Text("RESUME");
        textResume.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
        textResume.setFill(Color.WHITE);
        textResume.setX(32*6 - textResume.getLayoutBounds().getWidth()/2);
        textResume.setY(150);
        textResume.setOnMouseClicked(e -> {
            BombermanGame.switchState(SceneState.PLAYING);
        });
        textResume.setOnMouseEntered(e -> {
            textResume.setFill(Color.SKYBLUE);
        });
        textResume.setOnMouseExited(e -> {
            textResume.setFill(Color.WHITE);
        });
        Group textGroup = new Group(textPause, textResume);
        super.scene = new Scene(textGroup, Color.BLACK);
    }

    public PauseScene addHandler() {
        return this;
    }
}
