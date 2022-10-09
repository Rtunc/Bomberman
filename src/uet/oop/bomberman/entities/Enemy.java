package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends SetAnimatedEntity {
    private AnimateAction actionState;

    public Enemy(int xUnit, int yUnit) {
        super(xUnit, yUnit, null);
        actionState = MovingDirection.STAND;
    }

    public AnimateAction getActionState() {
        return actionState;
    }

    public void setActionState(AnimateAction actionState) {
        this.actionState = actionState;
    }

    protected abstract void calculateMove();

    protected void move(double xA, double yA) {
        x += xA;
        y += yA;
    }

    public void checkBomber(Bomber bomber) {
        if (this.collision(bomber.getX(), bomber.getY())) {
            bomber.setBomberDead();
        }
    }
}
