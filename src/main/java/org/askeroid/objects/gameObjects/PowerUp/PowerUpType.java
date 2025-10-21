package org.askeroid.objects.gameObjects.PowerUp;

/**
 * Liệt kê tất cả các loại Power-Up có thể xuất hiện trong trò chơi.
 * Mỗi loại Power-Up được gắn với:
 *  Đường dẫn hình ảnh hiển thị trong game.
 *  Thời gian hiệu lực (tính bằng mili-giây).
 * Nếu thời gian hiệu lực bằng 0, hiệu ứng sẽ có tác dụng vĩnh viễn (ví dụ: ExtraLife).
 */
public enum PowerUpType {

    /** Mở rộng chiều rộng thanh đỡ tạm thời. */
    WidePaddle("images/powerup/wide_paddle.png", 5000),

    /** Tăng tốc độ bóng tạm thời. */
    FastBall("images/powerup/fast_ball.png", 5000),

    /** Giảm tốc độ bóng tạm thời. */
    SlowBall("images/powerup/slow_ball.png", 5000),

    /** Tăng thêm một máu (hiệu ứng vĩnh viễn, không giới hạn thời gian). */
    ExtraLife("images/powerup/increase_health.png", 0);

    /** Đường dẫn tới hình ảnh đại diện của Power-Up. */
    private final String imagePath;

    /** Thời gian hiệu lực của Power-Up (ms). */
    private final long duration;

    /**
     * Khởi tạo một loại Power-Up cụ thể.
     *
     * @param path     đường dẫn đến hình ảnh Power-Up.
     * @param duration thời gian hiệu lực của hiệu ứng (tính bằng mili-giây).
     */
    PowerUpType(String path, long duration) {
        this.duration = duration;
        this.imagePath = path;
    }

    /**
     * Trả về thời gian hiệu lực của Power-Up.
     *
     * @return thời gian hiệu lực (ms).
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Trả về đường dẫn hình ảnh đại diện của Power-Up.
     *
     * @return đường dẫn ảnh (String).
     */
    public String getImagePath() {
        return imagePath;
    }
}
