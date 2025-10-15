package org.askeroid.scenes;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;
import org.askeroid.objects.gameObjects.Ball;
import org.askeroid.objects.gameObjects.Bricks.Brick;
import org.askeroid.objects.gameObjects.Bricks.DefaultBrick;
import org.askeroid.objects.gameObjects.Bricks.ExplosionBrick;
import org.askeroid.objects.gameObjects.Bricks.HardBrick;
import org.askeroid.objects.gameObjects.Paddle;
import org.askeroid.score.ScoreManager;
import org.askeroid.utils.LevelLoader;
import org.askeroid.utils.ScoreRanking;

import java.util.ArrayList;

public class PlayScreen extends GameScene {
    private Paddle paddle;
    private Ball ball;
    private ScoreManager scoreManager;
    private ArrayList<String> levels;
    private LevelLoader.LevelData currentLevelData;
    private int currentLevelIndex;

    public PlayScreen(Renderer renderer, SceneManager sceneManager, Stage primaryStage) {
        super(renderer, sceneManager, primaryStage);

        // Load available levels (once)
        LevelLoader.LevelList list = LevelLoader.loadLevelList();
        if (list == null || list.levels == null || list.levels.isEmpty()) {
            System.err.println("Error: Could not load any levels!");
            levels = new ArrayList<>();
        } else {
            levels = list.levels;
        }

        bindInputs();
        initGame(); // initial game setup
    }

    /**
     * Initializes or resets the entire game state.
     */

    private void initGame() {
        Brick.brickMap = new ArrayList<>();
        this.scoreManager = new ScoreManager(3);
        this.currentLevelIndex = 0;
        this.paddle = new Paddle(240, 600, 100, 20, 350);
        this.ball = new Ball(
                paddle.getX() + paddle.getWidth() / 2 - 10,
                paddle.getY() - 20,
                10,
                500,
                45,
                root.getWidth(),
                root.getHeight()
        );

        if (!levels.isEmpty()) {
            loadNextLevel();
        } else {
            System.err.println("No levels available to load.");
        }
    }

    /**
     * Replays the current scene by resetting all state.
     * Called from EndScreen when clicking "Replay".
     */

    public void replayScene() {
        System.out.println("Replaying PlayScreen...");
        initGame();
    }

    @Override
    public void update(double dt) {
        paddle.update(keyboard, dt, canvas.getWidth());
        ball.update(dt, paddle, Brick.brickMap);

        // Handle destroyed bricks
        if (Brick.brickMap.removeIf(brick -> !brick.isActive())) {
            double multiplier = scoreManager.getMultiplier();
            scoreManager.addScore(100, multiplier);
            scoreManager.increaseMultiplier(0.1);
        }

        // Ball fell off the screen
        if (ball.getY() > canvas.getHeight()) {
            scoreManager.decreaseHealth(1);
            if (scoreManager.getHealth() <= 0) {
                sceneManager.setScene("end", primaryStage);
                ((EndScreen) sceneManager.getCurrentScene()).setGameResult(false, scoreManager.getScore());
                ScoreRanking.saveScoreToFile(scoreManager.getScore());
            } else {
                scoreManager.resetMultiplier();
                ball.reset(paddle);
            }
        }

        // Win condition
        if (Brick.brickMap.isEmpty()) {
            currentLevelIndex++;
            if (currentLevelIndex >= levels.size()) {
                sceneManager.setScene("end", primaryStage);
                ((EndScreen) sceneManager.getCurrentScene()).setGameResult(true, scoreManager.getScore());
            } else {
                loadNextLevel();
                ball.reset(paddle);
            }
        }
    }

    @Override
    public void renderScreen(Renderer renderer) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderer.drawBackground(gc, "images/background/play_background.jpg");
        renderer.drawPaddle(gc, paddle);
        renderer.drawBall(gc, ball);

        // HUD
        renderer.drawText(gc, "Score: " + scoreManager.getScore(), 10, 30, "Arial", javafx.scene.paint.Color.WHITE);
        renderer.drawText(gc, "Multiplier: " + String.format("%.1f", scoreManager.getMultiplier()) + "x",
                root.getWidth() - 150, 30, "Arial", javafx.scene.paint.Color.WHITE);
        renderer.drawText(gc, "Health: " + scoreManager.getHealth(),
                root.getWidth() / 2 - 50, 30, "Arial", javafx.scene.paint.Color.WHITE);
        // Bricks
        for (Brick brick : Brick.brickMap) {
            renderer.drawBrick(gc, brick);
        }

        // Explosion masks
        ExplosionBrick.maskList.removeIf(mask -> (System.nanoTime() - mask.getTimeCreated()) > 1_000_000_000);
        for (ExplosionBrick.Mask mask : ExplosionBrick.maskList) {
            renderer.drawMask(gc, mask);
        }
    }

    private void loadNextLevel() {
        String levelName = levels.get(currentLevelIndex);
        currentLevelData = LevelLoader.loadLevelData("levels_data/" + levelName);

        if (currentLevelData == null) {
            System.err.println("Failed to load level: " + levelName);
            return;
        }

        ArrayList<ArrayList<String>> grid = LevelLoader.parseLayout(currentLevelData.layout, 8, 12);
        double brickWidth = 50;
        double brickHeight = 20;

        Brick.brickMap.clear();

        for (int row = 0; row < grid.size(); row++) {
            for (int col = 0; col < grid.get(row).size(); col++) {
                String cell = grid.get(row).get(col);
                double x = col * brickWidth;
                double y = row * brickHeight + 50; // top margin

                switch (cell) {
                    case "D" -> Brick.brickMap.add(new DefaultBrick(x, y, brickWidth, brickHeight));
                    case "H" -> Brick.brickMap.add(new HardBrick(x, y, brickWidth, brickHeight));
                    case "B" -> Brick.brickMap.add(new ExplosionBrick(x, y, brickWidth, brickHeight));
                    case "E" -> {} // empty
                    default -> System.err.println("Unknown brick type: " + cell);
                }
            }
        }
    }

    @Override
    protected void bindInputs() {
        keyboard.setOnKeyPress(key -> {
            if (key == KeyCode.SPACE) {
                ball.launch();
            }
        });
    }

    @Override
    public void onEnter() {
        // Called when re-entering this scene
        replayScene();
    }
}
