package org.askeroid.scenes;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;

public class EndScreen extends GameScene{
    public Scene scene;

    public EndScreen(Renderer renderer, SceneManager sceneManager, Stage primaryStage) {
        super(renderer, sceneManager, primaryStage);
    }

    @Override
    public void update(double dt) {
        System.out.println("End Screen Updating...");
    }

    @Override
    public void renderScreen(Renderer renderer) {
    }

    @Override
    protected void bindInputs() {
    }
    @Override
    public void onEnter() {
    }
}
