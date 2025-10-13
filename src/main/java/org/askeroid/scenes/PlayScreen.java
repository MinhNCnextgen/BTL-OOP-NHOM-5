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
    private int lives = 1;

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

    private void initGame() {
        // reset paddle
        this.paddle = new Paddle(240, 600, 100, 20, 350);

        // reset ball
        this.ball = new Balls(
                paddle.getX() + paddle.getWidth() / 2 - 10,
                paddle.getY() - 20,
                10,
                500,
                45,
                root.getWidth(),
                root.getHeight()
        );

        // clear and recreate bricks
        if (this.brickMap == null) {
            this.brickMap = new ArrayList<>();
        } else {
            this.brickMap.clear();
        }

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 10; col++) {
                double brickX = 30 + col * (60 + 10);
                double brickY = 100 + row * (20 + 5);
                int hits = 1;
                Bricks brick = new Bricks(brickX, brickY, 60, 20, hits);
                brickMap.add(brick);
            }
        }

        // reset score và lives
        this.scoreManager = new ScoreManager();
        this.lives = 1;
    }

    @Override
    public void update(double dt) {
        paddle.update(keyboard, dt, canvas.getWidth());
        ball.update(dt, paddle, brickMap);

        if ( brickMap.removeIf(brick -> !brick.isActive()) ) {
            double multiplier = scoreManager.getMultiplier();
            scoreManager.addScore(100, multiplier);
            multiplier = scoreManager.increaseMultiplier(0.1);
        }
        if (ball.getY() > canvas.getHeight()) {
            System.out.println("Ball missed! Resetting position.");
            ball.reset(paddle);
            scoreManager.resetMultiplier();
        }
        System.out.println(scoreManager.getScore());
        System.out.println(scoreManager.getMultiplier());

        if (brickMap.isEmpty()) {
            EndScreen endScreen = (EndScreen) sceneManager.getSceneByName("end");
            endScreen.setGameResult(true, scoreManager.getScore());
            sceneManager.setScene("end", primaryStage);
        }

        if (ball.getY() > canvas.getHeight()) {
            lives--;
            if (lives <= 0) {
                EndScreen endScreen = (EndScreen) sceneManager.getSceneByName("end");
                endScreen.setGameResult(false, scoreManager.getScore());
                sceneManager.setScene("end", primaryStage);
            } else {
                ball.reset(paddle);
                scoreManager.resetMultiplier();
            }
        }
    }

    @Override
    public void renderScreen(Renderer renderer) {
        this.gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderer.drawBackground(gc, "images/background/play_background.jpg");
        renderer.drawPaddle(gc, paddle);
        renderer.drawBall(gc, ball);
        renderer.drawText(gc, "Score: " + scoreManager.getScore(), 10, 30, "Arial", javafx.scene.paint.Color.WHITE);
        renderer.drawText(gc, "Multiplier: " + String.format("%.1f", scoreManager.getMultiplier()) + "x", root.getWidth()-150, 30, "Arial", javafx.scene.paint.Color.WHITE);
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
    public void onEnter() {
        initGame();
    }

}
