package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

import java.util.ArrayList;
import java.util.List;

/**
 * AnimatedEntity cho những thứ có hiệu ứng trên màn hình
 */
public abstract class AnimatedEntity extends Entity {
    /**
     * Số frame tạo hiệu ứng
     * Thường sẽ tính được như 60 frame 1s, có 4 frame => sau 25 frame
     * gọi hàm animate() 1 lần để thay đổi frame.
     */
    protected int MAX_ANIMATE = 0;
    /**
     * frame hiện tại.
     */
    protected int frame = 0;

    /**
     * currentFrameCount cho biết đang ở frame thứ bao nhiêu / tổng số frame
     */
    protected int currentFrameCount = 0;
    /**
     * frameSet là tập các frame tạo hiệu ứng
     */
    protected List<Image> frameSet;

    /**
     * isRender cho phép dừng việc render lại, mặc định là true
     */
    protected boolean isRender = true;

    /**
     * Khởi tạo với 1 image mặc định.
     *
     * @param xUnit vị trí x.
     * @param yUnit vị trí y.
     * @param img   hình ảnh mặc định (không hiệu ứng).
     */
    public AnimatedEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        frameSet = new ArrayList<>();
        frameSet.add(img);
        MAX_ANIMATE++;
    }

    /**
     * Thay đổi tập frame nếu chúng có hiệu ứng mới.
     *
     * @param frameSet tập frame hiệu ứng mới khác tập frame hiện tại.
     */
    public void setFrameSet(List<Image> frameSet) {
        this.frameSet = frameSet;
        MAX_ANIMATE = frameSet.size();
    }

    /**
     * Thêm hình ảnh vào tập frame.
     * Lưu ý phải đảm bảo số lượng frame và cách thức tính frame cho chuyển động.
     *
     * @param img frame thêm vào.
     */
    public void addFrame(Image img) {
        frameSet.add(img);
        MAX_ANIMATE++;
    }

    /**
     * Thay đổi frame để tạo hiệu ứng.
     */
    protected void animate() {
        if (frame < MAX_ANIMATE - 1) {
            frame++;
        } else {
            frame = 0;
        }
        super.setImg(frameSet.get(frame));
    }

    /**
     * render override lại để render hiệu ứng
     * với thời lượng = fps / frame / 3 (vì không / 3 hiệu ứng bị chậm, có thể thay đổi tùy thích)
     * nếu isRender không cho render => không render
     * @param gc GC
     */
    @Override
    public void render(GraphicsContext gc) {
        if (this.currentFrameCount < BombermanGame.fps / this.MAX_ANIMATE / 3) {
            this.currentFrameCount++;
        } else {
            this.currentFrameCount = 0;
            this.animate();
        }
        if (isRender) {
            super.render(gc);
        }
    }
}
