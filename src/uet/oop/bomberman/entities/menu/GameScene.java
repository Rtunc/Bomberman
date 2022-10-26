package uet.oop.bomberman.entities.menu;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.MovingDirection;

public class GameScene extends SceneManager {
    public GameScene(Parent parent) {
        scene = new Scene(parent);
    }

    public GameScene addHandler(Bomber bomberman) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    bomberman.setInputDirection(MovingDirection.UP);
                    bomberman.increaseVelocity();
                    break;
                case DOWN:
                    bomberman.setInputDirection(MovingDirection.DOWN);
                    bomberman.increaseVelocity();
                    break;
                case LEFT:
                    bomberman.setInputDirection(MovingDirection.LEFT);
                    bomberman.increaseVelocity();
                    break;
                case RIGHT:
                    bomberman.setInputDirection(MovingDirection.RIGHT);
                    bomberman.increaseVelocity();
                    break;
                case SPACE:
                    bomberman.placeBomb();
                    break;
                case ESCAPE:
                    BombermanGame.switchState(SceneState.PAUSE);
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case UP:
                case RIGHT:
                case DOWN:
                case LEFT:
                    bomberman.setInputDirection(null);
                    bomberman.resetVelocity();
                    break;
            }
        });
        return this;
    }
}
