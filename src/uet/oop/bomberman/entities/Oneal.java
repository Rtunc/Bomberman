package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Entity {
    int time_dead=6;
    public Oneal(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {
        if(isDead==true) {
            time_dead--;
           this.setImg(Sprite.oneal_dead.getFxImage());
           if(time_dead<0) {
               setRemove(true);
           }
        }
    }
}
