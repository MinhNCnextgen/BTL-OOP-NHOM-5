package org.askeroid.objects.buttons;

import java.util.function.Consumer;
import org.askeroid.scenes.GameScene;

/**
 * Đại diện cho một nút trong trò chơi.
 *
 * Lớp này quản lý vị trí, kích thước, hình ảnh, và hành động khi người chơi nhấn nút.
 * Mỗi nút được liên kết với một {@link GameScene} để hiển thị và xử lý callback.
 */
public class GameButton {

    private final double x;
    private final double y;
    private final double width;
    private final double height;
    private final GameScene gameScene;
    private String imagePath;
    private Consumer<GameButton> callback;

    /**
     * Tạo một nút mới trong trò chơi.
     *
     * @param x         tọa độ X của nút
     * @param y         tọa độ Y của nút
     * @param width     chiều rộng của nút
     * @param height    chiều cao của nút
     * @param imagePath đường dẫn tới hình ảnh của nút
     * @param gameScene cảnh (scene) chứa nút
     */
    public GameButton(double x, double y, double width, double height, String imagePath, GameScene gameScene) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
        this.gameScene = gameScene;
    }

    /**
     * Vẽ nút lên màn hình.
     */
    public void renderButton() {
        gameScene.getRenderer().drawImage(gameScene.getGraphicsContext(), this.imagePath, x, y, width, height);
    }

    /**
     * Đặt hình ảnh cho nút.
     *
     * @param imagePath đường dẫn hình ảnh mới
     */
    public void setImage(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Thiết lập hành động callback khi nút được nhấn.
     *
     * @param callback hàm callback nhận đối tượng {@code GameButton}
     */
    public void setCallback(Consumer<GameButton> callback) {
        this.callback = callback;
    }

    /**
     * Kích hoạt callback của nút (nếu có).
     */
    public void triggerCallback() {
        if (callback != null) {
            callback.accept(this);
        }
    }

    // Getter methods
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
