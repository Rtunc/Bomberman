package uet.oop.bomberman.entities;

public enum MovingDirection implements AnimateAction{
    UP, DOWN, LEFT, RIGHT, STAND;

    @Override
    public String getAction() {
        return name();
    }
}
