package org.askeroid.scenes;

import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;
import org.askeroid.input.Keyboard;

public abstract class GameScene {
    protected Scene scene;
    protected Pane root;
    protected Canvas canvas;
    protected GraphicsContext gc;
    protected final Renderer renderer;
    protected Keyboard keyboard;
    protected SceneManager sceneManager;
    protected Stage primaryStage;
    public GameScene(Renderer renderer, SceneManager sceneManager, Stage primaryStage) {
        this.renderer = renderer;
        this.root = new Pane();
        this.canvas = new Canvas();

        // Bind to root so canvas fills it
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());

        this.gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        this.scene = new Scene(root, 600, 800); // give it an initial size
        this.keyboard = new Keyboard(scene);
        this.sceneManager = sceneManager;
        this.primaryStage = primaryStage;
    }
    public Canvas getCanvas() {
        return canvas;
    }
    public GraphicsContext getGraphicsContext() {
        return gc;
    }
    public Scene getScene() {
        return scene;
    }
    public Renderer getRenderer() {
        return renderer;
    }
    public abstract void update(double dt);
    public abstract void renderScreen(Renderer renderer);
    protected abstract void bindInputs();
    public void onEnter() {}
    public void onExit() {}
}
