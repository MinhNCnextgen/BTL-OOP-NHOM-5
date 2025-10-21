package org.askeroid.scenes;

import javafx.scene.Cursor;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;
import org.askeroid.objects.buttons.GameButton;
import org.askeroid.sound.SoundManager;
import org.askeroid.utils.ScoreRanking;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Màn hình bảng xếp hạng (Ranking).
 * Hiển thị danh sách điểm cao nhất của người chơi
 * và cung cấp nút quay lại menu chính.
 */
public class Ranking extends GameScene {

    private final HashMap<String, GameButton> buttons;
    private SoundManager soundManager;

    /**
     * Khởi tạo màn hình bảng xếp hạng.
     *
     * @param renderer      bộ vẽ dùng để hiển thị nội dung.
     * @param sceneManager  đối tượng quản lý chuyển cảnh.
     * @param primaryStage  cửa sổ chính của trò chơi.
     */
    public Ranking(Renderer renderer, SceneManager sceneManager, Stage primaryStage) {
        super(renderer, sceneManager, primaryStage);
        soundManager = SoundManager.getInstance();
        buttons = new HashMap<>();

        buttons.put("home", new GameButton(
                15,
                root.getHeight() - 110,
                100,
                100,
                "images/buttons/home.png",
                this
        ));

        bindInputs();
    }

    /**
     * Cập nhật trạng thái của màn hình.
     * Màn hình bảng xếp hạng không có logic động nên để trống.
     *
     * @param dt thời gian trôi qua giữa hai khung hình (delta time).
     */
    @Override
    public void update(double dt) {
        // Không cần cập nhật động trong bảng xếp hạng
    }

    /**
     * Vẽ toàn bộ nội dung của màn hình bảng xếp hạng,
     * bao gồm nền, nút "home" và danh sách điểm cao.
     *
     * @param renderer bộ vẽ dùng để hiển thị hình ảnh và chữ.
     */
    @Override
    public void renderScreen(Renderer renderer) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderer.drawBackground(gc, "images/background/main_background.png");

        for (GameButton button : buttons.values()) {
            button.renderButton();
        }

        renderScore();
    }

    /**
     * Gắn sự kiện chuột cho các nút và hiệu ứng di chuột.
     */
    @Override
    protected void bindInputs() {
        buttons.get("home").setCallback(button -> {
            soundManager.playSound("click");
            System.out.println("Returning to Main Menu...");
            System.out.println("Trở về menu chính...");
            sceneManager.setScene("menu", primaryStage);
        });

        // Sự kiện click chuột
        keyboard.setOnMouseClick(e -> {
                    for (GameButton button : buttons.values()) {
                        if (keyboard.isOverRegion(button.getX(), button.getY(), button.getWidth(), button.getHeight())) {
                            soundManager.playSound("click");
                            button.triggerCallback();
                        }
                    }
            for (GameButton button : buttons.values()) {
                if (keyboard.isOverRegion(button.getX(), button.getY(), button.getWidth(), button.getHeight())) {
                    button.triggerCallback();
                }
            }
        });

        // Hiệu ứng di chuột
        keyboard.setOnMouseMove(e -> {
            boolean hovered = false;
            for (GameButton button : buttons.values()) {
                if (keyboard.isOverRegion(button.getX(), button.getY(), button.getWidth(), button.getHeight())) {
                    hovered = true;
                    break;
                }
            }
            scene.setCursor(hovered ? Cursor.HAND : Cursor.DEFAULT);
        });
    }

    /**
     * Được gọi khi người chơi vào màn hình bảng xếp hạng.
     * Thiết lập lại con trỏ chuột và in thông báo debug ra console.
     */
    @Override
    public void onEnter() {
        System.out.println("Đã vào màn hình bảng xếp hạng.");
        scene.setCursor(Cursor.DEFAULT);
    }

    /**
     * Hiển thị danh sách điểm cao trên màn hình.
     * Mỗi điểm được vẽ theo thứ tự xếp hạng.
     */
    private void renderScore() {
        ArrayList<Integer> scores = ScoreRanking.readScore();
        int startY = 250;
        int rank = 1;

        for (Integer score : scores) {
            String scoreText = rank + ". " + score;
            renderer.drawText(
                    gc,
                    scoreText,
                    root.getWidth() / 2 - 50,
                    startY,
                    "Times New Roman",
                    36,
                    javafx.scene.paint.Color.WHITE
            );
            startY += 60;
            rank++;
        }
    }
}
