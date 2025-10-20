package org.askeroid.scenes;

import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;
import org.askeroid.objects.gameObjects.Ball;
import org.askeroid.objects.gameObjects.Bricks.*;
import org.askeroid.objects.gameObjects.Paddle;
import org.askeroid.objects.gameObjects.PowerUp.PUObject;
import org.askeroid.objects.gameObjects.PowerUp.PowerUpManager;
import org.askeroid.score.ScoreManager;
import org.askeroid.utils.LevelLoader;
import org.askeroid.utils.ScoreRanking;

import java.util.ArrayList;
import java.util.Random;

/**
 * Lớp {@code PlayScreen} đại diện cho màn hình chơi chính của game Askeroid.
 * <p>
 * Chịu trách nhiệm quản lý paddle, bóng, gạch, power-up, điểm số,
 * và xử lý toàn bộ logic cập nhật và hiển thị trong quá trình chơi.
 * </p>
 */
public class PlayScreen extends GameScene {

    private Paddle paddle;
    private Ball ball;
    private ScoreManager scoreManager;
    private PowerUpManager powerUpManager;
    private final ArrayList<String> levels;
    private int currentLevelIndex;

    /**
     * Khởi tạo màn hình chơi chính, tải danh sách level và thiết lập trạng thái ban đầu.
     *
     * @param renderer      Đối tượng {@link Renderer} dùng để vẽ.
     * @param sceneManager  Quản lý cảnh dùng để chuyển đổi giữa các màn hình.
     * @param primaryStage  Cửa sổ chính của ứng dụng.
     */
    public PlayScreen(Renderer renderer, SceneManager sceneManager, Stage primaryStage) {
        super(renderer, sceneManager, primaryStage);

        LevelLoader.LevelList list = LevelLoader.loadLevelList();
        if (list == null || list.levels == null || list.levels.isEmpty()) {
            System.err.println("Error: Could not load any levels!");
            levels = new ArrayList<>();
        } else {
            levels = list.levels;
        }

        bindInputs();
        initGame();
    }

    /**
     * Khởi tạo hoặc đặt lại toàn bộ trạng thái trò chơi về ban đầu.
     * Gọi khi bắt đầu game hoặc khi người chơi chơi lại.
     */
    private void initGame() {
        Brick.brickMap = new ArrayList<>();
        this.scoreManager = new ScoreManager(3);
        this.powerUpManager = new PowerUpManager(this);
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
     * Chơi lại toàn bộ màn chơi bằng cách khởi tạo lại toàn bộ trạng thái.
     * Được gọi khi người chơi chọn "Replay" ở màn hình kết thúc.
     */
    public void replayScene() {
        System.out.println("Replaying PlayScreen...");
        initGame();
    }

    /**
     * Cập nhật toàn bộ logic của màn chơi.
     *
     * @param dt Khoảng thời gian giữa hai khung hình (delta time).
     */
    @Override
    public void update(double dt) {
        updateObjects(dt);
        checkOffBounds();
        checkWinCondition();
    }

    /**
     * Cập nhật trạng thái của paddle, bóng, power-up, và điểm số.
     *
     * @param dt Khoảng thời gian giữa hai khung hình.
     */
    private void updateObjects(double dt) {
        paddle.update(keyboard, dt, canvas.getWidth());
        ball.update(dt, paddle, Brick.brickMap);

        PUObject.PUObjectList.removeIf(pu -> !pu.isActive);
        for (PUObject pu : PUObject.PUObjectList) {
            pu.update(dt, canvas, this);
        }

        powerUpManager.update();

        // Xử lý khi gạch bị phá hủy
        for (Brick brick : Brick.brickMap) {
            if (!brick.isActive() && !(brick instanceof PowerUpBrick)) {
                Random random = new Random(System.nanoTime());
                if (random.nextDouble() <= 0.1) {
                    PUObject.PUObjectList.add(new PUObject(brick.getX() + 10, brick.getY(), 40, 40, 450, null));
                }
            }
        }

        // Cộng điểm khi gạch bị phá
        if (Brick.brickMap.removeIf(brick -> !brick.isActive())) {
            double multiplier = scoreManager.getMultiplier();
            scoreManager.addScore(100, multiplier);
            scoreManager.increaseMultiplier(0.1);
        }
    }

    /**
     * Kiểm tra xem bóng có rơi ra ngoài màn hình không.
     * <p>Nếu rơi, trừ máu và reset bóng; nếu hết máu → sang màn hình kết thúc.</p>
     */
    private void checkOffBounds() {
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
    }

    /**
     * Kiểm tra điều kiện thắng: khi không còn gạch trên màn hình.
     * <p>Nếu thắng level -> sang level kế tiếp, nếu hết level -> thắng toàn game.</p>
     */
    private void checkWinCondition() {
        if (Brick.brickMap.isEmpty()) {
            currentLevelIndex++;
            if (currentLevelIndex >= levels.size()) {
                sceneManager.setScene("end", primaryStage);
                ScoreRanking.saveScoreToFile(scoreManager.getScore());
                ((EndScreen) sceneManager.getCurrentScene()).setGameResult(true, scoreManager.getScore());
            } else {
                loadNextLevel();
                ball.reset(paddle);
            }
        }
    }

    /**
     * Vẽ toàn bộ giao diện màn chơi: nền, HUD và các đối tượng game.
     *
     * @param renderer Đối tượng {@link Renderer} dùng để vẽ.
     */
    @Override
    public void renderScreen(Renderer renderer) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderer.drawBackground(gc, "images/background/play_background.jpg");
        renderHUD(renderer);
        renderObjects(renderer);
    }

    /**
     * Vẽ HUD (điểm, máu, hệ số nhân).
     *
     * @param renderer Đối tượng {@link Renderer} dùng để vẽ chữ.
     */
    private void renderHUD(Renderer renderer) {
        renderer.drawText(gc, "Score: " + scoreManager.getScore(), 10, 30, "Times New Roman", 24, javafx.scene.paint.Color.WHITE);
        renderer.drawText(gc, "Multiplier: " + String.format("%.1f", scoreManager.getMultiplier()) + "x",
                root.getWidth() - 170, 30, "Times New Roman", 24, javafx.scene.paint.Color.WHITE);
        renderer.drawText(gc, "Health: " + scoreManager.getHealth(),
                root.getWidth() / 2 - 60, 30, "Times New Roman", 24, javafx.scene.paint.Color.WHITE);
    }

    /**
     * Vẽ tất cả đối tượng game: paddle, bóng, gạch, hiệu ứng nổ, và power-up.
     *
     * @param renderer Đối tượng {@link Renderer} dùng để vẽ.
     */
    private void renderObjects(Renderer renderer) {
        renderer.drawPaddle(gc, paddle);
        renderer.drawBall(gc, ball);

        for (Brick brick : Brick.brickMap) {
            renderer.drawBrick(gc, brick);
        }

        ExplosionBrick.maskList.removeIf(mask -> (System.nanoTime() - mask.getTimeCreated()) > 1_000_000_000);
        for (ExplosionBrick.Mask mask : ExplosionBrick.maskList) {
            renderer.drawMask(gc, mask);
        }

        for (PUObject pu : PUObject.PUObjectList) {
            renderer.drawImage(gc, pu.getImagePowerUp(), pu.getX(), pu.getY(), pu.getWidth(), pu.getHeight());
        }
    }

    /**
     * Tải level kế tiếp dựa trên danh sách đã có.
     * Đọc dữ liệu layout từ file JSON và tạo gạch tương ứng.
     */
    private void loadNextLevel() {
        String levelName = levels.get(currentLevelIndex);
        LevelLoader.LevelData currentLevelData = LevelLoader.loadLevelData("levels_data/" + levelName);

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
                double y = row * brickHeight + 50;

                switch (cell) {
                    case "D" -> Brick.brickMap.add(new DefaultBrick(x, y, brickWidth, brickHeight));
                    case "H" -> Brick.brickMap.add(new HardBrick(x, y, brickWidth, brickHeight));
                    case "B" -> Brick.brickMap.add(new ExplosionBrick(x, y, brickWidth, brickHeight));
                    case "P" -> Brick.brickMap.add(new PowerUpBrick(x, y, brickWidth, brickHeight));
                    case "E" -> {} // empty
                    default -> System.err.println("Unknown brick type: " + cell);
                }
            }
        }
    }

    /**
     * Gán các phím điều khiển trong màn chơi.
     * Hiện tại: nhấn SPACE để phóng bóng.
     */
    @Override
    protected void bindInputs() {
        keyboard.setOnKeyPress(key -> {
            if (key == KeyCode.SPACE) {
                ball.launch();
            }
        });
    }

    /**
     * Được gọi khi người chơi vào lại màn hình chơi (từ menu hoặc replay).
     */
    @Override
    public void onEnter() {
        replayScene();
    }

    // ----------------------------------------------------------------------
    // Getter
    // ----------------------------------------------------------------------

    /**
     * @return Paddle hiện tại của người chơi.
     */
    public Paddle getPaddle() {
        return paddle;
    }

    /**
     * @return Bóng hiện tại trong trò chơi.
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * @return Bộ quản lý điểm số và máu.
     */
    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    /**
     * @return Bộ quản lý Power-Up hiện tại.
     */
    public PowerUpManager getPowerUpManager() {
        return powerUpManager;
    }
}
