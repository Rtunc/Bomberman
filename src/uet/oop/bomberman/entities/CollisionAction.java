package uet.oop.bomberman.entities;

public enum CollisionAction implements AnimateAction {
    EXPLOSION, DEAD;

    @Override
    public String getAction() {
        return name();
    }

    @Override
    public AnimateAction getNext(AnimateAction animateAction) {
        return DEAD;
    }
}
