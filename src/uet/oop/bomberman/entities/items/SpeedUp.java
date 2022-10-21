package uet.oop.bomberman.entities.items;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedUp extends PowerUps{
    public SpeedUp(int x, int y) {
        super(x, y, Sprite.powerup_speed.getFxImage());
    }

    @Override
    protected void interactWithBomber(Bomber bomber) {
        bomber.addMaxVelocity();
    }
}
