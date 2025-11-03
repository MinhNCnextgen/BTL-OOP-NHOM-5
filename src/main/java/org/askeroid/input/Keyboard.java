package org.askeroid.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Lớp {@code Keyboard} chịu trách nhiệm quản lý và xử lý các sự kiện đầu vào từ
 * bàn phím và chuột trong trò chơi.
 * Lớp này lưu trạng thái của các phím đang được nhấn, vị trí chuột,
 * và cho phép gán các hàm xử lý (callback) cho từng loại sự kiện như:
 * nhấn/thả phím, nhấn/thả chuột, click chuột, di chuyển chuột.
 */
public class Keyboard {

    /** Bảng lưu trạng thái của các phím (đang nhấn hoặc không). */
    private final Map<KeyCode, Boolean> keyStates = new HashMap<>();

    /** Hàm xử lý khi nhấn phím. */
    private Consumer<KeyCode> onKeyPress;

    /** Hàm xử lý khi thả phím. */
    private Consumer<KeyCode> onKeyRelease;

    /** Hàm xử lý khi nhấn chuột. */
    private Consumer<MouseEvent> onMousePress;

    /** Hàm xử lý khi thả chuột. */
    private Consumer<MouseEvent> onMouseRelease;

    /** Hàm xử lý khi click chuột (nhấn + thả cùng vị trí). */
    private Consumer<MouseEvent> onMouseClick;

    /** Hàm xử lý khi di chuyển chuột. */
    private Consumer<MouseEvent> onMouseMove;

    /** Tọa độ X của chuột. */
    private double mouseX;

    /** Tọa độ Y của chuột. */
    private double mouseY;

    /** Trạng thái chuột đang được nhấn hay không. */
    private boolean mousePressed = false;

    /**
     * Khởi tạo lớp {@code Keyboard} và gắn các sự kiện chuột & bàn phím vào {@link Scene}.
     *
     * @param scene đối tượng {@link Scene} mà người chơi tương tác
     */
    public Keyboard(Scene scene) {
        // Xử lý sự kiện bàn phím
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            boolean wasPressed = keyStates.getOrDefault(key, false);
            keyStates.put(key, true);
            if (!wasPressed && onKeyPress != null) onKeyPress.accept(key);
        });

        scene.setOnKeyReleased(event -> {
            KeyCode key = event.getCode();
            keyStates.put(key, false);
            if (onKeyRelease != null) onKeyRelease.accept(key);
        });

        // Xử lý nhấn/thả chuột
        scene.setOnMousePressed(event -> {
            mousePressed = true;
            mouseX = event.getX();
            mouseY = event.getY();
            if (onMousePress != null) onMousePress.accept(event);
        });

        scene.setOnMouseReleased(event -> {
            mousePressed = false;
            mouseX = event.getX();
            mouseY = event.getY();
            if (onMouseRelease != null) onMouseRelease.accept(event);
        });

        // Click chuột (nhấn + thả cùng chỗ)
        scene.setOnMouseClicked(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
            if (onMouseClick != null) onMouseClick.accept(event);
        });

        // Di chuyển chuột
        scene.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
            if (onMouseMove != null) onMouseMove.accept(event);
        });

        // Kéo chuột (drag)
        scene.setOnMouseDragged(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
        });
    }

    /**
     * Kiểm tra xem một phím có đang được nhấn hay không.
     *
     * @param key mã phím cần kiểm tra
     * @return true nếu phím đang được nhấn, ngược lại false
     */
    public boolean isKeyPressed(KeyCode key) {
        return keyStates.getOrDefault(key, false);
    }

    /**
     * Kiểm tra xem chuột có đang được nhấn hay không.
     *
     * @return true nếu chuột đang nhấn, ngược lại false
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Lấy vị trí trục X hiện tại của chuột.
     *
     * @return giá trị X của con trỏ chuột
     */
    public double getMouseX() {
        return mouseX;
    }

    /**
     * Lấy vị trí trục Y hiện tại của chuột.
     *
     * @return giá trị Y của con trỏ chuột
     */
    public double getMouseY() {
        return mouseY;
    }


    /**
     * Kiểm tra xem con trỏ chuột có đang nằm trong một vùng hình chữ nhật hay không.
     *
     * @param x      tọa độ X của vùng
     * @param y      tọa độ Y của vùng
     * @param width  chiều rộng vùng
     * @param height chiều cao vùng
     * @return true nếu chuột nằm trong vùng, ngược lại {false
     */
    public boolean isOverRegion(double x, double y, double width, double height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    // Thiết lập các hàm xử lý sự kiện (callback)

    /** Gán hàm xử lý khi nhấn phím. */
    public void setOnKeyPress(Consumer<KeyCode> handler) { this.onKeyPress = handler; }

    /** Gán hàm xử lý khi thả phím. */
    public void setOnKeyRelease(Consumer<KeyCode> handler) { this.onKeyRelease = handler; }

    /** Gán hàm xử lý khi nhấn chuột. */
    public void setOnMousePress(Consumer<MouseEvent> handler) { this.onMousePress = handler; }

    /** Gán hàm xử lý khi thả chuột. */
    public void setOnMouseRelease(Consumer<MouseEvent> handler) { this.onMouseRelease = handler; }

    /** Gán hàm xử lý khi click chuột. */
    public void setOnMouseClick(Consumer<MouseEvent> handler) { this.onMouseClick = handler; }

    /** Gán hàm xử lý khi di chuyển chuột. */
    public void setOnMouseMove(Consumer<MouseEvent> handler) { this.onMouseMove = handler; }

    /**
     * Xóa toàn bộ trạng thái phím hiện tại (reset bàn phím).
     */
    public void clear() {
        keyStates.clear();
    }
}
