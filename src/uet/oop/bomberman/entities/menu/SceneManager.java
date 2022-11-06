package uet.oop.bomberman.entities.menu;

import javafx.scene.Scene;

public abstract class SceneManager {
    protected Scene scene;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
