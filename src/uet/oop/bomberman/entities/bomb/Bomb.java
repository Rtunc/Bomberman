package uet.oop.bomberman.entities.bomb;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;


import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.List;
import java.util.Map;

public class Bomb extends AnimatedEntity {
    protected int radius;
    protected int  _timeToExplode = 120; //2 seconds - thoi gian phat no
    public double _timeAfter = 20;// thoi gian de no
    protected  List<Image> explodeFrame;

    public boolean isExploded = false;

    public Bomb (int xUnit, int yUnit) {
        super(xUnit, yUnit, Sprite.bomb.getFxImage());
        this.addFrame(Sprite.bomb_1.getFxImage());
        this.addFrame(Sprite.bomb_2.getFxImage());



    }


//    protected boolean isExploded = false;

    public void update() {
        if(_timeToExplode > 0)
            _timeToExplode--;

        else {


            if(!isExploded)
                explode();
            else
//                updateFlames();

                if(_timeAfter > 0)
                    _timeAfter--;
                else {
//                remove();
                }
        }


    }

    public void render(GraphicsContext gc) {
        if(!isExploded){
            if (this.currentFrameCount < BombermanGame.fps / this.MAX_ANIMATE / 3) {
                this.currentFrameCount++;
            } else {
                this.currentFrameCount = 0;
                this.animate();
            }
        }
        super.render(gc);


    }

    protected void explode() {//nổ
        isExploded = true;
        // TODO: xử lý khi Character đứng tại vị trí Bomb
        // TODO: tạo các Flame

    }


}
