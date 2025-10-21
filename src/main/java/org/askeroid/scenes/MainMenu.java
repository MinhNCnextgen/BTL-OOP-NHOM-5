package org.askeroid.scenes;

import javafx.scene.Cursor;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;
import org.askeroid.objects.buttons.GameButton;
import org.askeroid.sound.SoundManager;
import java.util.HashMap;

/**
 * Lớp {@code MainMenu} đại diện cho màn hình chính của trò chơi Askeroid.
 * <p>
 * Màn hình bao gồm các nút điều hướng như "Play", "Exit", "Sound" và "Star (Ranking)".
 * Người chơi có thể tương tác bằng chuột để chọn chức năng.
 * </p>
 */
public class MainMenu extends GameScene {

    private final HashMap<String, GameButton> buttons;
    private boolean isSoundOn = true;
    private String currentSoundImage = "images/buttons/soundOn.png";
    private SoundManager soundManager;

    /**
     * Khởi tạo màn hình menu chính.
     *
     * @param renderer      Đối tượng {@link Renderer} dùng để vẽ giao diện.
     * @param sceneManager  Bộ quản lý cảnh, dùng để chuyển giữa các màn hình.
     * @param primaryStage  Cửa sổ chính của ứng dụng.
     */
    public MainMenu(Renderer renderer, SceneManager sceneManager, Stage primaryStage) {
        super(renderer, sceneManager, primaryStage);
        soundManager = SoundManager.getInstance();
        buttons = new HashMap<>();
        buttons.put("play", new GameButton(150, 270, 300, 150, "images/buttons/play.png", this));
        buttons.put("exit", new GameButton(150, 480, 300, 150, "images/buttons/quit.png", this));
        buttons.put("Active@3x", new GameButton(root.getWidth() / 2 - 50, root.getHeight() - 136, 100, 100, "asset/png@3x/Star/Large/Active@3x.png", this));
        buttons.put("sound", new GameButton(10, 690, 100, 100, currentSoundImage, this));

        bindInputs();
    }

    /**
     * Cập nhật logic của màn hình chính.
     * <p>Hiện tại không có logic cập nhật định kỳ trong menu.</p>
     *
     * @param dt Khoảng thời gian giữa hai khung hình (delta time).
     */
    @Override
    public void update(double dt) {
        // Không cần cập nhật logic trong menu chính
    }

    /**
     * Vẽ toàn bộ giao diện của màn hình chính.
     *
     * @param renderer Đối tượng {@link Renderer} dùng để vẽ hình ảnh và nền.
     */
    @Override
    public void renderScreen(Renderer renderer) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderer.drawBackground(gc, "images/background/main_background.png");

        for (GameButton button : buttons.values()) {
            button.renderButton();
        }
    }

    /**
     * Gán sự kiện điều khiển chuột cho các nút bấm trong menu.
     *
     * Gồm các callback:
     * Nút Play → chuyển sang màn chơi chính.
     * Nút Exit → thoát trò chơi.
     * Nút Start → chuyển sang bảng xếp hạng.
     * Nút Sound → bật/tắt âm thanh và đổi icon tương ứng.
     * Ngoài ra còn có hiệu ứng hover và xử lý click chuột.
     *
     */
    @Override
    protected void bindInputs() {
        buttons.get("play").setCallback(button -> {
            soundManager.playSound("click");
            System.out.println("Starting Game...");
            sceneManager.setScene("play", primaryStage);
        });

        buttons.get("exit").setCallback(button -> {
            soundManager.playSound("click");
            System.out.println("Exiting Game...");
            System.exit(0);
        });

        buttons.get("Active@3x").setCallback(button -> {
            soundManager.playSound("click");
            System.out.println("Star Button Clicked!");
            sceneManager.setScene("ranking", primaryStage);
        });

        buttons.get("sound").setCallback(button -> {
            soundManager.playSound("click");
            isSoundOn = !isSoundOn;
            currentSoundImage = isSoundOn ? "images/buttons/soundOn.png" : "images/buttons/soundOff.png";
            button.setImage(currentSoundImage);
            soundManager.setSoundEnabled(isSoundOn);

            System.out.println("Sound: " + (isSoundOn ? "ON" : "OFF"));
        });

        // Xử lý click chuột
        keyboard.setOnMouseClick(e -> {
            for (GameButton button : buttons.values()) {
                if (keyboard.isOverRegion(button.getX(), button.getY(), button.getWidth(), button.getHeight())) {

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
            scene.setCursor(hovered ? Cursor.HAND : Cursor.DEFAULT);
        });
    }

    /**
     * Phương thức được gọi khi người chơi vào màn hình này.
     */
    @Override
    public void onEnter() {
        soundManager.playBackgroundMusic();
        System.out.println("Entered Main Menu");
    }

    // Getter / Setter

    /**
     * Kiểm tra trạng thái bật/tắt âm thanh.
     *
     * @return {@code true} nếu âm thanh đang bật, ngược lại {@code false}.
     */
    public boolean isSoundOn() {
        return isSoundOn;
    }

    /**
     * Thay đổi trạng thái âm thanh.
     *
     * @param soundOn {@code true} để bật âm thanh, {@code false} để tắt.
     */
    public void setSoundOn(boolean soundOn) {
        isSoundOn = soundOn;
    }

    /**
     * Lấy đường dẫn ảnh hiện tại của nút âm thanh.
     *
     * @return Chuỗi đường dẫn ảnh đang được hiển thị.
     */
    public String getCurrentSoundImage() {
        return currentSoundImage;
    }

    /**
     * Lấy danh sách các nút trong menu.
     *
     * @return Bản đồ (Map) chứa các đối tượng {@link GameButton}.
     */
    public HashMap<String, GameButton> getButtons() {
        return buttons;
    }
}
