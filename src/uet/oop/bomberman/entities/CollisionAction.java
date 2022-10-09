package uet.oop.bomberman.entities;

/**
 * CollisionAction enum thể hiện hằng số hành động chết và nổ các entity
 */
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
