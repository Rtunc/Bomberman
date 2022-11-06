package uet.oop.bomberman.entities.menu;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.MovingDirection;

public class GameScene extends SceneManager {
    Pane parent;
    Rectangle rectangle;

    public GameScene(Pane parent) {
        scene = new Scene(parent);
        this.parent = parent;
        rectangle = new Rectangle(0, 0, scene.getWidth(), 50);
        rectangle.setFill(new Color(0f, 0f, 0f, 0.2));
        Group group = new Group(rectangle);
        parent.getChildren().add(group);
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
//        Text textPause = new Text();
//        textPause.setText("Heart: " + (BombermanGame.bomberman.getHeart() + 1));
//        textPause.setFont(Font.font("Courier New", FontWeight.NORMAL, 14));
//        textPause.setFill(Color.WHITE);
//        textPause.setX(20);
//        textPause.setY(10);
        return this;
    }
}
