package org.askeroid.scenes;

import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;
import org.askeroid.graphics.Renderer;

/**
 * Quản lý các màn hình (scene) trong trò chơi.
 * Lớp này chịu trách nhiệm tạo, lưu trữ, chuyển đổi giữa
 * các màn hình như menu chính, màn chơi, màn kết thúc, bảng xếp hạng, v.v.
 */
public class SceneManager {

    private GameScene currentScene;
    private final Map<String, GameScene> sceneMap;

    /**
     * Khởi tạo SceneManager và tạo sẵn các màn hình của trò chơi.
     *
     * @param renderer     bộ vẽ dùng chung cho tất cả màn hình.
     * @param primaryStage cửa sổ chính của trò chơi.
     */
    public SceneManager(Renderer renderer, Stage primaryStage) {
        sceneMap = new HashMap<>();
        sceneMap.put("menu", new MainMenu(renderer, this, primaryStage));
        sceneMap.put("play", new PlayScreen(renderer, this, primaryStage));
        sceneMap.put("end", new EndScreen(renderer, this, primaryStage));
        sceneMap.put("ranking", new Ranking(renderer, this, primaryStage));
        currentScene = sceneMap.get("menu");
    }

    /**
     * Lấy màn hình hiện tại mà trò chơi đang hiển thị.
     *
     * @return màn hình hiện tại.
     */
    public GameScene getCurrentScene() {
        return currentScene;
    }

    /**
     * Lấy màn hình dựa theo tên.
     *
     * @param sceneName tên của màn hình cần lấy (ví dụ: "menu", "play").
     * @return đối tượng {@link GameScene} tương ứng, hoặc {@code null} nếu không tồn tại.
     */
    public GameScene getSceneByName(String sceneName) {
        return sceneMap.get(sceneName);
    }

    /**
     * Chuyển sang một màn hình khác dựa trên tên.
     * <p>
     * Phương thức này khởi tạo lại màn hình tương ứng để đảm bảo trạng thái mới,
     * thay thế màn hình cũ trong bản đồ và hiển thị nó trên cửa sổ chính.
     * </p>
     *
     * @param sceneName tên của màn hình cần chuyển đến.
     * @param stage     cửa sổ hiển thị trò chơi.
     */
    public void setScene(String sceneName, Stage stage) {
        if (sceneMap.containsKey(sceneName)) {
            switch (sceneName) {
                case "menu" -> replaceScene("menu", new MainMenu(currentScene.getRenderer(), this, stage));
                case "play" -> replaceScene("play", new PlayScreen(currentScene.getRenderer(), this, stage));
                case "end" -> replaceScene("end", new EndScreen(currentScene.getRenderer(), this, stage));
                case "ranking" -> replaceScene("ranking", new Ranking(currentScene.getRenderer(), this, stage));
                default -> {
                    return;
                }
            }

            currentScene = sceneMap.get(sceneName);
            stage.setScene(currentScene.scene);
            stage.sizeToScene();
            currentScene.onEnter();
        }
    }

    /**
     * Thay thế một màn hình cũ trong bản đồ bằng màn hình mới.
     *
     * @param name     tên của màn hình cần thay.
     * @param newScene màn hình mới để thay thế.
     */
    private void replaceScene(String name, GameScene newScene) {
        sceneMap.put(name, newScene);
    }
}
