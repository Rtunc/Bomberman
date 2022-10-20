package uet.oop.bomberman.entities;

import uet.oop.bomberman.entities.bomb.Bomb;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

/**
 * Bomber là người chơi
 */
public class Bomber extends SetAnimatedEntity {

    /**
     * Max tốc độ khi người chơi nhấn giữ
     */
    private static final int maxVelocity = 3;

    /**
     * Hướng nhập từ người chơi
     */
    public MovingDirection inputDirection;
    /**
     * isAlive còn sống không
     */
    private boolean alive = true;
    private int numberOfBombs = 1;
    private int velocity = 1;


    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public void setNumberOfBombs(int numberOfBombs) {
        this.numberOfBombs = numberOfBombs;
    }

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
        if (!this.alive) {
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
        y += ya;
//
        x += xa;
//
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

        return BombermanGame.isFree(nextX, nextY);
    }

    public void placeBomb() {
        if(numberOfBombs>=1) {
            Bomb b = new Bomb(this.getXUnit(), this.getYUnit());
            BombermanGame.bombs.add(b);
            numberOfBombs--;
        }

    }
    @Override
    public void update() {


        calculateMove();
//
//        detectPlaceBomb();

    }

    /**
     * Cài đặt hành động khi người chơi chết
     */
    public void setBomberDead() {
        if (this.alive) {
            super.setCurrentState(CollisionAction.DEAD);
            super.frame = 0;
            this.alive = false;
        }
        if (frame == MAX_ANIMATE - 1) {
            isRender = false;
        }
    }

}
