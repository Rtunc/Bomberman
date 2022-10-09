package uet.oop.bomberman.entities;

public enum MovingDirection implements AnimateAction{
    UP, DOWN, LEFT, RIGHT, STAND;

    @Override
    public String getAction() {
        return name();
    }

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
}
