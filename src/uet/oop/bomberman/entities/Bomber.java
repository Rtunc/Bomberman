package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

/**
 * Bomber là người chơi
 */
public class Bomber extends SetAnimatedEntity {

    /**
     * Hướng nhập từ người chơi
     */
    public MovingDirection inputDirection;
    /**
     * Max tốc độ khi người chơi nhấn giữ
     */
    private int maxVelocity = 3;
    private int numberOfBombs = 1;
    private int heart = 1;
    private int velocity = 1;
    private boolean autocorrecting = false;
    private int deadRecover = 120;
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
//        TODO: xử lý input cho việc nhân vật chen vào giữa đơn giản hơn
        y += ya;
//        if ((int) y % Sprite.SCALED_SIZE <= (Sprite.SCALED_SIZE / Sprite.DEFAULT_SIZE)
//        || (int) y % Sprite.SCALED_SIZE >= (Sprite.DEFAULT_SIZE - 1) *(Sprite.SCALED_SIZE / Sprite.DEFAULT_SIZE) ) {
//            if (ya < 0) {
//                y -= (Sprite.SCALED_SIZE / Sprite.DEFAULT_SIZE);
//            } else {
//                y += (Sprite.SCALED_SIZE / Sprite.DEFAULT_SIZE);
//            }
//        }
        x += xa;
//        if ((int) x % Sprite.SCALED_SIZE <= (Sprite.SCALED_SIZE / Sprite.DEFAULT_SIZE)
//                || (int) x % Sprite.SCALED_SIZE >= (Sprite.DEFAULT_SIZE - 1) *(Sprite.SCALED_SIZE / Sprite.DEFAULT_SIZE) ) {
//            if (ya < 0) {
//                x -= (Sprite.SCALED_SIZE / Sprite.DEFAULT_SIZE);
//            } else {
//                x += (Sprite.SCALED_SIZE / Sprite.DEFAULT_SIZE);
//            }
//        }
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
        return result;
    }

    public void placeBomb() {
        Bomb b = new Bomb(this.getXUnit(), this.getYUnit());
        BombermanGame.bombs.add(b);

    }

    @Override
    public void update() {

        bomberDead();
        calculateMove();
//
//        detectPlaceBomb();

    }

    /**
     * Cài đặt hành động khi người chơi chết
     */
    public void setDead() {
        if (!this.isDead) {
            super.setCurrentState(CollisionAction.DEAD);
            super.frame = 0;
            this.isDead = true;
        }
    }

    private void bomberDead() {
        if (this.isDead) {
            if (!isRender && heart > 0 && deadRecover == 0) {
                heart--;
                isRender = true;
                this.isDead = false;
                deadRecover = 120;
                super.setCurrentState(MovingDirection.STAND);
            } else if (frame == MAX_ANIMATE - 1) {
                isRender = false;
            }
            if (!isRender) {
                deadRecover--;
            }
        }
    }

}
