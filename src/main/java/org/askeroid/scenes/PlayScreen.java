package org.askeroid.scenes;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;
import org.askeroid.objects.gameObjects.Balls;
import org.askeroid.objects.gameObjects.Paddle;
import org.askeroid.objects.gameObjects.Bricks.Bricks;
import org.askeroid.score.ScoreManager;

import java.util.ArrayList;

public class PlayScreen extends GameScene {
    public Scene scene;
    private Paddle paddle;
    private Balls ball;
//    private Bricks bricks;
    public ArrayList<Bricks> brickMap;
    private ScoreManager scoreManager = new ScoreManager();
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

        this.brickMap = new ArrayList<>();
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                double brickX = 30 + col * (60 + 10); // 10px gap
                double brickY = 100 + row * (20 + 5);  // 5px gap
                int hits = 1; // Vary hits between 1 and 3
                Bricks brick = new Bricks(brickX, brickY, 60, 20, hits);
                brickMap.add(brick);
            }
        }
        this.scoreManager = new ScoreManager();
        bindInputs();
    }

    @Override
    public void update(double dt) {
        paddle.update(keyboard, dt, canvas.getWidth());
        ball.update(dt, paddle, brickMap);

        if ( brickMap.removeIf(brick -> !brick.isActive()) ) {
            scoreManager.addScore(100); // Add score for each brick destroyed
            System.out.println(scoreManager.getScore());
        }
    }

    @Override
    public void renderScreen(Renderer renderer) {
        this.gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderer.drawBackground(gc, "images/background/play_background.jpg");
        renderer.drawPaddle(gc, paddle);
        renderer.drawBall(gc, ball);
        renderer.drawText(gc, "Score: " + scoreManager.getScore(), 10, 30, "Arial", javafx.scene.paint.Color.WHITE);
        for (Bricks brick : brickMap) {
            renderer.drawBrick(gc, brick);
        }
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
