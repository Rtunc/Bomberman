package uet.oop.bomberman.entities;

import javafx.scene.media.AudioClip;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.menu.SceneState;

import java.io.File;

/**
 * Enemy đơn giản là enemy, có thể kiểm tra va chạm với bomber.
 */
public abstract class Enemy extends SetAnimatedEntity implements AliveEntity {

    /**
     * Khởi tạo ban đầu với vị trí
     *
     * Các con enemy cụ thể sẽ cài tập ảnh sau
     * @param xUnit x
     * @param yUnit y
     */
    public Enemy(int xUnit, int yUnit) {
        super(xUnit, yUnit, null);
    }

    /**
     * Mỗi enemy có nước đi khác nhau
     */
    protected abstract void calculateMove();

    /**
     * unitDirection để lưu vị trí di chuyển ở unit
     */
    protected MovingDirection unitDirection;

    /**
     * isCorrecting khóa di chuyển nhân vật
     */
    protected boolean isCorrecting = false;

    public void move(double xA, double yA) {
        if (BombermanGame.state == SceneState.PLAYING) {
            x += xA;
            y += yA;
        }
    }

    /**
     * Check collision với bomber
     * @param bomber bomber
     */
    public void checkBomber(Bomber bomber) {
        if (!this.isDead && this.collision(bomber.getX(), bomber.getY())) {
            bomber.setDead();
        }
    }

    public void setDead() {
        if (!this.isDead) {
            super.setCurrentState(CollisionAction.DEAD);
            super.frame = 0;
            this.isDead = true;
            super.setRemove(true);
            String path = "res/Bomberman SFX (3).wav";
            AudioClip media = new AudioClip(new File(path).toURI().toString());
            //MediaPlayer mediaPlayer = new MediaPlayer(media);
            media.play(0.5);
            System.out.println("ded");
        }
    }

    @Override
    public void update() {
        enemyDead();
    }

    private void enemyDead() {
        if (this.isDead) {
            if (frame == MAX_ANIMATE - 1) {
                isRender = false;
            }
        }
    }
}