package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;

public abstract class PowerUps extends Entity {

    public PowerUps(int x, int y, Image img) {
        super(x, y, img);
    }

    protected abstract void interactWithBomber(Bomber bomber);

    public void checkBomber(Bomber bomber) {
        if (this.collision(bomber.getX(), bomber.getY())) {
            interactWithBomber(bomber);
            this.isDead = true;
            this.isRender = false;
        }
    }

    @Override
    public void update() {
    }
}
