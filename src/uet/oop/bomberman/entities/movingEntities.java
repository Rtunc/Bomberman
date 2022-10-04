package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class movingEntities extends Entity {
    protected int frame = 0;
    public movingEntities( int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    protected final int MAX_ANIMATE = 7500;

    protected void animate() {
        if(frame < MAX_ANIMATE) frame++; else frame = 0;
    }
    public void move(){

    }
}
