package uet.oop.bomberman.entities.menu;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Highscore;
import uet.oop.bomberman.graphics.Sprite;

public class GameOverScene extends SceneManager {

    public static GameOverScene GAMEOVERSCENE = new GameOverScene();

    private Group textGroup;

    private Text textNameScore = new Text();

    public void update() {
        textNameScore.setText(BombermanGame.bomberman.getName() + " - " + BombermanGame.bomberman.getPoint());
        textNameScore.setX(Sprite.SCALED_SIZE * 6 - textNameScore.getLayoutBounds().getWidth()/2);
        super.scene.setRoot(new Group());
        super.scene = new Scene(textGroup, Color.BLACK);
    }

    public GameOverScene() {
        Text textPause = new Text();
        textPause.setText("GAME OVER");
        textPause.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textPause.setFill(Color.WHITE);
        textPause.setX(Sprite.SCALED_SIZE * 6 - textPause.getLayoutBounds().getWidth()/2);
        textPause.setY(50 * Sprite.SCALED);

        textNameScore.setText(BombermanGame.bomberman.getName() + " - " + BombermanGame.bomberman.getPoint());
        textNameScore.setFont(Font.font("Courier New", FontWeight.NORMAL, 18 * Sprite.SCALED));
        textNameScore.setFill(Color.WHITE);
        textNameScore.setX(Sprite.SCALED_SIZE * 6 - textNameScore.getLayoutBounds().getWidth()/2);
        textNameScore.setY(90 * Sprite.SCALED);

        Text textResume = new Text("NEW GAME");
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
            Highscore.getInstance().returnSave();
            BombermanGame.restartGame(BombermanGame.setCurrentLevel(1));
            BombermanGame.bomberman.resetPoint();
            BombermanGame.saveGame();
        });

        Text textScore = new Text("HIGH SCORE");
        textScore.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textScore.setFill(Color.WHITE);
        textScore.setX(Sprite.SCALED_SIZE * 6 - textScore.getLayoutBounds().getWidth()/2);
        textScore.setY(200 * Sprite.SCALED);
        textScore.setOnMouseEntered(e -> {
            textScore.setFill(Color.SKYBLUE);
        });
        textScore.setOnMouseExited(e -> {
            textScore.setFill(Color.WHITE);
        });
        textScore.setOnMouseClicked(e -> {
            Highscore.getInstance().returnSave();
            BombermanGame.setCurrentLevel(1);
            BombermanGame.bomberman.resetPoint();
            BombermanGame.saveGame();
            HighScore.HIGHSCOREPANEL.updateList();
            BombermanGame.switchState(SceneState.HIGHSCORE);
        });

        Text textHome = new Text("HOME");
        textHome.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textHome.setFill(Color.WHITE);
        textHome.setX(Sprite.SCALED_SIZE * 6 - textHome.getLayoutBounds().getWidth()/2);
        textHome.setY(250 * Sprite.SCALED);
        textHome.setOnMouseEntered(e -> {
            textHome.setFill(Color.SKYBLUE);
        });
        textHome.setOnMouseExited(e -> {
            textHome.setFill(Color.WHITE);
        });
        textHome.setOnMouseClicked(e -> {
            Highscore.getInstance().returnSave();
            BombermanGame.setCurrentLevel(1);
            BombermanGame.bomberman.resetPoint();
            BombermanGame.saveGame();
            BombermanGame.switchState(SceneState.MENU);
        });

        textGroup = new Group(textPause, textScore, textHome, textNameScore, textResume);
        super.scene = new Scene(textGroup, Color.BLACK);
    }

    public GameOverScene addHandler() {
        return this;
    }
}
