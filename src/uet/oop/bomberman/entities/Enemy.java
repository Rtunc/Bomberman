package uet.oop.bomberman.entities;

/**
 * Enemy đơn giản là enemy, có thể kiểm tra va chạm với bomber.
 */
public abstract class Enemy extends SetAnimatedEntity {

    /**
     * Khởi tạo ban đầu với vị trí
     *
     * Các con enemy cụ thể sẽ cài tập ảnh sau
     * @param xUnit x
     * @param yUnit y
     */
    public Enemy(int xUnit, int yUnit) {
        super(xUnit, yUnit, null);
    }

    /**
     * Mỗi enemy có nước đi khác nhau
     */
    protected abstract void calculateMove();

    protected void move(double xA, double yA) {
        x += xA;
        y += yA;
    }

    /**
     * Check collision với bomber
     * @param bomber bomber
     */
    public void checkBomber(Bomber bomber) {
        if (this.collision(bomber.getX(), bomber.getY())) {
            bomber.setBomberDead();
        }
    }
}
