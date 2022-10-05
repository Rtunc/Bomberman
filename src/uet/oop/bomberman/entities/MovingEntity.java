package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MovingEntity kế thừa Animate.
 * Để giúp những nhân vật chuyển động có hướng cụ thể.
 */
public abstract class MovingEntity extends AnimatedEntity {
    /**
     * Danh sách các hiệu ứng tùy thuộc vào hướng đi
     */
    protected Map<MovingDirection, List<Image>> frameSet;

    private MovingDirection currentDirection;

    /**
     * Khởi tạo hình ảnh mặc định không hiệu ứng
     * Muốn thay đổi vị trí sau khi di chuyển, dùng setX, setY
     *
     * @param xUnit Vị trí x.
     * @param yUnit Vị trí y.
     * @param img   hình ảnh không hiệu ứng.
     */
    public MovingEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        frameSet = new HashMap<>();
        for (MovingDirection direction :
                MovingDirection.values()) {
            frameSet.put(direction, new ArrayList<>());
        }
    }

    /**
     * Thêm frame tùy vào hướng di chuyển
     *
     * @param img       hình ảnh thêm.
     * @param direction hướng di chuyển
     */
    public void addFrame(Image img, MovingDirection direction) {
        frameSet.get(direction).add(img);
    }

    public MovingDirection getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Nếu nhân vật có đổi hướng di chuyển, gọi hàm này để thay đổi tập hiệu ứng.
     *
     * @param direction hướng đi mới.
     */
    public void setCurrentDirection(MovingDirection direction) {
        this.currentDirection = direction;
        this.switchState();
    }

    private void switchState() {
        super.setFrameSet(frameSet.get(currentDirection));

    }

    /**
     * Các hướng di chuyển nhân vật.
     */
    public enum MovingDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        STAND,
    }
}
