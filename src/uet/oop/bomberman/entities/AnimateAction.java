package uet.oop.bomberman.entities;

/**
 * AnimateAction trừu tượng các hành động có thể hiệu ứng hóa
 */
public interface AnimateAction {
    String getAction();

    AnimateAction getNext(AnimateAction animateAction);
}
