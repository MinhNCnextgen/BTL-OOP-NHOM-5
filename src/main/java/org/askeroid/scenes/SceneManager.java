package org.askeroid.scenes;

import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;

import org.askeroid.graphics.Renderer;
public class SceneManager {
    private GameScene currentScene;
    private final Map<String, GameScene> sceneMap;

    public SceneManager(Renderer renderer, Stage primaryStage) {
        sceneMap = new HashMap<>();
        sceneMap.put("menu", new MainMenu(renderer, this, primaryStage));
        sceneMap.put("play", new PlayScreen(renderer, this, primaryStage));
        sceneMap.put("end", new EndScreen(renderer, this, primaryStage));
    }

    public GameScene getCurrentScene() {
        return currentScene;
    }

    public void setScene(String sceneName, Stage stage) {
        if  (sceneMap.containsKey(sceneName)) {
            currentScene =  sceneMap.get(sceneName);
            stage.setScene(currentScene.scene);
            currentScene.onEnter();
        }
    }
}