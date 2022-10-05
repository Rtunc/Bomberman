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
public abstract class SetAnimatedEntity extends AnimatedEntity {
    /**
     * Danh sách các hiệu ứng tùy thuộc vào hướng đi
     */
    protected Map<AnimateAction, List<Image>> frameSet;

    private AnimateAction currentState;

    /**
     * Khởi tạo hình ảnh mặc định không hiệu ứng
     * Muốn thay đổi vị trí sau khi di chuyển, dùng setX, setY
     *
     * @param xUnit Vị trí x.
     * @param yUnit Vị trí y.
     * @param img   hình ảnh không hiệu ứng.
     */
    public SetAnimatedEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        frameSet = new HashMap<>();
    }

    /**
     * Thêm frame tùy vào hướng di chuyển
     *
     * @param img       hình ảnh thêm.
     * @param state hướng di chuyển
     */
    public void addFrame(Image img, AnimateAction state) {
        frameSet.computeIfAbsent(state, k -> new ArrayList<>());
        frameSet.get(state).add(img);
    }

    public AnimateAction getCurrentState() {
        return currentState;
    }

    /**
     * Nếu nhân vật có đổi hướng di chuyển, gọi hàm này để thay đổi tập hiệu ứng.
     *
     * @param state hướng đi mới.
     */
    public void setCurrentState(AnimateAction state) {
        this.currentState = state;
        this.switchState();
    }

    private void switchState() {
        super.setFrameSet(frameSet.get(currentState));
    }
}
