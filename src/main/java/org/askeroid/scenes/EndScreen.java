package org.askeroid.scenes;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;
import org.askeroid.objects.buttons.GameButton;
import org.askeroid.sound.SoundManager;

import java.util.HashMap;

/**
 * Màn hình kết thúc trò chơi (EndScreen).
 * Hiển thị thông báo kết quả (thắng hoặc thua),
 * điểm số cuối cùng và các nút điều hướng như "Chơi lại" hoặc "Về menu chính".
 */
public class EndScreen extends GameScene {

    private final HashMap<String, GameButton> buttons;
    private String resultMessage;
    private int finalScore;
    private boolean isWin = false;
    private SoundManager soundManager;

    /**
     * Khởi tạo màn hình kết thúc trò chơi.
     *
     * @param renderer      bộ vẽ dùng để hiển thị nội dung.
     * @param sceneManager  đối tượng quản lý chuyển cảnh.
     * @param primaryStage  cửa sổ chính của trò chơi.
     */
    public EndScreen(Renderer renderer, SceneManager sceneManager, Stage primaryStage) {
        super(renderer, sceneManager, primaryStage);
        soundManager = SoundManager.getInstance();
        buttons = new HashMap<>();

        buttons.put("home", new GameButton(200, 400, 80, 80, "images/buttons/home.png", this));
        buttons.put("replay", new GameButton(320, 400, 80, 80, "images/buttons/replay.png", this));

        bindInputs();
    }

    /**
     * Thiết lập kết quả trò chơi và điểm số cuối cùng.
     *
     * @param isWin  true nếu người chơi chiến thắng, false nếu thua.
     * @param score  điểm số đạt được sau khi kết thúc trò chơi.
     */
    public void setGameResult(boolean isWin, int score) {
        this.isWin = isWin;
        this.finalScore = score;
        this.resultMessage = isWin ? "YOU WIN!" : "GAME OVER";
    }

    /**
     * Cập nhật trạng thái màn hình.
     * Màn hình kết thúc không có logic động, nên để trống.
     *
     * @param dt thời gian trôi qua giữa hai khung hình (delta time).
     */
    @Override
    public void update(double dt) {
        // Không có cập nhật động trong màn hình kết thúc.
    }

    /**
     * Vẽ toàn bộ nội dung màn hình kết thúc, bao gồm nền, điểm và nút.
     *
     * @param renderer bộ vẽ dùng để hiển thị hình ảnh.
     */
    @Override
    public void renderScreen(Renderer renderer) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderer.drawBackground(gc, "images/background/play_background.jpg");

        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Hiển thị thông báo kết quả
        Color resultColor = isWin ? Color.rgb(0, 255, 100) : Color.rgb(255, 50, 50);
        gc.setFill(resultColor);
        gc.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 60));

        javafx.scene.text.Text tempText = new javafx.scene.text.Text(resultMessage);
        tempText.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 60));
        double textWidth = tempText.getLayoutBounds().getWidth();
        gc.fillText(resultMessage, (canvas.getWidth() - textWidth) / 2, 200);

        // Hiển thị điểm số
        String scoreText = "Score: " + finalScore;
        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.NORMAL, 30));
        tempText.setText(scoreText);
        tempText.setFont(javafx.scene.text.Font.font("Arial", 30));
        textWidth = tempText.getLayoutBounds().getWidth();
        gc.fillText(scoreText, (canvas.getWidth() - textWidth) / 2, 280);

        // Vẽ các nút
        for (GameButton button : buttons.values()) {
            button.renderButton();
        }
    }

    /**
     * Gắn các sự kiện chuột cho nút "home" và "replay",
     * đồng thời xử lý hiệu ứng di chuột.
     */
    @Override
    protected void bindInputs() {
        buttons.get("home").setCallback(button -> {

            System.out.println("Returning to Main Menu...");
            soundManager.playSound("click");
            System.out.println("Trở về menu chính...");
            sceneManager.setScene("menu", primaryStage);
        });

        buttons.get("replay").setCallback(button -> {
            soundManager.playSound("click");
            System.out.println("Replaying Game...");
            System.out.println("Chơi lại...");
            sceneManager.setScene("play", primaryStage);
        });

        // Xử lý sự kiện click chuột
        keyboard.setOnMouseClick(e -> {
            for (GameButton button : buttons.values()) {
                if (keyboard.isOverRegion(button.getX(), button.getY(), button.getWidth(), button.getHeight())) {
                    soundManager.playSound("click");
                    button.triggerCallback();
                }
            }
        });

        // Hiệu ứng hover chuột
        keyboard.setOnMouseMove(e -> {
            boolean hovered = false;
            for (GameButton button : buttons.values()) {
                if (keyboard.isOverRegion(button.getX(), button.getY(), button.getWidth(), button.getHeight())) {
                    hovered = true;
                    break;
                }
            }
            scene.setCursor(hovered ? javafx.scene.Cursor.HAND : javafx.scene.Cursor.DEFAULT);
        });
    }

    /**
     * Thiết lập lại con trỏ chuột khi người chơi vào màn hình kết thúc.
     */
    @Override
    public void onEnter() {
        scene.setCursor(javafx.scene.Cursor.DEFAULT);
    }
}
