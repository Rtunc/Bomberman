package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends MovingEntity {
    private final double velocity = 5;
    private final int speed = 5;
    public boolean _moving = false;
    public boolean running, goNorth, goSouth, goEast, goWest;
    public StatusDirection status;
    protected boolean _alive = true;

    private MovingDirection currentState;

    /**
     * Khởi tạo Bomber với tập hình ảnh.
     *
     * @param x x ban đầu.
     * @param y t ban đầu
     */
    public Bomber(int x, int y) {
        super(x, y, null);

        super.addFrame(Sprite.player_up.getFxImage(), MovingDirection.UP);
        super.addFrame(Sprite.player_up_1.getFxImage(), MovingDirection.UP);
        super.addFrame(Sprite.player_up_2.getFxImage(), MovingDirection.UP);

        super.addFrame(Sprite.player_down.getFxImage(), MovingDirection.DOWN);
        super.addFrame(Sprite.player_down_1.getFxImage(), MovingDirection.DOWN);
        super.addFrame(Sprite.player_down_2.getFxImage(), MovingDirection.DOWN);

        super.addFrame(Sprite.player_left.getFxImage(), MovingDirection.LEFT);
        super.addFrame(Sprite.player_left_1.getFxImage(), MovingDirection.LEFT);
        super.addFrame(Sprite.player_left_2.getFxImage(), MovingDirection.LEFT);

        super.addFrame(Sprite.player_right.getFxImage(), MovingDirection.RIGHT);
        super.addFrame(Sprite.player_right_1.getFxImage(), MovingDirection.RIGHT);
        super.addFrame(Sprite.player_right_2.getFxImage(), MovingDirection.RIGHT);

        currentState = MovingDirection.RIGHT;
        super.switchState(currentState);
    }

    /**
     * Chỉ cho debug, không hiệu ứng
     */
    public Bomber(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    protected void calculateMove() {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
        int xa = 0, ya = 0;
        if (goNorth && BombermanGame.isFree(x, y - 1)) {
            ya -= 1;
            super.switchState(MovingDirection.UP);
        }
        if (goSouth && BombermanGame.isFree(x, y + 1)) {
            ya += 1;
            super.switchState(MovingDirection.DOWN);
        }
        if (goEast && BombermanGame.isFree(x + 1, y)) {
            xa += 1;
            super.switchState(MovingDirection.RIGHT);
        }
        if (goWest && BombermanGame.isFree(x - 1, y)) {
            xa -= 1;
            super.switchState(MovingDirection.LEFT);
        }
        if (xa != 0 || ya != 0) {
            move(xa, ya);

        }

    }

    public void move(double xa, double ya) {
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ _x, _y
        // TODO: nhớ cập nhật giá trị _direction sau khi di chuyển
//        if(xa > 0) status = StatusDirection.RIGHT;
//        if(xa < 0) status = StatusDirection.LEFT;
//        if(ya > 0) status = StatusDirection.DOWN;
//        if(ya < 0) status = StatusDirection.UP;

        y += ya;

        x += xa;
//        if(canMove(0, ya)) { //separate the moves for the player can slide when is colliding
//
//        }
//
//        if(canMove(xa, 0)) {
//
//        }
    }

    public void canMove() {

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
