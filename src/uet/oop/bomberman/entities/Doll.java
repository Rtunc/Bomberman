package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {
    private final int velocity = 2 * Sprite.SCALED;

    public Doll(int xUnit, int yUnit) {
        super(xUnit, yUnit);
        super.addFrame(Sprite.doll_right1.getFxImage(), MovingDirection.STAND);

        super.addFrame(Sprite.doll_left1.getFxImage(), MovingDirection.LEFT);
        super.addFrame(Sprite.doll_left2.getFxImage(), MovingDirection.LEFT);
        super.addFrame(Sprite.doll_left3.getFxImage(), MovingDirection.LEFT);

        super.addFrame(Sprite.doll_right1.getFxImage(), MovingDirection.RIGHT);
        super.addFrame(Sprite.doll_right2.getFxImage(), MovingDirection.RIGHT);
        super.addFrame(Sprite.doll_right3.getFxImage(), MovingDirection.RIGHT);

        super.addFrame(Sprite.doll_dead.getFxImage(), CollisionAction.DEAD);
        super.addFrame(Sprite.mob_dead1.getFxImage(), CollisionAction.DEAD);
        super.addFrame(Sprite.mob_dead2.getFxImage(), CollisionAction.DEAD);
        super.addFrame(Sprite.mob_dead3.getFxImage(), CollisionAction.DEAD);

        super.setCurrentState(MovingDirection.STAND);
        unitDirection = MovingDirection.RIGHT;

        point = 200;
    }

    @Override
    protected void calculateMove() {
        if (this.isDead) {
            return;
        }

        if (isCorrecting) {
            switch (unitDirection) {
                case UP:
                    move(0, -velocity);
                    if (this.y % Sprite.SCALED_SIZE == 0) {
                        isCorrecting = false;
                        unitDirection = unitDirection.randomMove();
                    }
                    break;
                case DOWN:
                    move(0, velocity);
                    if (this.y % Sprite.SCALED_SIZE == 0) {
                        isCorrecting = false;
                        unitDirection = unitDirection.randomMove();
                    }
                    break;
                case LEFT:
                    move(-velocity, 0);
                    if (this.x % Sprite.SCALED_SIZE == 0) {
                        isCorrecting = false;
                        unitDirection = unitDirection.randomMove();
                    }
                    break;
                case RIGHT:
                    move(velocity, 0);
                    if (this.x % Sprite.SCALED_SIZE == 0) {
                        isCorrecting = false;
                        unitDirection = unitDirection.randomMove();
                    }
                    break;
            }
            return;
        }
        switch (unitDirection) {
            case UP:
                if (canMove(convertUnit(this.x), convertUnit(this.y) - 1)) {
                    isCorrecting = true;
                } else {
                    unitDirection = (MovingDirection) unitDirection.getNext(unitDirection);
                    calculateMove();
                }
                break;
            case DOWN:
                if (canMove(convertUnit(this.x), convertUnit(this.y) + 1)) {
                    isCorrecting = true;
                } else {
                    unitDirection = (MovingDirection) unitDirection.getNext(unitDirection);
                    calculateMove();
                }
                break;
            case LEFT:
                if (canMove(convertUnit(this.x) - 1, (convertUnit(this.y)))) {
                    super.setCurrentState(unitDirection);
                    isCorrecting = true;
                } else {
                    unitDirection = (MovingDirection) unitDirection.getNext(unitDirection);
                    calculateMove();
                }
                break;
            case RIGHT:
                if (canMove(convertUnit(this.x) + 1, convertUnit(this.y))) {
                    super.setCurrentState(unitDirection);
                    isCorrecting = true;
                } else {
                    unitDirection = (MovingDirection) unitDirection.getNext(unitDirection);
                    calculateMove();
                }
                break;
            default:
                unitDirection = unitDirection.randomMove();
        }
    }

    private boolean canMove(int nextX, int nextY) {
        boolean result = true;
        for (Entity e :
                BombermanGame.FindList(nextX, nextY, BombermanGame.stillObjects)) {
            if (e instanceof Wall) {
                result = false;
                break;
            }
        }
        if (BombermanGame.getBomb(convertAbsolute(nextX), convertAbsolute(nextY)) != null) {
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
