package uet.oop.bomberman.entities.menu;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Pair;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Highscore;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class HighScore extends SceneManager {

    private static List<Pair<String, Integer>> highScore;

    public static HighScore HIGHSCOREPANEL = new HighScore();

    public void updateList() {
        Group temp = new Group();
        super.scene.setRoot(temp);
        highScore = Highscore.getInstance().getHighScore();
        for (int i = 0; i < highScore.size(); i++) {
            Text textScore = new Text();
            textScore.setText(highScore.get(i).getKey() + " - " + highScore.get(i).getValue());
            textScore.setFont(Font.font("Courier New", FontWeight.NORMAL, 18 * Sprite.SCALED));
            textScore.setFill(Color.WHITE);
            textScore.setX(Sprite.SCALED_SIZE * 6 - textScore.getLayoutBounds().getWidth()/2);
            textScore.setY((90 + i*30) * Sprite.SCALED);
            textGroup.getChildren().add(textScore);
        }
        super.scene = new Scene(textGroup, Color.BLACK);
    }

    private Group textGroup = new Group();

    public HighScore() {
        Text textPause = new Text();
        textPause.setText("HIGH SCORE");
        textPause.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textPause.setFill(Color.WHITE);
        textPause.setX(Sprite.SCALED_SIZE * 6 - textPause.getLayoutBounds().getWidth()/2);
        textPause.setY(50 * Sprite.SCALED);


        Text textResume = new Text("HOME");
        textResume.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textResume.setFill(Color.WHITE);
        textResume.setX(Sprite.SCALED_SIZE * 6 - textResume.getLayoutBounds().getWidth()/2);
        textResume.setY(300 * Sprite.SCALED);
        textResume.setOnMouseEntered(e -> {
            textResume.setFill(Color.SKYBLUE);
        });
        textResume.setOnMouseExited(e -> {
            textResume.setFill(Color.WHITE);
        });
        textResume.setOnMouseClicked(e -> {
            textGroup.getChildren().clear();
            textGroup.getChildren().add(textPause);
            textGroup.getChildren().add(textResume);
            BombermanGame.switchState(SceneState.MENU);
        });

        textGroup.getChildren().add(textPause);
        textGroup.getChildren().add(textResume);

        super.scene = new Scene(textGroup, Color.BLACK);
    }

    public HighScore addHandler() {
        return this;
    }
}
