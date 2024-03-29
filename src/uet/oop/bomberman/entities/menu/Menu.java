package uet.oop.bomberman.entities.menu;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Highscore;
import uet.oop.bomberman.graphics.Sprite;

public class Menu extends SceneManager {

    public Menu() {
        Text textPause = new Text();
        textPause.setText("BOMBERMAN");
        textPause.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textPause.setFill(Color.WHITE);
        textPause.setX(Sprite.SCALED_SIZE * 6 - textPause.getLayoutBounds().getWidth()/2);
        textPause.setY(50 * Sprite.SCALED);
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

        Text aContinue = new Text("CONTINUE");
        aContinue.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        aContinue.setFill(Color.WHITE);
        aContinue.setX(Sprite.SCALED_SIZE * 6 - aContinue.getLayoutBounds().getWidth()/2);
        aContinue.setY(200 * Sprite.SCALED);
        aContinue.setOnMouseEntered(e -> {
            aContinue.setFill(Color.SKYBLUE);
        });
        aContinue.setOnMouseExited(e -> {
            aContinue.setFill(Color.WHITE);
        });
        aContinue.setOnMouseClicked(e -> {
            BombermanGame.switchState(SceneState.PLAYING);
            BombermanGame.continueGame();
        });

        Text aHighScore = new Text("HIGH SCORE");
        aHighScore.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        aHighScore.setFill(Color.WHITE);
        aHighScore.setX(Sprite.SCALED_SIZE * 6 - aHighScore.getLayoutBounds().getWidth()/2);
        aHighScore.setY(250 * Sprite.SCALED);
        aHighScore.setOnMouseEntered(e -> {
            aHighScore.setFill(Color.SKYBLUE);
        });
        aHighScore.setOnMouseExited(e -> {
            aHighScore.setFill(Color.WHITE);
        });
        aHighScore.setOnMouseClicked(e -> {
            HighScore.HIGHSCOREPANEL.updateList();
            BombermanGame.switchState(SceneState.HIGHSCORE);
        });

        Text textQuit = new Text("QUIT");
        textQuit.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textQuit.setFill(Color.WHITE);
        textQuit.setX(Sprite.SCALED_SIZE * 6 - textQuit.getLayoutBounds().getWidth()/2);
        textQuit.setY(350 * Sprite.SCALED);
        textQuit.setOnMouseEntered(e -> {
            textQuit.setFill(Color.SKYBLUE);
        });
        textQuit.setOnMouseExited(e -> {
            textQuit.setFill(Color.WHITE);
        });
        textQuit.setOnMouseClicked(e -> {
            Platform.exit();
        });
        Group textGroup = new Group(textPause, aHighScore, textQuit, textResume, aContinue);
        super.scene = new Scene(textGroup, Color.BLACK);
    }

    public Menu addHandler() {
        return this;
    }
}
