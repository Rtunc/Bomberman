package uet.oop.bomberman;

import uet.oop.bomberman.graphics.Camera;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.bomb.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class BombermanGame extends Application {
    public static int WIDTH;
    private Camera camera;
    public static int HEIGHT;
    public Bomber bomberman;
    public static double fps;
    private static char[][] mapMatrix;
    private final List<Entity> entities = new ArrayList<>();
    public static final List<Bomb> bombs = new ArrayList<>();
    private final List<Entity> stillObjects = new ArrayList<>();
    private GraphicsContext gc;
    private Canvas canvas;

    public static void main(String[] args) {

        Application.launch(BombermanGame.class);
    }

    public static boolean isFree(int nextX, int nextY) {
        int size = Sprite.SCALED_SIZE;
        int nextX_1 = nextX / size;
        int nextY_1 = nextY / size;

        int nextX_2 = (nextX + size - 2) / size;
        int nextY_2 = nextY / size;

        int nextX_3 = nextX / size;
        int nextY_3 = (nextY + size - 2) / size;

        int nextX_4 = (nextX + size - 2) / size;
        int nextY_4 = (nextY + size - 2) / size;
        return !((mapMatrix[nextY_1][nextX_1] == '*' || mapMatrix[nextY_1][nextX_1] == '#') ||
                (mapMatrix[nextY_2][nextX_2] == '*' || mapMatrix[nextY_2][nextX_2] == '#') ||
                (mapMatrix[nextY_3][nextX_3] == '*' || mapMatrix[nextY_3][nextX_3] == '#') ||
                (mapMatrix[nextY_4][nextX_4] == '*' || mapMatrix[nextY_4][nextX_4] == '#'));

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
        stage.setHeight(13*32);
        stage.setWidth(13*32);
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
                char chatactor = mapMatrix[i][j];
                switch (chatactor) {
                    case '#': {
                        Wall object = new Wall(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        break;

                    }
                    case '*': {
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
//


                    default: {
                        Entity object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        break;

                    }
                }

            }
        }
    }

    public void restartGame() {

    }

    public void update() {
        entities.forEach(Entity::update);
        Iterator<Bomb> itB = bombs.iterator();

        while (itB.hasNext()) {
            Entity e = itB.next();
            if(e.isDead()) {
                itB.remove();
            }
        }
//      Tìm bomber
//      TODO: có cách nào tìm bomber nhanh hơn sửa vào đây
        Bomber bomber = null;
        for (Entity b :
                entities) {
            if (b instanceof Bomber) {
                bomber = (Bomber) b;
            }
        }

//      Check enemy
        for (Entity o :
                entities) {
            if (o instanceof Enemy) {
                ((Enemy) o).checkBomber(bomber);
            }
        }
        TranslateTransition t = new TranslateTransition(Duration.millis(0.1),canvas);
        t.setFromX(bomberman.getX());
        t.setToX(120-bomberman.getX());
        t.setFromY(bomberman.getY());
        t.setToY(120-bomberman.getY());

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

