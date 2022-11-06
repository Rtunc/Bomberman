package uet.oop.bomberman.entities.menu;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;

public class GameOverScene extends SceneManager {

    public GameOverScene() {
        Text textPause = new Text();
        textPause.setText("GAME OVER");
        textPause.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
        textPause.setFill(Color.WHITE);
        textPause.setX(32*6 - textPause.getLayoutBounds().getWidth()/2);
        textPause.setY(50);
        Text textResume = new Text("NEW GAME");
        textResume.setFont(Font.font("Courier New", FontWeight.BOLD, 24));
        textResume.setFill(Color.WHITE);
        textResume.setX(32*6 - textResume.getLayoutBounds().getWidth()/2);
        textResume.setY(150);
        textResume.setOnMouseEntered(e -> {
            textResume.setFill(Color.SKYBLUE);
        });
        textResume.setOnMouseExited(e -> {
            textResume.setFill(Color.WHITE);
        });
        textResume.setOnMouseClicked(e -> {
            BombermanGame.switchState(SceneState.PLAYING);
            BombermanGame.restartGame(1);
        });
        Group textGroup = new Group(textPause, textResume);
        super.scene = new Scene(textGroup, Color.BLACK);
    }

    public GameOverScene addHandler() {
        return this;
    }
}
