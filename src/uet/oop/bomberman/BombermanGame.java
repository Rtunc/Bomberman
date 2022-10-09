package uet.oop.bomberman;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


public class BombermanGame extends Application {
    public static int WIDTH;
    public static int HEIGHT;
    public static double fps;
    private static char[][] mapMatrix;
    private final List<Entity> entities = new ArrayList<>();
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

        createMapFromFile();
        Bomber bomberman = new Bomber(1, 1);
        entities.add(bomberman);
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
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
//        scene.setOnKeyPressed(KeyEvent -> {
//            KeyCode keyCode = KeyEvent.getCode();
//            switch (keyCode) {
//                case KP_RIGHT: {
//                    bomberman.status = Bomber.StatusDirection.RIGHT;
//                    System.out.println(bomberman.status);
//                    break;
//                }
//                case LEFT: {
//                    bomberman.status = Bomber.StatusDirection.LEFT;
//                    break;
//                }
//
//                case UP: {
//                    bomberman.status = Bomber.StatusDirection.UP;
//                    break;
//                }
//
//                case DOWN: {
//                    bomberman.status = Bomber.StatusDirection.DOWN;
//                    break;
//                }
//                case SPACE: {
//                    bomberman.status = Bomber.StatusDirection.STOP;
//                    System.out.println(bomberman.status);
//                    break;
//                }
//
//
//
//            }
//        });
//        Scene.setOnKeyPressed(event -> {
//        KeyCode keycode = keyEvent.getCode();
//        })
        // Them scene vao stage
        stage.setScene(scene);
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

    public void createMapFromFile() {
        BufferedReader bufferedReader = null;

        try {
            Reader reader = new FileReader("res/levels/Level1.txt");
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
                        Entity object1 = new Balloon(j, i, Sprite.balloom_left2.getFxImage());
                        entities.add(object);
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

    public void update() {
        entities.forEach(Entity::update);

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

        stillObjects.forEach(Entity::update);

    }

    public void checkCollision() {
        for (int i = 0; i < stillObjects.size(); i++) {
            if (stillObjects.get(i) instanceof Wall) {

            }
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}

