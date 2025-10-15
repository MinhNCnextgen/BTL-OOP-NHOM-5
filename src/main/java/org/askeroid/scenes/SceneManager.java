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
        sceneMap.put("ranking", new Ranking(renderer, this, primaryStage));
        currentScene = sceneMap.get("menu");
    }

    public GameScene getCurrentScene() {
        return currentScene;
    }

    public GameScene getSceneByName(String sceneName) {
        return sceneMap.get(sceneName);
    }

    public void setScene(String sceneName, Stage stage) {
        if  (sceneMap.containsKey(sceneName)) {
            switch (sceneName) {
                case "menu" -> replaceScene("menu", new MainMenu(currentScene.getRenderer(), this, stage));
                case "play" -> replaceScene("play", new PlayScreen(currentScene.getRenderer(), this, stage));
                case "end" -> replaceScene("end", new EndScreen(currentScene.getRenderer(), this, stage));
                case "ranking" -> replaceScene("ranking", new Ranking(currentScene.getRenderer(), this, stage));
                default -> {}
            }
            currentScene =  sceneMap.get(sceneName);
            stage.setScene(currentScene.scene);
            stage.sizeToScene();
            currentScene.onEnter();
        }
    }
    private void replaceScene(String name, GameScene newScene) {
        sceneMap.put(name, newScene);
    }
}