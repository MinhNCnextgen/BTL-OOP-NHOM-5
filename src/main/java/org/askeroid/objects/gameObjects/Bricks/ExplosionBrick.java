package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.GameObject;
import java.util.ArrayList;

/**
 * Gạch nổ (ExplosionBrick) là loại gạch đặc biệt khi bị phá hủy sẽ tạo ra vụ nổ,
 * ảnh hưởng đến các gạch xung quanh trong một bán kính nhất định.
 */
public class ExplosionBrick extends Brick {

    /** Bán kính vụ nổ tính từ tâm gạch. */
    private final double explosionRadius;

    /** Danh sách các mặt nạ vụ nổ đang hoạt động để xử lý hiệu ứng va chạm. */
    public static ArrayList<Mask> maskList = new ArrayList<>();

    /**
     * Khởi tạo một gạch nổ tại vị trí xác định.
     *
     * @param x      tọa độ X của gạch
     * @param y      tọa độ Y của gạch
     * @param width  chiều rộng gạch
     * @param height chiều cao gạch
     */
    public ExplosionBrick(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.color = Color.RED;
        this.hits = 1;
        this.explosionRadius = Math.max(width, height); // bán kính nổ bằng kích thước lớn hơn của gạch
    }

    /**
     * Lớp Mask đại diện cho vùng ảnh hưởng hình tròn của vụ nổ.
     * Dùng để kiểm tra va chạm với các gạch khác.
     */
    public class Mask extends GameObject {

        private final double radius;
        private final long timeCreated;

        /**
         * Tạo một mặt nạ vụ nổ tại vị trí trung tâm với bán kính xác định.
         *
         * @param centerX tọa độ X của tâm vụ nổ
         * @param centerY tọa độ Y của tâm vụ nổ
         * @param radius  bán kính vụ nổ
         */
        public Mask(double centerX, double centerY, double radius) {
            super(centerX - radius, centerY - radius, radius * 2, radius * 2, 0);
            this.radius = radius;
            this.timeCreated = System.nanoTime();
        }

        /**
         * Kiểm tra xem mặt nạ vụ nổ có chạm vào một gạch khác hay không.
         *
         * @param b gạch cần kiểm tra
         * @return true nếu gạch nằm trong vùng nổ
         */
        public boolean intersects(Brick b) {
            double dx = (b.getX() + b.getWidth() / 2) - (x + radius);
            double dy = (b.getY() + b.getHeight() / 2) - (y + radius);
            double distance = Math.sqrt(dx * dx + dy * dy);
            double brickRadius = Math.max(b.getWidth(), b.getHeight()) / 2.0;
            return distance < (radius + brickRadius);
        }

        public long getTimeCreated() {
            return timeCreated;
        }
    }

    /**
     * Khi bị đánh trúng, nếu gạch không còn hoạt động thì sẽ phát nổ.
     */
    @Override
    public void hit() {
        super.hit();
        if (!active) {
            explode();
        }
    }

    /**
     * Xử lý vụ nổ: tạo mặt nạ nổ, thêm vào danh sách,
     * và gây sát thương cho các gạch khác trong vùng ảnh hưởng.
     */
    protected void explode() {
        System.out.println("ExplosionBrick tại (" + x + ", " + y + ") đã nổ!");

        double centerX = x + width / 2;
        double centerY = y + height / 2;

        Mask mask = new Mask(centerX, centerY, explosionRadius);
        maskList.add(mask);

        for (Brick b : brickMap) {
            if (b != this && b.isActive() && mask.intersects(b)) {
                b.hit();
            }
        }
    }
}
