package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.font.GraphicAttribute;

public class Bomber extends SetAnimatedEntity {

    public boolean goNorth;
    public boolean goSouth;
    public boolean goEast;
    public boolean goWest;
    public MovingDirection inputDirection;
    private boolean alive = true;
    private int numberOfBombs = 1;
    private int velocity = 1;

    private int maxVelocity = 3;
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

        super.setCurrentState(MovingDirection.STAND);
    }
    /**
     * Chỉ cho debug, không hiệu ứng
     */
    public Bomber(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void resetVelocity() {
        this.velocity = 1;
    }

    public void increaseVelocity() {
        if (currentFrameCount < BombermanGame.fps / MAX_ANIMATE / 2) {
            this.velocity++;
        }
        if (velocity > maxVelocity) velocity = maxVelocity;
    }

    public void setInputDirection(MovingDirection inputDirection) {
        this.inputDirection = inputDirection;
        if (inputDirection == null) {
            super.setCurrentState(MovingDirection.STAND);
        }
    }

    protected void calculateMove() {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
//        int xa = 0, ya = 0;
//        if (goNorth && BombermanGame.isFree(x, y - 1)) {
//            ya -= 1;
//            super.setCurrentDirection(MovingDirection.UP);
//        }
//        if (goSouth && BombermanGame.isFree(x, y + 1)) {
//            ya += 1;
//            super.setCurrentDirection(MovingDirection.DOWN);
//        }
//        if (goEast && BombermanGame.isFree(x + 1, y)) {
//            xa += 1;
//            super.setCurrentDirection(MovingDirection.RIGHT);
//        }
//        if (goWest && BombermanGame.isFree(x - 1, y)) {
//            xa -= 1;
//            super.setCurrentDirection(MovingDirection.LEFT);
//        }
//        if (xa != 0 || ya != 0) {
//            move(xa, ya);
//        }

        if (inputDirection == null) {
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
            }
        }
    }

    public void move(double xa, double ya) {
//        TODO: xử lý input cho việc nhân vật chen vào giữa đơn giản hơn
//        int size = Sprite.SCALED_SIZE;
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
//        if (x % size < size - (x % size)) {
//            x -= x % size;
//        } else {
//            x += size - (x % size);
//        }
//        if (x % size < size - (x % size)) {
//            x -= x % size;
//        } else {
//            x += size - (x % size);
//        }
    }

    /**
     * Hàm kiểm tra có di chuyển được không so với block, brick, bomb
     *
     * @param nextX next X
     * @param nextY next Y
     * @return có di chuyển được không
     */
    public boolean canMove(int nextX, int nextY) {
        int size = Sprite.SCALED_SIZE;
        int defaultSize = Sprite.DEFAULT_SIZE;
        if (nextX % size < size - (nextX % size) && nextX % size <= 3 * (size / defaultSize)) {
            nextX -= nextX % size;
        } else if (size - (nextX % size) <= 3 * (size / defaultSize)) {
            nextX += size - (nextX % size);
        }
        if (nextY % size < size - (nextY % size) && nextY % size <= 3 * (size / defaultSize)) {
            nextY -= nextY % size;
        } else if (size - (nextX % size) <= 3 * (size / defaultSize)) {
            nextY += size - (nextY % size);
        }
        boolean result = BombermanGame.isFree(nextX, nextY);
        return result;
    }


    @Override
    public void update() {


        calculateMove();
//
//        detectPlaceBomb();

    }

    public enum StatusDirection {
        UP, RIGHT, DOWN, LEFT, STOP, DIE
    }
}
