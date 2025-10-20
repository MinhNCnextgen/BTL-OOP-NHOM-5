package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.PowerUp.PUObject;

/**
 * Lớp đại diện cho loại gạch đặc biệt có chứa vật phẩm.
 * Khi viên gạch này bị phá hủy, nó sẽ sinh ra một vật phẩm rơi xuống.
 * Người chơi có thể bắt lấy vật phẩm này để nhận hiệu ứng tăng cường.
 */
public class PowerUpBrick extends Brick {

    /**
     * Khởi tạo một viên gạch vật phẩm tại vị trí và kích thước cho trước.
     * Gạch vật phẩm chỉ cần bị đánh trúng một lần là sẽ vỡ
     * và sinh ra một vật phẩm rơi xuống.
     *
     * @param x      toạ độ x của viên gạch.
     * @param y      toạ độ y của viên gạch.
     * @param width  chiều rộng của viên gạch.
     * @param height chiều cao của viên gạch.
     */
    public PowerUpBrick(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.hits = 1;
        this.color = Color.PINK;
    }

    /**
     * Xử lý khi viên gạch bị bóng đánh trúng.
     * Khi viên gạch bị phá hủy (không còn hoạt động),
     * một vật phẩm {@link PUObject} mới sẽ được tạo ra
     * và thêm vào danh sách vật phẩm đang tồn tại trong trò chơi.
     */
    @Override
    public void hit() {
        super.hit();
        if (!active) {
            PUObject.PUObjectList.add(new PUObject(x + 10, y, 40, 40, 450, null));
        }
    }
}
