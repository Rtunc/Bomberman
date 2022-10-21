package uet.oop.bomberman.entities.items;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class AddFlame extends PowerUps{
    public AddFlame(int x, int y) {
        super(x, y, Sprite.powerup_flames.getFxImage());
    }

    @Override
    protected void interactWithBomber(Bomber bomber) {
        bomber.increaseFlameRadius();
    }
}
