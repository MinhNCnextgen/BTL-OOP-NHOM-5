package org.askeroid.objects.gameObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.Bricks.Brick;

import java.util.ArrayList;

/**
 * Lớp Ball mô tả quả bóng trong trò chơi.
 * Bóng có thể di chuyển, phản xạ với tường, va chạm với thanh đỡ (paddle)
 * và các viên gạch (brick).
 */
public class Ball extends GameObject {

    private double dx;
    private double dy;
    private final double radius;
    private double canvasWidth;
    private double canvasHeight;
    private boolean stuckToPaddle = true;  // <-- new flag

    /**
     * Khởi tạo bóng với vị trí, tốc độ, góc bay và kích thước màn hình.
     *
     * @param x           tọa độ X ban đầu
     * @param y           tọa độ Y ban đầu
     * @param radius      bán kính bóng
     * @param speed       tốc độ di chuyển
     * @param angle       góc phóng (độ)
     * @param canvasWidth chiều rộng vùng chơi
     * @param canvasHeight chiều cao vùng chơi
     */
    public Ball(double x, double y, double radius, double speed, double angle,
                double canvasWidth, double canvasHeight) {
        super(x, y, radius * 2, radius * 2, speed);
        this.radius = radius;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        // Tính vận tốc ban đầu theo góc
        this.dx = Math.cos(Math.toRadians(angle)) * speed;
        this.dy = -Math.sin(Math.toRadians(angle)) * speed;
    }

    /**
     * Cập nhật trạng thái bóng mỗi khung hình.
     * Xử lý dính vào paddle, di chuyển và va chạm với các đối tượng.
     *
     * @param dt        thời gian trôi qua (nano giây)
     * @param paddle    thanh đỡ hiện tại
     * @param brickList danh sách gạch trong màn chơi
     */
    public void update(double dt, Paddle paddle, ArrayList<Brick> brickList) {
        dt /= 1_000_000_000.0;
        if (stuckToPaddle) {
            stickToPaddle(paddle);
        } else {
            moveBall(dt);
            wallCollision();
            paddleCollision(paddle);
            brickCollision(brickList);
        }
    }

    /**
     * Giữ bóng ở vị trí dính vào đỉnh thanh đỡ (trước khi phóng).
     *
     * @param paddle thanh đỡ
     */
    private void stickToPaddle(Paddle paddle) {
        x = paddle.getX() + (paddle.getWidth() / 2) - radius;
        y = paddle.getY() - radius * 2 - 1;
    }

    /**
     * Di chuyển bóng theo vận tốc hiện tại.
     *
     * @param dt thời gian trôi qua (giây)
     */
    private void moveBall(double dt) {
        x += dx * dt;
        y += dy * dt;
    }

    /**
     * Phản xạ bóng khi va chạm với tường hoặc trần.
     */
    private void wallCollision() {
        if (x <= 0 || x + radius * 2 >= canvasWidth) {
            dx *= -1;
            x = Math.max(0, Math.min(x, canvasWidth - radius * 2));
        }

        if (y <= 0) {
            dy *= -1;
            y = 0;
        }
    }

    /**
     * Xử lý va chạm giữa bóng và thanh đỡ (paddle).
     * Góc phản xạ thay đổi tùy theo vị trí va chạm.
     *
     * @param paddle thanh đỡ hiện tại
     */
    private void paddleCollision(Paddle paddle) {
        if (intersects(paddle)) {
            dy *= -1;
            double hitPos = (x + radius) - (paddle.getX() + paddle.getWidth() / 2);
            dx = hitPos * 5; // hệ số điều chỉnh góc
            y = paddle.getY() - radius * 2;
        }
    }

    /**
     * Xử lý va chạm giữa bóng và các viên gạch.
     * Khi va chạm, bóng phản xạ theo hướng phù hợp
     * và gọi phương thức hit() của viên gạch.
     *
     * @param brickList danh sách gạch hiện tại
     */
    private void brickCollision(ArrayList<Brick> brickList) {
        for (Brick brick : brickList) {
            if (!brick.isActive()) continue;
            if (intersects(brick)) {

                double overlapLeft = (x + radius * 2) - brick.getX();
                double overlapRight = (brick.getX() + brick.getWidth()) - x;
                double overlapTop = (y + radius * 2) - brick.getY();
                double overlapBottom = (brick.getY() + brick.getHeight()) - y;

                double minOverlapX = Math.min(overlapLeft, overlapRight);
                double minOverlapY = Math.min(overlapTop, overlapBottom);

                if (minOverlapX < minOverlapY) {
                    dx *= -1;
                    if (overlapLeft < overlapRight) {
                        x = brick.getX() - radius * 2 - 0.1;
                    } else {
                        x = brick.getX() + brick.getWidth() + 0.1;
                    }
                } else {
                    dy *= -1;
                    if (overlapTop < overlapBottom) {
                        y = brick.getY() - radius * 2 - 0.1;
                    } else {
                        y = brick.getY() + brick.getHeight() + 0.1;
                    }
                }

                double hitPos = (x + radius) - (brick.getX() + brick.getWidth() / 2);
                dx += hitPos * 0.05;

                brick.hit();
                break;
            }
        }
    }

    /**
     * Đặt lại bóng về trạng thái dính vào paddle sau khi rơi xuống đáy.
     *
     * @param paddle thanh đỡ hiện tại
     */
    public void reset(Paddle paddle) {
        stuckToPaddle = true;
        this.dx = Math.cos(Math.toRadians(45)) * speed;
        this.dy = Math.sin(Math.toRadians(45)) * speed;
    }

    /**
     * Phóng bóng khỏi thanh đỡ để bắt đầu di chuyển.
     */
    public void launch() {
        if (!stuckToPaddle) return;
        stuckToPaddle = false;
        dx = 0;
        dy = -speed;
    }

    public double getRadius() { return radius; }
    public double getSpeed() { return speed; }

    /**
     * Đặt tốc độ mới cho bóng và giữ nguyên hướng bay hiện tại.
     *
     * @param val tốc độ mới
     */
    public void setSpeed(double val) {
        this.speed = val;
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            dx = (dx / length) * speed;
            dy = (dy / length) * speed;
        }
    }
}
