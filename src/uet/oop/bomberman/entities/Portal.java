package uet.oop.bomberman.entities;

import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Portal extends Entity {
    private boolean isOpen = false;

    public Portal(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.portal.getFxImage());
    }

    @Override
    public void update() {
        if (BombermanGame.enemies.size() == 0 && !isOpen) {
            clearEntity();
            isOpen = true;
        }
        if (isOpen) {
            checkBomber(BombermanGame.bomberman);
        }
    }

    public void checkBomber(Bomber bomber) {
        if (this.collision(bomber.getX(), bomber.getY())) {
            BombermanGame.restartGame(1);
        }
    }

    private void clearEntity() {
        List<Entity> entityList = BombermanGame.FindList(getXUnit(), getYUnit(), BombermanGame.stillObjects);
        for (Entity e :
                entityList) {
            if (e instanceof Grass) {
                e.setDead(true);
                e.isRender = false;
                e.setRemove(true);
            }
        }
        WritableImage image = new WritableImage(Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
        image.getPixelWriter().setColor(0, 0, Color.BLACK);
        this.img = image;
    }
}
