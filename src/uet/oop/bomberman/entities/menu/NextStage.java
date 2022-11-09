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

    public static NextStage NEXTSTAGE = new NextStage();

    private Text textScore = new Text();

    private Text textPause = new Text();

    private Group textGroup;

    public void update() {
        textScore.setText("SCORE: " + BombermanGame.bomberman.getPoint());
        textPause.setText("STAGE " + (BombermanGame.currentLevel - 1) + " CLEAR");
        textScore.setX(Sprite.SCALED_SIZE * 6 - textScore.getLayoutBounds().getWidth()/2);
        textPause.setX(Sprite.SCALED_SIZE * 6 - textPause.getLayoutBounds().getWidth()/2);
        super.scene.setRoot(new Group());
        super.scene = new Scene(textGroup, Color.BLACK);
    }

    public NextStage() {
        textPause.setText("STAGE " + BombermanGame.currentLevel + " CLEAR");
        textPause.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textPause.setFill(Color.WHITE);
        textPause.setX(Sprite.SCALED_SIZE * 6 - textPause.getLayoutBounds().getWidth()/2);
        textPause.setY(50 * Sprite.SCALED);

        textScore.setText("SCORE: " + BombermanGame.bomberman.getPoint());
        textScore.setFont(Font.font("Courier New", FontWeight.NORMAL, 18 * Sprite.SCALED));
        textScore.setFill(Color.WHITE);
        textScore.setX(Sprite.SCALED_SIZE * 6 - textScore.getLayoutBounds().getWidth()/2);
        textScore.setY(90 * Sprite.SCALED);


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
            BombermanGame.saveGame();
        });

        textGroup = new Group(textScore, textResume, textPause);
        super.scene = new Scene(textGroup, Color.BLACK);
    }

    public NextStage addHandler() {
        return this;
    }
}
