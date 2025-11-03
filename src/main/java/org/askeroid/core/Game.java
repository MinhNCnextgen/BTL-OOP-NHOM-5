package org.askeroid.core;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

import org.askeroid.graphics.Renderer;
import org.askeroid.scenes.SceneManager;
import org.askeroid.input.Keyboard;

/**
 * Điểm vào của game.
 */
public class Game extends Application {
    private Renderer renderer;
    private SceneManager sceneManager;
    private Stage primaryStage;

    /** Tạo sân khấu chính và các renderer,scene manager của game
     * @param primaryStage Sân khấu chính của Javafx.
     * @throws Exception Lỗi không chạy được màn hình.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {;
        this.primaryStage = primaryStage;
        this.renderer = new Renderer(primaryStage);
        renderer.createWindow();
        this.sceneManager = new SceneManager(renderer, primaryStage);
        mainloop();
    }

    /** Vòng lặp chính của game, đảm nhận việc update và vẽ mỗi frame.
     *
     */
    private void mainloop() {
        sceneManager.setScene("menu", primaryStage);
        primaryStage.show();

        final long frameTime = 1_000_000_000L / 120; // 60 FPS target
        final long[] lastFrame = {0};

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastFrame[0] >= frameTime) {
                    sceneManager.getCurrentScene().update(now - lastFrame[0]);
                    sceneManager.getCurrentScene().renderScreen(renderer);
                    lastFrame[0] = now;
                }
            }
        };
        timer.start();
    }

    public void init(String[] args) {
        launch(args);
    }
}
