package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;

/**
 * Lớp đại diện cho loại gạch cơ bản (Default Brick) trong trò chơi.
 * Đây là loại gạch đơn giản nhất — chỉ cần bị đánh trúng một lần
 * là sẽ bị phá hủy. Màu mặc định của gạch là màu xanh lá cây.
 */
public class DefaultBrick extends Brick {

    /**
     * Khởi tạo một viên gạch mặc định tại vị trí và kích thước cho trước.
     *
     * @param x      hoành độ (tọa độ X) của viên gạch.
     * @param y      tung độ (tọa độ Y) của viên gạch.
     * @param width  chiều rộng của viên gạch.
     * @param height chiều cao của viên gạch.
     */
    public DefaultBrick(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.color = Color.GREEN;
        this.hits = 1;
    }
}
