package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.GameObject;

import java.util.ArrayList;

/**
 * Lớp trừu tượng đại diện cho một viên gạch trong trò chơi.
 * Mỗi viên gạch có màu sắc, trạng thái hoạt động (active/inactive)
 * và số lần có thể bị đánh trúng trước khi bị phá hủy.
 * Các lớp con sẽ xác định hành vi hoặc đặc điểm riêng (ví dụ: gạch cứng, gạch thưởng...).
 */
public abstract class Brick extends GameObject {

    /** Màu của viên gạch. */
    protected Color color;

    /** Trạng thái của gạch: true nếu còn tồn tại, false nếu đã bị phá. */
    protected boolean active;

    /** Số lần còn lại trước khi gạch bị phá hủy. */
    protected int hits;

    /** Danh sách tĩnh chứa toàn bộ các viên gạch trong màn chơi hiện tại. */
    public static ArrayList<Brick> brickMap;

    /**
     * Khởi tạo một viên gạch tại vị trí cụ thể.
     *
     * @param x      hoành độ (tọa độ X) của viên gạch.
     * @param y      tung độ (tọa độ Y) của viên gạch.
     * @param width  chiều rộng của viên gạch.
     * @param height chiều cao của viên gạch.
     */
    public Brick(double x, double y, double width, double height) {
        super(x, y, width, height, 0); // Gạch là đối tượng tĩnh, không di chuyển
        this.active = true;
    }

    /**
     * Xử lý khi gạch bị đánh trúng.
     * Giảm số lần chịu đòn, và nếu số lần này nhỏ hơn hoặc bằng 0,
     * thì gạch được đánh dấu là đã bị phá hủy (active = false).
     */
    public void hit() {
        hits--;
        if (hits <= 0) {
            active = false;
        }
    }

    /**
     * Kiểm tra xem gạch còn hoạt động hay đã bị phá.
     *
     * @return true nếu gạch còn tồn tại, false nếu đã bị phá hủy.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Lấy màu của viên gạch.
     *
     * @return đối tượng {@link Color} biểu thị màu sắc của gạch.
     */
    public Color getColor() {
        return color;
    }
}
