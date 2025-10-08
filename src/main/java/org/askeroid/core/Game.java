package org.askeroid.core;
import javafx.application.Application;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;

public class GameLoop extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Renderer gameRenderer = new Renderer(primaryStage);
        primaryStage.show();
    }

    public void init(String[] args) {
        launch(args);
    }
}
