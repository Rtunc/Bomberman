package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class AddBomb extends PowerUps{
    public AddBomb(int x, int y) {
        super(x, y, Sprite.powerup_bombs.getFxImage());
    }

    @Override
    protected void interactWithBomber(Bomber bomber) {
        bomber.increaseNumberOfBombes();
    }
}
