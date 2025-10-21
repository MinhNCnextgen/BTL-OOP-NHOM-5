package org.askeroid.objects.gameObjects;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import org.askeroid.graphics.Renderer;

/**
 * Lớp trừu tượng đại diện cho một đối tượng trong trò chơi (game object).
 * Mọi đối tượng có vị trí, kích thước và tốc độ đều nên kế thừa lớp này —
 * ví dụ như bóng, thanh đỡ, gạch, hay vật phẩm tăng sức mạnh.
 */
public abstract class GameObject {
    protected double x, y, width, height, speed;

    /**
     * Khởi tạo một đối tượng trò chơi với vị trí, kích thước và tốc độ xác định.
     *
     * @param x      tọa độ X của đối tượng.
     * @param y      tọa độ Y của đối tượng.
     * @param width  chiều rộng của đối tượng.
     * @param height chiều cao của đối tượng.
     * @param speed  tốc độ di chuyển của đối tượng (pixel/giây).
     */
    public GameObject(double x, double y, double width, double height, double speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    /**
     * Kiểm tra xem đối tượng hiện tại có va chạm (giao nhau) với đối tượng khác hay không.
     *
     * @param other đối tượng khác cần kiểm tra va chạm.
     * @return {@code true} nếu hai đối tượng giao nhau, ngược lại {@code false}.
     */
    public boolean intersects(GameObject other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }

    /**Getters &  Setters */

    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public void setX(double val) { x = val; }
    public void setY(double val) { y = val; }
    public void setWidth(double val) { width = val; }
    public void setHeight(double val) { height = val; }
}
