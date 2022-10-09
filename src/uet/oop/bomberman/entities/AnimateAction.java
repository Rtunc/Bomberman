package uet.oop.bomberman.entities;

public interface AnimateAction {
    String getAction();
    AnimateAction getNext(AnimateAction animateAction);
}
