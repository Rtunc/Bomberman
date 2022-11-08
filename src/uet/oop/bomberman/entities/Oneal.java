package uet.oop.bomberman.entities;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.util.*;

/**
 * Oneal chỉ còn va chạm với bomb, muốn thay đổi gì nhắn lại
 */
public class Oneal extends Enemy {
    MovingDirection randomMove = MovingDirection.LEFT;
    private final int velocity = 2;

    private static final int WIDE = 10;
    private static final int DIAG = 14;

    private class Node {
        public Node(int xUnit, int yUnit) {
            this.xUnit = xUnit;
            this.yUnit = yUnit;
        }

        public Node arrow;

        public int xUnit, yUnit;
        public int fCost, hCost, gCost;

        public void calculateCost(int xStart, int yStart, int xEnd, int yEnd) {
            int hxStart = xStart;
            int hyStart = yStart;
            int oldG = 0;
            if (arrow != null) {
                hxStart = arrow.xUnit;
                hyStart = arrow.yUnit;
                oldG = arrow.gCost;
            }
            this.hCost = Math.min(Math.abs(xEnd - xUnit), Math.abs(yEnd - yUnit))*DIAG
                    + Math.abs(Math.abs(xEnd - xUnit) - Math.abs(yEnd - yUnit))*WIDE;
            this.gCost = oldG + Math.min(Math.abs(hxStart - xUnit), Math.abs(hyStart - yUnit))*DIAG
                    + Math.abs(Math.abs(hxStart - xUnit) - Math.abs(hyStart - yUnit))*WIDE;
            this.fCost = this.hCost + this.gCost;
        }
    }

    public Oneal(int x, int y) {
        super(x, y);
        super.addFrame(Sprite.oneal_right1.getFxImage(), MovingDirection.STAND);

        super.addFrame(Sprite.oneal_left1.getFxImage(), MovingDirection.LEFT);
        super.addFrame(Sprite.oneal_left2.getFxImage(), MovingDirection.LEFT);
        super.addFrame(Sprite.oneal_left3.getFxImage(), MovingDirection.LEFT);

        super.addFrame(Sprite.oneal_right1.getFxImage(), MovingDirection.RIGHT);
        super.addFrame(Sprite.oneal_right2.getFxImage(), MovingDirection.RIGHT);
        super.addFrame(Sprite.oneal_right3.getFxImage(), MovingDirection.RIGHT);

        super.addFrame(Sprite.oneal_dead.getFxImage(), CollisionAction.DEAD);
        super.addFrame(Sprite.mob_dead1.getFxImage(), CollisionAction.DEAD);
        super.addFrame(Sprite.mob_dead2.getFxImage(), CollisionAction.DEAD);
        super.addFrame(Sprite.mob_dead3.getFxImage(), CollisionAction.DEAD);
        super.setCurrentState(MovingDirection.STAND);
    }

    private Node nextStep = null;
    public void buildTarget(Bomber bomber) {
        nextStep = null;

        if (isCorrecting || bomber.isDead()) {
            return;
        }

        PriorityQueue<Node> open = new PriorityQueue<>((c1, c2) -> {
            if (c1.fCost == c2.fCost) {
                return Integer.compare(c1.hCost, c2.hCost);
            } else {
                return Integer.compare(c1.fCost, c2.fCost);
            }
        });
        ArrayList<Node> closed = new ArrayList<>();
        int xEnd = bomber.getXUnit();
        int yEnd = bomber.getYUnit();
        int xStart = this.getXUnit();
        int yStart = this.getYUnit();
        Node start = new Node(xStart, yStart);
        start.calculateCost(xStart, yStart, xEnd, yEnd);
        open.add(start);

        while (true) {
            Node current = open.poll();
            closed.add(current);

            if (current == null) {
                return;
            }
            if (current.xUnit == xEnd && current.yUnit == yEnd) {
                break;
            }

            for (int i = -1; i <= 1 ; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (Math.abs(i) == Math.abs(j)) {
                        continue;
                    }
                    List<Entity> entities = BombermanGame.FindList(current.xUnit + j, current.yUnit + i, BombermanGame.stillObjects);
                    entities.addAll(BombermanGame.FindList(current.xUnit + j, current.yUnit + i, BombermanGame.bombs));
                    boolean blocked = false;
                    for (Entity e :
                            entities) {
                        if (e instanceof Wall || e instanceof Brick || e instanceof Bomb) {
                            blocked = true;
                            break;
                        }
                    }
                    for (Node n :
                            closed) {
                        if (n.xUnit == current.xUnit + j && n.yUnit == current.yUnit + i) {
                            blocked = true;
                            break;
                        }
                    }
                    if (blocked) {
                        continue;
                    }

                    Node neighbor = null;
                    for (Node n:
                         open) {
                        if (n.xUnit == current.xUnit + j && n.yUnit == current.yUnit + i) {
                            neighbor = n;
                            break;
                        }
                    }
                    if (neighbor == null) {
                        neighbor = new Node(current.xUnit + j, current.yUnit + i);
                        neighbor.arrow = current;
                        neighbor.calculateCost(xStart, yStart, xEnd, yEnd);
                        open.add(neighbor);
                    } else {
                        int oldF = neighbor.fCost;
                        int oldH = neighbor.hCost;
                        Node oldArr = neighbor.arrow;
                        neighbor.arrow = current;
                        neighbor.calculateCost(xStart, yStart, xEnd, yEnd);
                        if (oldF < neighbor.fCost) {
                            neighbor.fCost = oldF;
                            neighbor.hCost = oldH;
                            neighbor.arrow = oldArr;
                        }
                    }
                }
            }
        }

        Node end = closed.get(closed.size() - 1);
        if (end != null) {
            while (end.arrow != null && end.arrow != start) {
                end = end.arrow;
            }
        }
        nextStep = end;
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
                    }
                    break;
                case DOWN:
                    move(0, velocity);
                    if (this.y % Sprite.SCALED_SIZE == 0) {
                        isCorrecting = false;
                    }
                    break;
                case LEFT:
                    move(-velocity, 0);
                    if (this.x % Sprite.SCALED_SIZE == 0) {
                        isCorrecting = false;
                    }
                    break;
                case RIGHT:
                    move(velocity, 0);
                    if (this.x % Sprite.SCALED_SIZE == 0) {
                        isCorrecting = false;
                    }
                    break;
            }
            return;
        }

        if (nextStep == null) {
            switch (randomMove) {
                case UP:
                    if (canMove(x, y - velocity)) {
                        move(0, -velocity);
                        unitDirection = randomMove;
                        isCorrecting = true;
                    } else {
                        randomMove = (MovingDirection) randomMove.getNext(randomMove);
                        calculateMove();
                    }
                    break;
                case DOWN:
                    if (canMove(x, y + velocity)) {
                        move(0, +velocity);
                        unitDirection = randomMove;
                        isCorrecting = true;
                    } else {
                        randomMove = (MovingDirection) randomMove.getNext(randomMove);
                        calculateMove();
                    }
                    break;
                case LEFT:
                    if (canMove(x - velocity, y)) {
                        move(-velocity, 0);
                        super.setCurrentState(MovingDirection.LEFT);
                        unitDirection = randomMove;
                        isCorrecting = true;
                    } else {
                        randomMove = (MovingDirection) randomMove.getNext(randomMove);
                        calculateMove();
                    }
                    break;
                case RIGHT:
                    if (canMove(x + velocity, y)) {
                        move(+velocity, 0);
                        super.setCurrentState(MovingDirection.RIGHT);
                        unitDirection = randomMove;
                        isCorrecting = true;
                    } else {
                        randomMove = (MovingDirection) randomMove.getNext(randomMove);
                        calculateMove();
                    }
                    break;
                case STAND:
                    randomMove = MovingDirection.UP;
            }
        } else {
            switch (nextStep.xUnit - this.getXUnit()) {
                case -1:
                    unitDirection = MovingDirection.LEFT;
                    super.setCurrentState(unitDirection);
                    break;
                case 1:
                    unitDirection = MovingDirection.RIGHT;
                    super.setCurrentState(unitDirection);
                    break;
            }

            switch (nextStep.yUnit - this.getYUnit()) {
                case -1:
                    unitDirection = MovingDirection.UP;
                    break;
                case 1:
                    unitDirection = MovingDirection.DOWN;
                    break;
            }
            isCorrecting = true;
        }
    }

    private boolean canMove(int nextX, int nextY) {
        boolean result = BombermanGame.isFree(nextX, nextY);
        if (BombermanGame.getBomb(nextX, nextY) != null) {
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
