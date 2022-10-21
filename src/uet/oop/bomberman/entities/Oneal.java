package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.HashMap;
import java.util.Map;

/**
 * Oneal chỉ còn va chạm với bomb, muốn thay đổi gì nhắn lại
 */
public class Oneal extends Enemy {
    MovingDirection randomMove = MovingDirection.LEFT;
    private final int velocity = 2;

    public Oneal(int x, int y) {
        super(x, y);
        super.addFrame(Sprite.oneal_right1.getFxImage(), MovingDirection.STAND);

        super.addFrame(Sprite.oneal_left1.getFxImage(), MovingDirection.LEFT);
        super.addFrame(Sprite.oneal_left2.getFxImage(), MovingDirection.LEFT);
        super.addFrame(Sprite.oneal_left3.getFxImage(), MovingDirection.LEFT);

        super.addFrame(Sprite.oneal_right1.getFxImage(), MovingDirection.RIGHT);
        super.addFrame(Sprite.oneal_right2.getFxImage(), MovingDirection.RIGHT);
        super.addFrame(Sprite.oneal_right3.getFxImage(), MovingDirection.RIGHT);

        super.addFrame(Sprite.oneal_dead.getFxImage(), CollisionAction.DEAD);
        super.addFrame(Sprite.mob_dead1.getFxImage(), CollisionAction.DEAD);
        super.addFrame(Sprite.mob_dead2.getFxImage(), CollisionAction.DEAD);
        super.addFrame(Sprite.mob_dead3.getFxImage(), CollisionAction.DEAD);
        super.setCurrentState(MovingDirection.STAND);
    }

    @Override
    protected void calculateMove() {

        switch (randomMove) {
            case UP:
                if (canMove(x, y - velocity)) {
                    move(0, -velocity);
                } else {
                    randomMove = (MovingDirection) randomMove.getNext(randomMove);
                    calculateMove();
                }
                break;
            case DOWN:
                if (canMove(x, y + velocity)) {
                    move(0, +velocity);
                } else {
                    randomMove = (MovingDirection) randomMove.getNext(randomMove);
                    calculateMove();
                }
                break;
            case LEFT:
                if (canMove(x - velocity, y)) {
                    move(-velocity, 0);
                    super.setCurrentState(MovingDirection.LEFT);
                } else {
                    randomMove = (MovingDirection) randomMove.getNext(randomMove);
                    calculateMove();
                }
                break;
            case RIGHT:
                if (canMove(x + velocity, y)) {
                    move(+velocity, 0);
                    super.setCurrentState(MovingDirection.RIGHT);
                } else {
                    randomMove = (MovingDirection) randomMove.getNext(randomMove);
                    calculateMove();
                }
                break;
            case STAND:
                randomMove = MovingDirection.UP;
        }
    }

    private boolean canMove(int nextX, int nextY) {
        boolean result = BombermanGame.isFree(nextX, nextY);
        if (BombermanGame.getBomb(nextX, nextY) != null) {
            result = false;
        }
        return result;
    }

    @Override
    public void update() {
        super.update();
        calculateMove();
    }
}
