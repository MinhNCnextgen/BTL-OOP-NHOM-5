package org.askeroid.objects.gameObjects;

import javafx.scene.input.KeyCode;
import org.askeroid.input.Keyboard;

/**
 * Lớp đại diện cho thanh đỡ (Paddle) trong trò chơi.
 * Thanh đỡ do người chơi điều khiển, di chuyển theo trục ngang
 * để hứng bóng và ngăn bóng rơi ra ngoài màn hình.
 */
public class Paddle extends GameObject {

    /**
     * Tạo một thanh đỡ với vị trí, kích thước và tốc độ xác định.
     *
     * @param x      tọa độ X ban đầu của thanh đỡ.
     * @param y      tọa độ Y ban đầu của thanh đỡ.
     * @param width  chiều rộng của thanh đỡ.
     * @param height chiều cao của thanh đỡ.
     * @param speed  tốc độ di chuyển (pixel/giây).
     */
    public Paddle(double x, double y, double width, double height, double speed) {
        super(x, y, width, height, speed);
    }

    /**
     * Cập nhật vị trí thanh đỡ dựa trên đầu vào bàn phím và thời gian trôi qua.
     * Người chơi có thể di chuyển thanh đỡ sang trái/phải bằng các phím:
     * Trái: Mũi tên trái hoặc phím
     * Phải:</b> Mũi tên phải hoặc phím D
     * Hàm này cũng đảm bảo thanh đỡ không vượt ra ngoài giới hạn màn hình.
     * @param keyboard     đối tượng quản lý trạng thái phím bấm.
     * @param dt           thời gian trôi qua kể từ khung hình trước (nano giây).
     * @param canvasWidth  chiều rộng của màn hình trò chơi.
     */
    public void update(Keyboard keyboard, double dt, double canvasWidth) {
        double dx = 0;
        dt /= 1_000_000_000.0;

        if (keyboard.isKeyPressed(KeyCode.LEFT) || keyboard.isKeyPressed(KeyCode.A)) {
            dx -= speed * dt;
        }

        if (keyboard.isKeyPressed(KeyCode.RIGHT) || keyboard.isKeyPressed(KeyCode.D)) {
            dx += speed * dt;
        }

        x += dx;

        if (x < 0) x = 0;
        if (x + width > canvasWidth) x = canvasWidth - width;
    }
}
