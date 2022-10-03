package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Bomber extends movingEntities {
    private double _speed=5;
    protected boolean _alive = true;
    public boolean _moving = false;
    public enum StatusDirection {
        UP, RIGHT, DOWN, LEFT, STOP,DIE
    }
    public boolean running, goNorth, goSouth, goEast, goWest;

    public StatusDirection status;

    private int speed=5;
    public Bomber(int x, int y, Image img) {
        super( x, y, img);
    }

    protected void calculateMove() {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
        int xa = 0, ya = 0;
        if (goNorth) ya -= 1;
        if (goSouth) ya += 1;
        if (goEast)  xa += 1;
        if (goWest)  xa -= 1;
            System.out.println(xa);
        if(xa != 0 || ya != 0)  {
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
   public void canMove(){

   }
    @Override
    public void update() {
        animate();

        calculateMove();
//
//        detectPlaceBomb();

    }
}
