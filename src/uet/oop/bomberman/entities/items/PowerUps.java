package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;

import java.io.File;

public abstract class PowerUps extends Entity {

    public PowerUps(int x, int y, Image img) {
        super(x, y, img);
    }

    protected abstract void interactWithBomber(Bomber bomber);

    public void checkBomber(Bomber bomber) {
        if (this.collision(bomber.getX(), bomber.getY())) {
            interactWithBomber(bomber);
            String path = "res/Bomberman SFX (4).wav";
            AudioClip media = new AudioClip(new File(path).toURI().toString());
            //MediaPlayer mediaPlayer = new MediaPlayer(media);
            media.play(0.1);
            System.out.println("pow");
            this.isDead = true;
            super.setRemove(true);
            this.isRender = false;
        }
    }

    @Override
    public void update() {
    }
}
