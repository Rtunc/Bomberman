package uet.oop.bomberman.graphics;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;

public class Camera {

    private float offX, offY;
    private Entity target;

    public Camera(Entity player) {
        this.target = player;
    }

    public void update(BombermanGame bm) {
        if(target==null){
            return;
        }

        offX = target.getX() - 32*31/2;
        offY = target.getY() - 32*13/2;
    }

    public float getOffX() {
        return offX;
    }

    public void setOffX(float offX) {
        this.offX = offX;
    }

    public float getOffY() {
        return offY;
    }

    public void setOffY(float offY) {
        this.offY = offY;
    }

    public Entity getTarget() {
        return target;
    }

    public void setTarget(Entity target) {
        this.target = target;
    }
}
