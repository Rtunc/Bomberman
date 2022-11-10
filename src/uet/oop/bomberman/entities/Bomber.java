package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.menu.SceneState;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;

/**
 * Bomber là người chơi
 */
public class Bomber extends SetAnimatedEntity implements AliveEntity {
    /**
     * Hướng nhập từ người chơi
     */
    public MovingDirection inputDirection;
    int deadImmune = 0;
    private String name = "";
    private static int point = 0;
    /**
     * Max tốc độ khi người chơi nhấn giữ
     */
    private int maxVelocity = 3 * Sprite.SCALED;
    private int numberOfBombs = 2;
    private int flameRadius = 1;
    private int heart = 1;
    private int velocity = Sprite.SCALED;
    private boolean autocorrecting = false;
    private int deadRecover = 120;
    private boolean withBomb = false;
    private int lastBombX = 0;
    private int lastBombY = 0;
    /**
     * Khởi tạo Bomber với tập hình ảnh.
     *
     * @param x x ban đầu.
     * @param y t ban đầu
     */
    public Bomber(int x, int y) {
        super(x, y, null);

        super.addFrame(Sprite.player_down.getFxImage(), MovingDirection.STAND);

        super.addFrame(Sprite.player_up_1.getFxImage(), MovingDirection.UP);
        super.addFrame(Sprite.player_up.getFxImage(), MovingDirection.UP);
        super.addFrame(Sprite.player_up_2.getFxImage(), MovingDirection.UP);

        super.addFrame(Sprite.player_down_1.getFxImage(), MovingDirection.DOWN);
        super.addFrame(Sprite.player_down.getFxImage(), MovingDirection.DOWN);
        super.addFrame(Sprite.player_down_2.getFxImage(), MovingDirection.DOWN);

        super.addFrame(Sprite.player_left_1.getFxImage(), MovingDirection.LEFT);
        super.addFrame(Sprite.player_left.getFxImage(), MovingDirection.LEFT);
        super.addFrame(Sprite.player_left_2.getFxImage(), MovingDirection.LEFT);

        super.addFrame(Sprite.player_right_1.getFxImage(), MovingDirection.RIGHT);
        super.addFrame(Sprite.player_right.getFxImage(), MovingDirection.RIGHT);
        super.addFrame(Sprite.player_right_2.getFxImage(), MovingDirection.RIGHT);

        super.addFrame(Sprite.player_dead1.getFxImage(), CollisionAction.DEAD);
        super.addFrame(Sprite.player_dead2.getFxImage(), CollisionAction.DEAD);
        super.addFrame(Sprite.player_dead3.getFxImage(), CollisionAction.DEAD);

        super.setCurrentState(MovingDirection.STAND);
    }

    /**
     * Chỉ cho debug, không hiệu ứng
     */
    public Bomber(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void resetPoint() {
        this.point = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void increasePoint(int point) {
        this.point += point;
    }

    public int getFlameRadius() {
        return flameRadius;
    }

    public void setFlameRadius(int flameRadius) {
        this.flameRadius = flameRadius;
    }

    public void increaseFlameRadius() {
        this.flameRadius++;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public void increaseHeart() {
        this.heart++;
    }

    public void addMaxVelocity() {
        maxVelocity++;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public void setNumberOfBombs(int numberOfBombs) {
        this.numberOfBombs = numberOfBombs;
    }

    public void increaseNumberOfBombes() {
        this.numberOfBombs++;
    }

    public int getDeadRecover() {
        return deadRecover;
    }

    /**
     * Nhả tốc độ
     */
    public void resetVelocity() {
        this.velocity = 1;
    }

    /**
     * Tăng tốc khi giữ
     */
    public void increaseVelocity() {
        if (currentFrameCount < BombermanGame.fps / MAX_ANIMATE / 2) {
            this.velocity++;
        }
        if (velocity > maxVelocity) velocity = maxVelocity;
    }

    /**
     * Nhận vào hướng di chuyển từ người chơi
     *
     * @param inputDirection hướng di chuyển
     */
    public void setInputDirection(MovingDirection inputDirection) {
        if (autocorrecting) return;
        this.inputDirection = inputDirection;
        if (inputDirection == null) {
            super.setCurrentState(MovingDirection.STAND);
        }
    }

    /**
     * Tính toán nước đi
     */
    protected void calculateMove() {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
        if (inputDirection == null) {
            return;
        }
        if (this.isDead) {
            return;
        }

        int xVel = 0, yVel = 0;
        switch (inputDirection) {
            case UP:
                yVel -= velocity;
                break;
            case DOWN:
                yVel += velocity;
                break;
            case LEFT:
                xVel -= velocity;
                break;
            case RIGHT:
                xVel += velocity;
                break;
        }

        super.setCurrentState(inputDirection);
        if (xVel != 0 || yVel != 0) {
            if (canMove(x + xVel, y + yVel)) {
                move(xVel, yVel);
                autocorrecting = false;
            } else {
                int size = Sprite.SCALED_SIZE;
                int scaled = size / Sprite.DEFAULT_SIZE;
                int nextX = x + xVel;
                int nextY = y + yVel;
                int modX = nextX % size;
                int modY = nextY % size;
                if (size - modX <= 10 * scaled) {
                    if (canMove(nextX + (size - modX), nextY) && xVel == 0 && modX != 0) {
                        autocorrecting = true;
                        move(1, 0);
                    }
                } else if (modX <= 10 * scaled) {
                    if (canMove(nextX - modX, nextY) && xVel == 0 && modX != 0) {
                        autocorrecting = true;
                        move(-1, 0);
                    }
                }
                if (size - modY <= 10 * scaled) {
                    if (canMove(nextX, nextY + (size - modY)) && yVel == 0 && modY != 0) {
                        autocorrecting = true;
                        move(0, 1);
                    }
                } else if (modY <= 10 * scaled) {
                    if (canMove(nextX, nextY - modY) && yVel == 0 && modY != 0) {
                        autocorrecting = true;
                        move(0, -1);
                    }
                }
            }
        }
    }

    public void move(double xa, double ya) {
        if (BombermanGame.state == SceneState.PLAYING) {
            x += xa;
            y += ya;
        }
    }

    /**
     * Hàm kiểm tra có di chuyển được không so với block, brick, bomb
     * <p>
     * Cho phép lệch 3px so với gạch
     *
     * @param nextX next X
     * @param nextY next Y
     * @return có di chuyển được không
     */
    public boolean canMove(int nextX, int nextY) {
        boolean result = BombermanGame.isFree(nextX, nextY);
        if (BombermanGame.getBomb(nextX, nextY) != null && !withBomb) {
            result = false;
        }
        return result;
    }

    public void placeBomb() {
        if (numberOfBombs >= 1) {
            for (Entity e :
                    BombermanGame.enemies) {
                if (e.collision(this.getXUnit() * Sprite.SCALED_SIZE, this.getYUnit() * Sprite.SCALED_SIZE)) {
                    return;
                }
            }
            if (BombermanGame.getBomb(this.getX(), this.getY()) != null) {
                return;
            }
            Bomb b = new Bomb(this.getXUnit(), this.getYUnit(), flameRadius);
            BombermanGame.bombs.add(b);
            numberOfBombs--;
            withBomb = true;
            lastBombX = b.getX();
            lastBombY = b.getY();
        }

    }

    @Override
    public void update() {
        bomberDead();
        bomberDeadImmune();
        if (autocorrecting || inputDirection != null) {
            calculateMove();
        }
        if (withBomb && lastBombX != 0 && (Math.abs(lastBombX - x) > Sprite.SCALED_SIZE || Math.abs(lastBombY - y) > Sprite.SCALED_SIZE))
            withBomb = false;
//
//        detectPlaceBomb();

    }

    /**
     * Cài đặt hành động khi người chơi chết
     */
    public void setDead() {
        if (!this.isDead && deadImmune == 0) {
            super.setCurrentState(CollisionAction.DEAD);
            super.frame = 0;
            this.isDead = true;
            String path = "res/Bomberman SFX (2).wav";
            AudioClip media = new AudioClip(new File(path).toURI().toString());
            //MediaPlayer mediaPlayer = new MediaPlayer(media);
            media.play(0.1);
            System.out.println("bomb ded");
        }
    }

    public boolean isImmune() {
        return deadImmune != 0;
    }

    private void bomberDead() {
        if (this.isDead) {
            if (!isRender && heart > 0 && deadRecover == 0) {
                heart--;
                isRender = true;
                this.isDead = false;
                deadRecover = 120;
                deadImmune = 120;
                super.setCurrentState(MovingDirection.STAND);
            } else if (!isRender && heart == 0 && deadRecover == 0) {
                BombermanGame.gameOver = true;
            } else if (frame == MAX_ANIMATE - 1) {
                isRender = false;
            }
            if (!isRender) {
                deadRecover--;
            }
        }
    }

    private void bomberDeadImmune() {
        if (deadImmune > 0) {
            deadImmune--;
            isRender = deadImmune % 12 != 0;
            if (deadImmune == 0) {
                isRender = true;
            }
        }
    }

}
