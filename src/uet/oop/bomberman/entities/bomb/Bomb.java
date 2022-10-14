package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;


import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
import java.util.Map;

public class Bomb extends AnimatedEntity {


    protected int radius = 3;
    protected int _timeToExplode = 120; //2 seconds - thoi gian phat no
    public int _timeAfter = 0;// thoi gian de no = 20s
    protected List<Image> explodeFrame;
    private Image horizontal = Sprite.explosion_horizontal.getFxImage();
    private Image vertical = Sprite.explosion_vertical.getFxImage();
    private Image topEnd = Sprite.explosion_vertical_top_last.getFxImage();
    private Image bottomEnd = Sprite.explosion_vertical_down_last.getFxImage();
    private Image rightEnd = Sprite.explosion_horizontal_right_last.getFxImage();
    private Image leftEnd = Sprite.explosion_horizontal_left_last.getFxImage();
    private Image center = Sprite.bomb_exploded.getFxImage();
    public boolean isExploded = false;

    public Bomb(int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.bomb.getFxImage());
        this.addFrame(Sprite.bomb_1.getFxImage());
        this.addFrame(Sprite.bomb_2.getFxImage());
    }


//    protected boolean isExploded = false;

    public void update() {
        if (_timeToExplode > 0)
            _timeToExplode--;
        else {
            if (!isExploded) {
                explode();
            } else {
                if (_timeAfter <= 18) {
                    _timeAfter++;
                } else {
                    setDead(true);
                }
            }

        }
    }


    public void updateImageFlames() {
        setImage("horizontal",
                Sprite.movingSprite(Sprite.explosion_horizontal,
                        Sprite.explosion_horizontal1,
                        Sprite.explosion_horizontal2, _timeAfter, 18).getFxImage());
        setImage("vertical",
                Sprite.movingSprite(Sprite.explosion_vertical,
                        Sprite.explosion_vertical1,
                        Sprite.explosion_vertical2, _timeAfter, 18). getFxImage());
        setImage("top",
                Sprite.movingSprite(Sprite.explosion_vertical_top_last,
                        Sprite.explosion_vertical_top_last1,
                        Sprite.explosion_vertical_top_last2, _timeAfter, 18).getFxImage());
        setImage("bottom", Sprite.movingSprite(Sprite.explosion_vertical_down_last,
                Sprite.explosion_vertical_down_last1,
                Sprite.explosion_vertical_down_last2, _timeAfter, 18).getFxImage());
        setImage("left",
                Sprite.movingSprite(Sprite.explosion_horizontal_left_last,
                        Sprite.explosion_horizontal_left_last1,
                        Sprite.explosion_horizontal_left_last2, _timeAfter, 18).getFxImage());
        setImage("right",
                Sprite.movingSprite(Sprite.explosion_horizontal_right_last,
                        Sprite.explosion_horizontal_right_last1,
                        Sprite.explosion_horizontal_right_last2, _timeAfter, 18).getFxImage());
        setImage("center",
                Sprite.movingSprite(Sprite.bomb_exploded,
                        Sprite.bomb_exploded1,
                        Sprite.bomb_exploded2, _timeAfter, 18).getFxImage());
    }

    public void render(GraphicsContext gc) {
        if (!isExploded) {
            if (this.currentFrameCount < BombermanGame.fps / this.MAX_ANIMATE / 3) {
                this.currentFrameCount++;
            } else {
                this.currentFrameCount = 0;
                this.animate();
            }
            super.render(gc);
        }
        else {
            for (int i = 1; i <= radius - 1; i++) {
                gc.drawImage(vertical, super.getX(), super.getY() - i * 32);
                gc.drawImage(vertical, super.getX(), super.getY() + i * 32);
                gc.drawImage(horizontal, super.getX() - i * 32, super.getY());
                gc.drawImage(horizontal, super.getX() + i * 32, super.getY());
            }
            gc.drawImage(topEnd, super.getX(), super.getY() - radius * 32);
            gc.drawImage(bottomEnd, super.getX(), super.getY() + radius * 32);
            gc.drawImage(rightEnd, super.getX() + radius * 32, super.getY());
            gc.drawImage(leftEnd, super.getX() - radius * 32, super.getY());
            gc.drawImage(center, super.getX(), super.getY());
            updateImageFlames();
        }



    }

    public void setImage(String dir, Image img) {
        if (dir.equals("top")) {
            this.topEnd = img;
        }
        if (dir.equals("bottom")) {
            this.bottomEnd = img;
        }
        if (dir.equals("left")) {
            this.leftEnd = img;
        }
        if (dir.equals("right")) {
            this.rightEnd = img;
        }
        if (dir.equals("horizontal")) {
            this.horizontal = img;
        }
        if (dir.equals("vertical")) {
            this.vertical = img;
        }
        if (dir.equals("center")) {
            this.center = img;
        }
    }

    protected void explode() {//nổ
        isExploded = true;
    }


}
