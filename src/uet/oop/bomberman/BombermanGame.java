package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.items.AddBomb;
import uet.oop.bomberman.entities.items.AddFlame;
import uet.oop.bomberman.entities.items.PowerUps;
import uet.oop.bomberman.entities.items.SpeedUp;
import uet.oop.bomberman.entities.menu.*;
import uet.oop.bomberman.graphics.Camera;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.util.*;


public class BombermanGame extends Application {
    public static final Map<SceneState, SceneManager> gameScene = new HashMap<>();
    public static final List<Entity> entities = new ArrayList<>();
    public static final List<Enemy> enemies = new ArrayList<>();
    public static final List<Entity> bombs = new ArrayList<>();
    public static final List<Entity> stillObjects = new ArrayList<>();
    public static SceneState state = SceneState.MENU;
    public static int WIDTH;
    public static int HEIGHT;
    public static double fps;

    public static int currentLevel;

    public static int MAX_LEVEL = 2;
    private Image hp_i = new Image(new FileInputStream("res/textures/Bar_i.png"), 153, 0, true, true);
    private ImageView imageView_i = new ImageView();
    private Image hp_o = new Image(new FileInputStream("res/textures/Em.png"),200, 0, true, true);
    private ImageView imageView_o = new ImageView();
    private Text textStage = new Text();
    private double barX=0, barY=0;

    public BombermanGame() throws FileNotFoundException {
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static int setCurrentLevel(int currentLevel) {
        if (currentLevel > MAX_LEVEL) {
            BombermanGame.currentLevel = 1;
            return BombermanGame.currentLevel;
        }
        BombermanGame.currentLevel = currentLevel;
        return currentLevel;
    }

    public static boolean victory = false;
    public static boolean gameOver = false;
    public static Bomber bomberman;
    private static char[][] mapMatrix;
    private static Camera camera;
    private GraphicsContext gc;
    private Canvas canvas;
    private Rectangle rectangle = new Rectangle(0,0,820,50);;

    public static void initList(Pane parent) {
        gameScene.put(SceneState.PAUSE, new PauseScene());
        gameScene.put(SceneState.PLAYING, new GameScene(parent).addHandler(bomberman));
        gameScene.put(SceneState.GAMEOVER, GameOverScene.GAMEOVERSCENE);
        gameScene.put(SceneState.NEXTSTAGE, NextStage.NEXTSTAGE);
        gameScene.put(SceneState.CREDIT, Credit.CREDITSCENE);
        gameScene.put(SceneState.MENU, new Menu());
        gameScene.put(SceneState.HIGHSCORE, HighScore.HIGHSCOREPANEL);
        gameScene.put(SceneState.NAMEINPUT, NameInput.NAMEINPUTSCENE);
    }

    public static void continueGame() {
        Pair<Pair<String, Integer>, Integer> save = Highscore.getInstance().returnSave();
        if (save == null) {
            restartGame(1);
            bomberman.resetPoint();
            saveGame();
            return;
        }
        int level = save.getValue();
        restartGame(level);
        bomberman.setName(save.getKey().getKey());
        bomberman.increasePoint(save.getKey().getValue());
        saveGame();
    }

    public static void saveGame() {
        Highscore.getInstance().addSave(bomberman.getName(), bomberman.getPoint(), currentLevel);
    }

    public static void restartGame(int level) {
        entities.clear();
        stillObjects.clear();
        enemies.clear();
        bomberman = null;
        mapMatrix = null;
        createMapFromFile(String.format("res/levels/Level%d.txt", level));
        createEntities();
        camera = new Camera(bomberman);
        victory = gameOver = false;
        ((GameScene) gameScene.get(SceneState.PLAYING)).addHandler(bomberman);
    }

    public static void switchState(SceneState state) {
        BombermanGame.state = state;
    }

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

    public static Entity FindEntity(int x, int y, List<Entity> CheckList, Entity type) {
        for (Entity check : CheckList) {
            if (check.getXUnit() == x && check.getYUnit() == y)
                if (check.getClass().equals(type.getClass())) {
                    return check;
                }
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

    public static void createMapFromFile(String fileName) {
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
            currentLevel = level;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createEntities() {
        for (int i = 0; i < HEIGHT; i++) {

            for (int j = 0; j < WIDTH; j++) {
                char character = mapMatrix[i][j];
                switch (character) {
                    case '#': {
                        Wall object = new Wall(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        break;
                    }
                    case 'p': {
                        Grass object2 = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object2);
                        bomberman = new Bomber(j, i);
                        entities.add(bomberman);
                        break;
                    }
                    case 'x': {
                        Grass object2 = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object2);
                        Portal portal = new Portal(j, i);
                        stillObjects.add(portal);
                        Brick object3 = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object3);
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
                        Enemy object1 = new Balloom(j, i);
                        entities.add(object1);
                        enemies.add(object1);
                        break;
                    }
                    case '2': {
                        Entity object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        Enemy object2 = new Oneal(j, i);
                        entities.add(object2);
                        enemies.add(object2);
                        break;
                    }
                    case '3': {
                        Entity object = new Grass(j, i, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        Enemy object2 = new Doll(j, i);
                        entities.add(object2);
                        enemies.add(object2);
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

    @Override
    public void start(Stage stage) {
        BorderPane border = new BorderPane();
        createMapFromFile("res/levels/Level1.txt");
        createEntities();
        camera = new Camera(bomberman);
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        imageView_i.setX(36.2);
        imageView_i.setY(17.67);
        imageView_i.setImage(hp_i);
        imageView_i.setFitWidth(153);
        imageView_o.setImage(hp_o);
        textStage.setText("STAGE " + currentLevel);
        textStage.setFont(Font.font("Courier New", FontWeight.BOLD, 24 * Sprite.SCALED));
        textStage.setFill(Color.WHITE);
        textStage.setX(Sprite.SCALED_SIZE * 6 - textStage.getLayoutBounds().getWidth()/2);
        textStage.setY(50 * Sprite.SCALED);

        // Tao root container
        Pane root = new Pane();
        root.getChildren().add(canvas);
        root.getChildren().add(imageView_i);
        root.getChildren().add(imageView_o);
        root.getChildren().add(textStage);
        initList(root);
        gc = canvas.getGraphicsContext2D();
//
//        Scene.setOnKeyPressed(event -> {
//        KeyCode keycode = keyEvent.getCode();
//        })
        // Them scene vao stage
        stage.setScene(gameScene.get(state).getScene());
        stage.setHeight(13 * Sprite.SCALED_SIZE);
        stage.setWidth(13 * Sprite.SCALED_SIZE);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            long lastUpdate = 0;

            @Override
            public void handle(long l) {
                render();
                SceneManager thisState = gameScene.get(state);
                if (thisState instanceof GameScene) {
//                    ((GameScene) thisState).addHandler(bomberman);
                }
                stage.setScene(thisState.getScene());
                update(root);
                if (lastUpdate > 0) {
                    fps = (double) 1 / ((l - lastUpdate) * 1e-9);
                }
                lastUpdate = l;
            }
        };
        timer.start();
    }


    public void update(Pane root) {
        if (bomberman.isDead()){
            System.out.println("ok");
            root.getChildren().remove(imageView_i);
            root.getChildren().remove(imageView_o);
            imageView_i.setX(36.2);
            imageView_i.setY(17.67);
            imageView_i.setFitWidth(imageView_i.getFitWidth() - 76.5/120.0);
            root.getChildren().add(imageView_i);
            root.getChildren().add(imageView_o);
        }
        if (BombermanGame.gameOver && state == SceneState.PLAYING) {
            NameInput.NAMEINPUTSCENE.update();
            BombermanGame.switchState(SceneState.NAMEINPUT);
        }
        entities.forEach(Entity::update);
//        System.out.println(bomberman.getX());
        Iterator<Entity> itB = bombs.iterator();
        Iterator<Entity> itS = stillObjects.iterator();
        enemies.removeIf(Entity::isDead);

        while (itB.hasNext()) {
            Entity e = itB.next();
            if (e.isRemove()) {
                itB.remove();
                bomberman.setNumberOfBombs(bomberman.getNumberOfBombs() + 1);
            }
        }

        while (itS.hasNext()) {
            Entity e = itS.next();
            if (e instanceof PowerUps) {
                ((PowerUps) e).checkBomber(bomberman);
//                System.out.println(e.getX());
            }
            if (e.isRemove()) {
                itS.remove();
            }

        }

//      Check enemy
        for (Entity o : entities) {
            if (o instanceof Enemy) {
                ((Enemy) o).checkBomber(bomberman);
                if (o instanceof Oneal) {
                    ((Oneal) o).buildTarget(bomberman);
                }
            }
        }




        TranslateTransition t = new TranslateTransition(Duration.millis(0.1), canvas);
//        System.out.println(bomberman.getX());
        if (bomberman.getX() < 400) {
            t.setToX(0);
            rectangle.setX(0);
        } else if (bomberman.getX() > 1564) {
            t.setToX(-1167);
            rectangle.setX(1164);
        } else {
            t.setToX(400 - bomberman.getX());
            rectangle.setX(bomberman.getX()-400);
        }

        if (bomberman.getY() < 169) {
            t.setToY(0);
            rectangle.setY(0);
        } else if (bomberman.getY() > 208) {
            t.setToY(-37);
            rectangle.setY(37);
        } else {
            t.setToY(13 * 13 - bomberman.getY());
            rectangle.setY(bomberman.getY()-169);
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

