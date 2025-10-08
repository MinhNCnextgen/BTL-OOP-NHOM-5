package org.askeroid.scenes;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;
import org.askeroid.objects.gameObjects.Balls;
import org.askeroid.objects.gameObjects.Paddle;

public class PlayScreen extends GameScene {
    public Scene scene;
    private Paddle paddle;
    private Balls ball;
    public PlayScreen(Renderer renderer, SceneManager sceneManager, Stage primaryStage) {
        super(renderer, sceneManager, primaryStage);
        this.paddle = new Paddle(240, 600, 100, 20, 350);
        this.ball = new Balls(
                paddle.getX() + paddle.getWidth() / 2 - 10, // align middle
                paddle.getY() - 20, // on top of paddle
                10,  // radius
                500, // speed
                45,  // initial angle
                root.getWidth(),
                root.getHeight()
        );
        bindInputs();
    }

    @Override
    public void update(double dt) {
        paddle.update(keyboard, dt, canvas.getWidth());
        ball.update(dt, paddle);
    }

    @Override
    public void renderScreen(Renderer renderer) {
        this.gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderer.drawBackground(gc, "images/background/play_background.jpg");
        renderer.drawPaddle(gc, paddle);
        renderer.drawBall(gc, ball);
    }

    @Override
    protected void bindInputs() {
        keyboard.setOnKeyPress(key -> {
            if (key == KeyCode.SPACE) {
                ball.launch(); // launch ball when space is pressed
            }
        });
    }

    @Override
    public void onEnter() {}

}
