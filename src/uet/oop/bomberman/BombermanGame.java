package uet.oop.bomberman;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.AudioClip;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.items.AddBomb;
import uet.oop.bomberman.entities.items.AddFlame;
import uet.oop.bomberman.entities.items.PowerUps;
import uet.oop.bomberman.entities.items.SpeedUp;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BombermanGame extends Application {

    public static final List<Entity> entities = new ArrayList<>();
    public static final List<Bomb> bombs = new ArrayList<>();
    public static final List<Entity> stillObjects = new ArrayList<>();
    public static int WIDTH;
    public static int HEIGHT;
    public static double fps;
    public static boolean victory = false;
    public static boolean gameOver = false;
    private static char[][] mapMatrix;
    public Bomber bomberman;
    private Camera camera;
    private GraphicsContext gc;
    private Canvas canvas;

    public static void main(String[] args) {

        Application.launch(BombermanGame.class);
    }

    public static boolean isFree(int nextX, int nextY) {
        int size = Sprite.SCALED_SIZE;
        int nextX_1 = (nextX + 1) / size;
        int nextY_1 = (nextY + 1) / size;

        int nextX_2 = (nextX + size - 1) / size;
        int nextY_2 = (nextY + 1) / size;

        int nextX_3 = (nextX + 1) / size;
        int nextY_3 = (nextY + size - 1) / size;

        int nextX_4 = (nextX + size - 1) / size;
        int nextY_4 = (nextY + size - 1) / size;

        List<Entity> ListStillObject1 = BombermanGame.FindList(nextX_1, nextY_1, BombermanGame.stillObjects);
        List<Entity> ListStillObject2 = BombermanGame.FindList(nextX_2, nextY_2, BombermanGame.stillObjects);
        List<Entity> ListStillObject3 = BombermanGame.FindList(nextX_3, nextY_3, BombermanGame.stillObjects);
        List<Entity> ListStillObject4 = BombermanGame.FindList(nextX_4, nextY_4, BombermanGame.stillObjects);

        for (Entity check : ListStillObject1) {
            if (check instanceof Wall || check instanceof Brick) {
                return false;
            }
        }
        for (Entity check : ListStillObject2) {
            if (check instanceof Wall || check instanceof Brick) {
                return false;
            }
        }
        for (Entity check : ListStillObject3) {
            if (check instanceof Wall || check instanceof Brick) {
                return false;
            }
        }
        for (Entity check : ListStillObject4) {
            if (check instanceof Wall || check instanceof Brick) {
                return false;
            }
        }
        return true;
    }

    public static Entity getEntity(double x, double y) {
        Iterator<Entity> bs = entities.iterator();
        Entity b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.getX() == (int) x && b.getY() == (int) y)
                return b;
        }
        return null;
    }

    public static Entity getBomb(double x, double y) {
        return bombs.stream().filter(b -> b.collision((int) x, (int) y)).findFirst().orElse(null);
    }

    public static Entity FindEntity(int x, int y, List<Entity> CheckList) {

        for (Entity check : CheckList) {
            if (check.getXUnit() == x && check.getYUnit() == y)
                return check;
        }
        return null;
    }

    public static List<Entity> FindList(int x, int y, List<Entity> CheckList) {
        List<Entity> ResultList = new ArrayList<>();
        for (Entity check : CheckList) {
            if (check.getXUnit() == x && check.getYUnit() == y)
                ResultList.add(check);
        }
        return ResultList;
    }

    @Override
    public void start(Stage stage) {
        BorderPane border = new BorderPane();
        createMapFromFile("res/levels/Level1.txt");
        bomberman = new Bomber(1, 1);
        camera = new Camera(bomberman);


        entities.add(bomberman);
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Pane root = new Pane();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        bomberman.setInputDirection(MovingDirection.UP);
                        bomberman.increaseVelocity();
                        break;
                    case DOWN:
                        bomberman.setInputDirection(MovingDirection.DOWN);
                        bomberman.increaseVelocity();
                        break;
                    case LEFT:
                        bomberman.setInputDirection(MovingDirection.LEFT);
                        bomberman.increaseVelocity();
                        break;
                    case RIGHT:
                        bomberman.setInputDirection(MovingDirection.RIGHT);
                        bomberman.increaseVelocity();
                        break;
                    case SPACE:
                        bomberman.placeBomb();
                        break;

                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                    case RIGHT:
                    case DOWN:
                    case LEFT:
                        bomberman.setInputDirection(null);
                        bomberman.resetVelocity();
                        break;

                }
            }
        });
//
//        Scene.setOnKeyPressed(event -> {
//        KeyCode keycode = keyEvent.getCode();
//        })
        // Them scene vao stage
        stage.setScene(scene);
        stage.setHeight(13 * 32);
        stage.setWidth(13 * 32);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            long lastUpdate = 0;

            @Override
            public void handle(long l) {
                render();
                update();
                if (lastUpdate > 0) {
                    fps = (double) 1 / ((l - lastUpdate) * 1e-9);
                }
                lastUpdate = l;
            }
        };
        timer.start();
        createEntities();
    }

    public void createMapFromFile(String fileName) {
        BufferedReader bufferedReader = null;

        try {
            Reader reader = new FileReader(fileName);
            bufferedReader = new BufferedReader(reader);
            String firstLine = bufferedReader.readLine();
            int level = 0;
            int row = 0;
            int column = 0;


            String[] tokens = firstLine.split("\\s");
            level = Integer.parseInt(tokens[0]);
            row = Integer.parseInt(tokens[1]);
            column = Integer.parseInt(tokens[2]);
            WIDTH = column;
            HEIGHT = row;
            mapMatrix = new char[row][column];
            for (int i = 0; i < row; i++) {
                String rowText = bufferedReader.readLine();
                for (int j = 0; j < column; j++) {
                    char x = rowText.charAt(j);
                    mapMatrix[i][j] = x;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createEntities() {
        for (int i = 0; i < HEIGHT; i++) {

            for (int j = 0; j < WIDTH; j++) {
                char character = mapMatrix[i][j];
                switch (character) {
                    case '#': {
                        Wall object = new Wall(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        break;

                    }
                    case '*': {
                        Entity object2 = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object2);
                        Brick object = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object);

                        break;

                    }
                    case '1': {
                        Entity object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        Entity object1 = new Balloom(j, i);
                        entities.add(object1);
                        break;
                    }
                    case '2': {
                        Entity object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        Entity object2 = new Oneal(j, i);
                        entities.add(object2);
                        break;
                    }
                    case 'b': {
                        Grass object3 = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object3);
                        AddBomb object = new AddBomb(j, i);
                        stillObjects.add(object);
                        Brick object2 = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object2);
                        break;
                    }
                    case 's': {
                        Grass object2 = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object2);
                        SpeedUp object = new SpeedUp(j, i);
                        stillObjects.add(object);
                        Brick object3 = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object3);
                        break;
                    }
                    case 'f': {
                        Grass object2 = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object2);
                        AddFlame object = new AddFlame(j, i);
                        stillObjects.add(object);
                        Brick object3 = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object3);
                        break;
                    }
                    default: {
                        Entity object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        break;

                    }
                }

            }
        }
    }

    public void update() {
        if (victory || gameOver) {
            Platform.exit();
        }
        entities.forEach(Entity::update);
        Iterator<Bomb> itB = bombs.iterator();
        Iterator<Entity> itE = entities.iterator();
        Iterator<Entity> itS = stillObjects.iterator();

        while (itB.hasNext()) {
            Entity e = itB.next();
            if (e.isRemove()) {
                itB.remove();
                bomberman.setNumberOfBombs(bomberman.getNumberOfBombs() + 1);
            }
        }
        while (itE.hasNext()) {
            Entity e = itE.next();
            if (e.isRemove()) {
                itE.remove();
            }
        }

        while (itS.hasNext()) {
            Entity e = itS.next();
            if (e instanceof PowerUps) {
                ((PowerUps) e).checkBomber(bomberman);
            }
            if (e.isRemove()) {
                itS.remove();
            }

        }

//      Check enemy
        for (Entity o :
                entities) {
            if (o instanceof Enemy) {
                ((Enemy) o).checkBomber(bomberman);
            }
        }

        TranslateTransition t = new TranslateTransition(Duration.millis(0.1), canvas);
        if (bomberman.getX() < 208) {
            t.setToX(0);
        } else if (bomberman.getX() > 800) {
            t.setToX(-592);
        } else {
            t.setToX(16 * 13 - bomberman.getX());
        }

        if (bomberman.getY() < 169) {
            t.setToY(0);
        } else if (bomberman.getY() > 208) {
            t.setToY(-37);
        } else {
            t.setToY(13 * 13 - bomberman.getY());
        }

        t.setInterpolator(Interpolator.LINEAR);
        t.play();
        stillObjects.forEach(Entity::update);
        bombs.forEach(Entity::update);


    }


    public void render() {

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}

