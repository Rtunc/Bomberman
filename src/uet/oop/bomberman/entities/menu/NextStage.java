package uet.oop.bomberman.entities.menu;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class NextStage extends SceneManager {

    public NextStage() {
        Text textPause = new Text();
        textPause.setText("STAGE " + BombermanGame.currentLevel + " DONE");
        textPause.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textPause.setFill(Color.WHITE);
        textPause.setX(Sprite.SCALED_SIZE * 6 - textPause.getLayoutBounds().getWidth()/2);
        textPause.setY(50 * Sprite.SCALED);
        Text textResume = new Text("NEXT STAGE");
        textResume.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textResume.setFill(Color.WHITE);
        textResume.setX(Sprite.SCALED_SIZE * 6 - textResume.getLayoutBounds().getWidth()/2);
        textResume.setY(150 * Sprite.SCALED);
        textResume.setOnMouseEntered(e -> {
            textResume.setFill(Color.SKYBLUE);
        });
        textResume.setOnMouseExited(e -> {
            textResume.setFill(Color.WHITE);
        });
        textResume.setOnMouseClicked(e -> {
            BombermanGame.switchState(SceneState.PLAYING);
            BombermanGame.restartGame(BombermanGame.getCurrentLevel());
        });
        Group textGroup = new Group(textPause, textResume);
        super.scene = new Scene(textGroup, Color.BLACK);
    }

    public NextStage addHandler() {
        return this;
    }
}
