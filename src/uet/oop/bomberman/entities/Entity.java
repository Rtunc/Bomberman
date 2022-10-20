package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;
    protected boolean isDead=false;
    protected boolean remove=false;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;
    protected Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    /**
     * lấy tọa độ X tọa độ đơn vị
     */
    public int getXUnit() {
        return (x+Sprite.DEFAULT_SIZE)/Sprite.SCALED_SIZE;
    }
    /**
     * lấy tọa độ X tọa độ đơn vị
     */
    public int getYUnit() {
        return (y+Sprite.DEFAULT_SIZE)/Sprite.SCALED_SIZE;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public void update() {

    }

    /**
     * Kiểm tra 2 thực thể có va chạm
     * @param x x thực thể cần kiểm tra
     * @param y y thực thể cần kiểm tra
     * @return có va chạm với thực thể này?
     */
    public boolean collision(int x, int y) {
        if (Math.abs(x - this.getX()) < Sprite.SCALED_SIZE) {
            return Math.abs(y - this.getY()) < Sprite.SCALED_SIZE;
        }
        return false;
    }

}
