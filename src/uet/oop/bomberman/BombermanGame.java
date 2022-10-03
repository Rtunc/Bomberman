package uet.oop.bomberman;



import java.lang.*;
import java.io.*;
import java.awt.*;
import com.sun.rowset.internal.Row;
import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sun.text.normalizer.UCharacter;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class BombermanGame extends Application {
    private static char[][] mapMatrix;
    public static int WIDTH;
    public static int HEIGHT;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();


    public static void main(String[] args) {

        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {

        createMapFromFile();
        Bomber bomberman = new Bomber(1, 1, Sprite.player_down.getFxImage());
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
                    case UP:   bomberman.goNorth = true; break;
                    case DOWN:  bomberman.goSouth = true; break;
                    case LEFT:  bomberman.goWest  = true; break;
                    case RIGHT: bomberman.goEast  = true; break;

                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:   bomberman.goNorth = false; break;
                    case DOWN:  bomberman.goSouth = false; break;
                    case LEFT:  bomberman.goWest  = false; break;
                    case RIGHT: bomberman.goEast  = false; break;

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
            @Override
            public void handle(long l) {
                render();
                update();
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
            System.out.println(firstLine);
            int level = 0;
            int row = 0;
            int column = 0;


            String[] tokens = firstLine.split("\\s");
            level = Integer.parseInt(tokens[0]);
            row = Integer.parseInt(tokens[1]);
            column = Integer.parseInt(tokens[2]);
            this.WIDTH = column;
            System.out.println(WIDTH);
            this.HEIGHT = row;
            mapMatrix = new char[row][column];
            for (int i = 0; i < row; i++) {
                String rowText = bufferedReader.readLine();
                System.out.println(rowText);
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
                        Entity object2 = new Oneal(j, i, Sprite.oneal_dead.getFxImage());
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
        public void update () {
            entities.forEach(Entity::update);

            stillObjects.forEach(Entity::update);

        }
        public void checkCollision () {
            for (int i = 0; i < stillObjects.size(); i++) {
                if (stillObjects.get(i) instanceof Wall) {

                }
            }
        }

        public void render () {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            stillObjects.forEach(g -> g.render(gc));
            entities.forEach(g -> g.render(gc));
        }
    }

