package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;

/**
 * Lớp đại diện cho loại gạch cứng (Hard Brick) trong trò chơi.
 * Gạch cứng cần phải bị đánh trúng nhiều lần mới vỡ hoàn toàn.
 * Mỗi lần bị đánh trúng, số lượng "lượt chịu đòn" (hits) giảm đi 1
 * và màu của gạch sẽ thay đổi để biểu thị mức độ hư hại.
 */
public class HardBrick extends Brick {

    /**
     * Khởi tạo một viên gạch cứng tại vị trí và kích thước cho trước.
     * <p>
     * Gạch này có thể chịu được 3 lần đánh trước khi bị phá hủy hoàn toàn.
     * </p>
     *
     * @param x      hoành độ (tọa độ X) của viên gạch.
     * @param y      tung độ (tọa độ Y) của viên gạch.
     * @param width  chiều rộng của viên gạch.
     * @param height chiều cao của viên gạch.
     */
    public HardBrick(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.color = Color.ORANGE;
        this.hits = 3;
    }

    /**
     * Trả về màu tương ứng với số lần bị đánh còn lại.
     *
     * @param hits số lượt còn lại.
     * @return màu sắc tương ứng với mức độ hư hại.
     */
    private Color getColorForHits(int hits) {
        switch (hits) {
            case 1: return Color.GREEN;
            case 2: return Color.YELLOW;
            case 3: return Color.ORANGE;
            default: return Color.RED;
        }
    }

    /**
     * Xử lý khi viên gạch bị bóng đánh trúng.
     * Giảm số lượt chịu đòn và cập nhật lại màu sắc tương ứng
     * nếu gạch vẫn còn hoạt động (chưa bị phá hủy).
     */
    @Override
    public void hit() {
        super.hit();
        if (active) {
            this.color = getColorForHits(hits);
        }
    }
}
