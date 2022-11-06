package uet.oop.bomberman.entities;

/**
 * MovingDirection là các hướng đi của thực thể
 */
public enum MovingDirection implements AnimateAction {
    UP, DOWN, LEFT, RIGHT, STAND;

    @Override
    public String getAction() {
        return name();
    }

    /**
     * getNext() của MovingDirection trả về hướng tiếp theo trong U, D, L, R
     * <p>
     * Vd: hướng nhập vào: UP -> hướng tiếp theo: DOWN
     *
     * @param animateAction hướng nhập vào
     * @return hướng tiếp theo
     */
    @Override
    public AnimateAction getNext(AnimateAction animateAction) {
        switch ((MovingDirection) animateAction) {
            case UP:
                return DOWN;
            case DOWN:
                return LEFT;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return UP;
        }
        return STAND;
    }

    public MovingDirection randomMove() {
        return (MovingDirection) getByNum((int) (Math.random()*10));
    }

    @Override
    public AnimateAction getByNum(int num) {
        num %= 4;
        switch (num) {
            case 0: return UP;
            case 1: return DOWN;
            case 2: return LEFT;
            case 3: return RIGHT;
        }
        return STAND;
    }
}
