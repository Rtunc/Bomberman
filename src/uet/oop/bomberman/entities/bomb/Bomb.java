package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;

public class Bomb extends AnimatedEntity {

    public int _timeAfter = 0;// thoi gian de no = 20s
    public boolean isExploded = false;
    protected int radius = 1;
    protected int _timeToExplode = 120; //2 seconds - thoi gian phat no
    protected List<Image> explodeFrame;
    private int flameLengthLeft = 0;
    private int flameLengthTop = 0;
    private int flameLengthRight = 0;
    private int flameLengthDown = 0;
    private Image horizontal = Sprite.explosion_horizontal.getFxImage();
    private Image vertical = Sprite.explosion_vertical.getFxImage();
    private Image topEnd = Sprite.explosion_vertical_top_last.getFxImage();
    private Image bottomEnd = Sprite.explosion_vertical_down_last.getFxImage();
    private Image rightEnd = Sprite.explosion_horizontal_right_last.getFxImage();
    private Image leftEnd = Sprite.explosion_horizontal_left_last.getFxImage();
    private Image center = Sprite.bomb_exploded.getFxImage();

    public Bomb(int xUnit, int yUnit, int radius) {
        super(xUnit, yUnit, Sprite.bomb.getFxImage());
        this.addFrame(Sprite.bomb_1.getFxImage());
        this.addFrame(Sprite.bomb_2.getFxImage());
        this.radius = radius;
    }

//    protected int caculateFlameSize(int Status) {
//        switch (Status) {
//            case 0 : {
//                int tempSize = 1;
//                while (tempSize <= flameSize) {
//                    List<Entity> CheckList = BombermanGame.FindList(this.getXUnit(), this.getYUnit() - tempSize, BombermanGame.staticObject);
//                    for (Entity check : CheckList) {
//                        if (!check.collide(check)) {
//                            if (check instanceof Brick) {
//                                ((Brick) check).isDestroy();
//                            } else if (check instanceof Bomb && ((Bomb) check).timeToExplode > 0 ) {
//                                ((Bomb) check).timeToExplode = 1;
//                            }
//                            return tempSize - 1;
//                        }
//                    }
//                    if (tempSize == flameSize) return tempSize;
//                    tempSize++;
//                }
//                break;
//            }
//    protected boolean isExploded = false;

    public void update() {
        if (_timeToExplode > 0)
            _timeToExplode--;
        else {
            if (!isExploded) {
                isExploded = true;
                explode();
            } else {
                if (_timeAfter <= 18) {
                    checkEnitiies();
                    _timeAfter++;
                } else {
                    setRemove(true);
                }
            }

        }
    }


    public void updateImageFlames() {
        setImage("horizontal",
                Sprite.movingSprite(Sprite.explosion_horizontal,
                        Sprite.explosion_horizontal1,
                        Sprite.explosion_horizontal2, _timeAfter, 6).getFxImage());
        setImage("vertical",
                Sprite.movingSprite(Sprite.explosion_vertical,
                        Sprite.explosion_vertical1,
                        Sprite.explosion_vertical2, _timeAfter, 6).getFxImage());
        setImage("top",
                Sprite.movingSprite(Sprite.explosion_vertical_top_last,
                        Sprite.explosion_vertical_top_last1,
                        Sprite.explosion_vertical_top_last2, _timeAfter, 6).getFxImage());
        setImage("bottom", Sprite.movingSprite(Sprite.explosion_vertical_down_last,
                Sprite.explosion_vertical_down_last1,
                Sprite.explosion_vertical_down_last2, _timeAfter, 6).getFxImage());
        setImage("left",
                Sprite.movingSprite(Sprite.explosion_horizontal_left_last,
                        Sprite.explosion_horizontal_left_last1,
                        Sprite.explosion_horizontal_left_last2, _timeAfter, 6).getFxImage());
        setImage("right",
                Sprite.movingSprite(Sprite.explosion_horizontal_right_last,
                        Sprite.explosion_horizontal_right_last1,
                        Sprite.explosion_horizontal_right_last2, _timeAfter, 6).getFxImage());
        setImage("center",
                Sprite.movingSprite(Sprite.bomb_exploded,
                        Sprite.bomb_exploded1,
                        Sprite.bomb_exploded2, _timeAfter, 6).getFxImage());
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
        } else {
            gc.drawImage(center, super.getX(), super.getY());
            /*
              render flame theo 4 hướng trên, dưới, trái, phải
             */
            for (int i = 1; i <= flameLengthTop; i++) {
                gc.drawImage(i == radius ? topEnd : vertical, super.getX(), super.getY() - i * 32);
            }

            for (int i = 1; i <= flameLengthDown; i++) {
                gc.drawImage(i == radius ? bottomEnd : vertical, super.getX(), super.getY() + i * 32);

            }
            for (int i = 1; i <= flameLengthLeft; i++) {
                gc.drawImage(i == radius ? leftEnd : horizontal, super.getX() - i * 32, super.getY());
            }
            for (int i = 1; i <= flameLengthRight; i++) {
                gc.drawImage(i == radius ? rightEnd : horizontal, super.getX() + i * 32, super.getY());
            }
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

    private void checkEnitiies() {
        for (int i = 1; i <= flameLengthTop; i++) {
            List<Entity> checkList = BombermanGame.FindList(this.getXUnit(), this.getYUnit() - i, BombermanGame.stillObjects);
            List<Entity> entityList = BombermanGame.FindList(this.getXUnit(), this.getYUnit() - i, BombermanGame.entities);
            boolean stop = false;
            for (Entity check : checkList) {
                if (check instanceof Wall) {
                    stop = true;
                } else if (check instanceof Brick) {
                    check.setDead(true);
                    stop = true;
                }
            }
            for (Entity enemy : entityList) {
                if (enemy instanceof AliveEntity) {
                    ((AliveEntity) enemy).setDead();
                }
            }
            if (stop) {
                break;
            }
        }
        for (int i = 1; i <= flameLengthDown; i++) {
            List<Entity> checkList = BombermanGame.FindList(this.getXUnit(), this.getYUnit() + i, BombermanGame.stillObjects);
            List<Entity> entityList = BombermanGame.FindList(this.getXUnit(), this.getYUnit() + i, BombermanGame.entities);
            boolean stop = false;
            for (Entity check : checkList) {
                if (check instanceof Wall) {
                    stop = true;
                } else if (check instanceof Brick) {
                    check.setDead(true);
                    stop = true;
                }
            }
            for (Entity enemy : entityList) {
                if (enemy instanceof AliveEntity) {
                    ((AliveEntity) enemy).setDead();
                }
            }
            if (stop) {
                break;
            }
        }
        for (int i = 1; i <= flameLengthRight; i++) {
            List<Entity> checkList = BombermanGame.FindList(this.getXUnit() + i, this.getYUnit(), BombermanGame.stillObjects);
            List<Entity> entityList = BombermanGame.FindList(this.getXUnit() + i, this.getYUnit(), BombermanGame.entities);
            boolean stop = false;
            for (Entity check : checkList) {
                if (check instanceof Wall) {
                    stop = true;
                } else if (check instanceof Brick) {
                    check.setDead(true);
                    stop = true;
                }
            }
            for (Entity enemy : entityList) {
                if (enemy instanceof AliveEntity) {
                    ((AliveEntity) enemy).setDead();
                }
            }
            if (stop) {
                break;
            }
        }
        for (int i = 1; i <= flameLengthLeft; i++) {
            List<Entity> checkList = BombermanGame.FindList(this.getXUnit() - i, this.getYUnit(), BombermanGame.stillObjects);
            List<Entity> entityList = BombermanGame.FindList(this.getXUnit() - i, this.getYUnit(), BombermanGame.entities);
            boolean stop = false;
            for (Entity check : checkList) {
                if (check instanceof Wall) {
                    stop = true;
                } else if (check instanceof Brick) {
                    check.setDead(true);
                    stop = true;
                }
            }
            for (Entity enemy : entityList) {
                if (enemy instanceof AliveEntity) {
                    ((AliveEntity) enemy).setDead();
                }
            }
            if (stop) {
                break;
            }
        }
    }


    /**
     * Cập nhật sizeFlame của bomb + phá gạch;
     * TODO: Kill enemy
     */
    protected void explode() {//nổ
        for (int i = 1; i <= radius; i++) {
            List<Entity> checkList = BombermanGame.FindList(this.getXUnit(), this.getYUnit() - i, BombermanGame.stillObjects);
            List<Entity> entityList = BombermanGame.FindList(this.getXUnit(), this.getYUnit() - i, BombermanGame.entities);
            boolean stop = false;
            for (Entity check : checkList) {
                if (check instanceof Wall) {
                    stop = true;
                } else if (check instanceof Brick) {
                    check.setDead(true);
                    stop = true;
                }
            }
            for (Entity enemy : entityList) {
                if (enemy instanceof AliveEntity) {
                    ((AliveEntity) enemy).setDead();
                }
            }
            if (stop) {
                break;
            } else {
                if (flameLengthTop < radius) {
                    flameLengthTop++;
                }
            }
        }
        for (int i = 1; i <= radius; i++) {
            List<Entity> checkList = BombermanGame.FindList(this.getXUnit(), this.getYUnit() + i, BombermanGame.stillObjects);
            List<Entity> entityList = BombermanGame.FindList(this.getXUnit(), this.getYUnit() + i, BombermanGame.entities);
            boolean stop = false;
            for (Entity check : checkList) {
                if (check instanceof Wall) {
                    stop = true;
                } else if (check instanceof Brick) {
                    check.setDead(true);
                    stop = true;
                }
            }
            for (Entity enemy : entityList) {
                if (enemy instanceof AliveEntity) {
                    ((AliveEntity) enemy).setDead();
                }
            }
            if (stop) {
                break;
            } else {
                if (flameLengthDown < radius) {
                    flameLengthDown++;
                }
            }
        }
        for (int i = 1; i <= radius; i++) {
            List<Entity> checkList = BombermanGame.FindList(this.getXUnit() + i, this.getYUnit(), BombermanGame.stillObjects);
            List<Entity> entityList = BombermanGame.FindList(this.getXUnit() + i, this.getYUnit(), BombermanGame.entities);
            boolean stop = false;
            for (Entity check : checkList) {
                if (check instanceof Wall) {
                    stop = true;
                } else if (check instanceof Brick) {
                    check.setDead(true);
                    stop = true;
                }
            }
            for (Entity enemy : entityList) {
                if (enemy instanceof AliveEntity) {
                    ((AliveEntity) enemy).setDead();
                }
            }
            if (stop) {
                break;
            } else {
                if (flameLengthRight < radius) {
                    flameLengthRight++;
                }
            }
        }
        for (int i = 1; i <= radius; i++) {
            List<Entity> checkList = BombermanGame.FindList(this.getXUnit() - i, this.getYUnit(), BombermanGame.stillObjects);
            List<Entity> entityList = BombermanGame.FindList(this.getXUnit() - i, this.getYUnit(), BombermanGame.entities);
            boolean stop = false;
            for (Entity check : checkList) {
                if (check instanceof Wall) {
                    stop = true;
                } else if (check instanceof Brick) {
                    check.setDead(true);
                    stop = true;
                }
            }
            for (Entity enemy : entityList) {
                if (enemy instanceof AliveEntity) {
                    ((AliveEntity) enemy).setDead();
                }
            }
            if (stop) {
                break;
            } else {
                if (flameLengthLeft < radius) {
                    flameLengthLeft++;
                }
            }
        }
    }


}
