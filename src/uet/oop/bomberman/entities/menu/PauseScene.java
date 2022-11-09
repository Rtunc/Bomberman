package uet.oop.bomberman.entities.menu;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class PauseScene extends SceneManager {

    public PauseScene() {
        Text textPause = new Text();
        textPause.setText("GAME PAUSED");
        textPause.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textPause.setFill(Color.WHITE);
        textPause.setX(Sprite.SCALED_SIZE *6 - textPause.getLayoutBounds().getWidth()/2);
        textPause.setY(50 * Sprite.SCALED);

        Text textResume = new Text("RESUME");
        textResume.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textResume.setFill(Color.WHITE);
        textResume.setX(Sprite.SCALED_SIZE *6 - textResume.getLayoutBounds().getWidth()/2);
        textResume.setY(150 * Sprite.SCALED);
        textResume.setOnMouseClicked(e -> {
            BombermanGame.switchState(SceneState.PLAYING);
        });
        textResume.setOnMouseEntered(e -> {
            textResume.setFill(Color.SKYBLUE);
        });
        textResume.setOnMouseExited(e -> {
            textResume.setFill(Color.WHITE);
        });

        Text textHome = new Text("HOME");
        textHome.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textHome.setFill(Color.WHITE);
        textHome.setX(Sprite.SCALED_SIZE * 6 - textHome.getLayoutBounds().getWidth()/2);
        textHome.setY(200 * Sprite.SCALED);
        textHome.setOnMouseEntered(e -> {
            textHome.setFill(Color.SKYBLUE);
        });
        textHome.setOnMouseExited(e -> {
            textHome.setFill(Color.WHITE);
        });
        textHome.setOnMouseClicked(e -> {
            BombermanGame.switchState(SceneState.MENU);
        });

        Group textGroup = new Group(textPause, textResume, textHome);
        super.scene = new Scene(textGroup, Color.BLACK);
    }

    public PauseScene addHandler() {
        return this;
    }
}
