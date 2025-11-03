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

        buttons.put("home", new GameButton(200, 400, 80, 80,
                "images/buttons/home.png", this));
        buttons.put("replay", new GameButton(320, 400, 80, 80,
                "images/buttons/replay.png", this));

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

    @Override
    /**
     * Hiển thị màn hình kết quả (thắng/thua) sau khi kết thúc trò chơi.
     *
     * @param renderer đối tượng Renderer chịu trách nhiệm vẽ lên màn hình.
     */
    public void renderScreen(Renderer renderer) {
        // Xóa toàn bộ canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Vẽ nền trò chơi
        renderer.drawBackground(gc, "images/background/play_background.jpg");

        // Vẽ lớp phủ mờ (overlay)
        renderer.drawOverlay(gc, canvas);

        // Tô thêm lớp tối nhẹ để làm nổi bật chữ
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Color resultColor = isWin ? Color.rgb(0, 255, 100) : Color.rgb(255, 50, 50);
        String fontFamily = "Arial";

        // Tính độ rộng chữ để căn giữa
        javafx.scene.text.Text tempText = new javafx.scene.text.Text(resultMessage);
        tempText.setFont(javafx.scene.text.Font.font(fontFamily, javafx.scene.text.FontWeight.BOLD, 60));
        double textWidth = tempText.getLayoutBounds().getWidth();

        renderer.drawText(gc, resultMessage,
                (canvas.getWidth() - textWidth) / 2,
                200, fontFamily, 60, resultColor);

        String scoreText = "Score: " + finalScore;
        tempText.setText(scoreText);
        tempText.setFont(javafx.scene.text.Font.font(fontFamily, 30));
        textWidth = tempText.getLayoutBounds().getWidth();

        renderer.drawText(gc, scoreText,
                (canvas.getWidth() - textWidth) / 2,
                280, fontFamily, 30, Color.WHITE);

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
            sceneManager.setScene("menu", primaryStage);
        });

        buttons.get("replay").setCallback(button -> {
            soundManager.playSound("click");
            System.out.println("Replaying Game...");
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
