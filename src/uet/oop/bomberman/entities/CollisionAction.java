package uet.oop.bomberman.entities;

public enum CollisionAction implements AnimateAction {
    EXPLOSION;

    @Override
    public String getAction() {
        return name();
    }
}
