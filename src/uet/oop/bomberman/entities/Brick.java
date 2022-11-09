package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity {
    private int timeDead=18;
    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if(isDead()) {
            if(timeDead<0) {
                this.setRemove(true);
            }
            timeDead--;
            this.setImg(Sprite.movingSprite(Sprite.brick_exploded2, Sprite.brick_exploded1, Sprite.brick_exploded,timeDead, 6).getFxImage());
        }
    }
}
