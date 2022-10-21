package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {

    MovingDirection randomMove = MovingDirection.LEFT;
    private final int velocity = 2;

    public Balloom(int x, int y) {
        super(x, y);
        super.addFrame(Sprite.balloom_right1.getFxImage(), MovingDirection.STAND);

        super.addFrame(Sprite.balloom_left1.getFxImage(), MovingDirection.LEFT);
        super.addFrame(Sprite.balloom_left2.getFxImage(), MovingDirection.LEFT);
        super.addFrame(Sprite.balloom_left3.getFxImage(), MovingDirection.LEFT);

        super.addFrame(Sprite.balloom_right1.getFxImage(), MovingDirection.RIGHT);
        super.addFrame(Sprite.balloom_right2.getFxImage(), MovingDirection.RIGHT);
        super.addFrame(Sprite.balloom_right3.getFxImage(), MovingDirection.RIGHT);

        super.addFrame(Sprite.balloom_dead.getFxImage(), CollisionAction.DEAD);
        super.setCurrentState(MovingDirection.STAND);
    }

    @Override
    protected void calculateMove() {
        if (canMove(x, y - velocity) && randomMove != MovingDirection.DOWN) {
            randomMove = MovingDirection.UP;
        } else if (canMove(x, y + velocity) && randomMove != MovingDirection.UP) {
            randomMove = MovingDirection.DOWN;
        } else if (canMove(x - velocity, y) && randomMove != MovingDirection.RIGHT) {
            randomMove = MovingDirection.LEFT;
        } else if (canMove(x + velocity, y) && randomMove != MovingDirection.LEFT) {
            randomMove = MovingDirection.RIGHT;
        }

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
        return result;
    }

    @Override
    public void update() {
        super.update();
        calculateMove();
    }
}
