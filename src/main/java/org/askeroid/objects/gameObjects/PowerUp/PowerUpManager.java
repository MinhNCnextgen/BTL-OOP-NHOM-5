package org.askeroid.objects.gameObjects.PowerUp;

import org.askeroid.objects.gameObjects.Paddle;
import org.askeroid.objects.gameObjects.Ball;
import org.askeroid.scenes.PlayScreen;

import java.util.ArrayList;

/**
 * Lớp chịu trách nhiệm quản lý tất cả các hiệu ứng Power-Up trong trò chơi.
 * <p>
 * PowerUpManager xử lý việc:
 * <ul>
 *     <li>Kích hoạt hiệu ứng tương ứng khi người chơi hứng vật phẩm.</li>
 *     <li>Theo dõi thời gian tồn tại của mỗi hiệu ứng.</li>
 *     <li>Tự động hoàn tác (reset) hiệu ứng khi hết thời gian.</li>
 * </ul>
 * </p>
 */
public class PowerUpManager {

    /**
     * Lớp phụ (nội bộ) dùng để lưu thông tin một Power-Up đang hoạt động.
     * Gồm loại Power-Up và thời điểm được kích hoạt.
     */
    private class PowerUpInstance {
        PowerUpType type;
        long time_created;

        public PowerUpInstance(PowerUpType type, long time_created) {
            this.type = type;
            this.time_created = time_created;
        }
    }

    /** Danh sách chứa các Power-Up đang hoạt động (có hiệu lực). */
    private static final ArrayList<PowerUpInstance> activePowerUps = new ArrayList<>();

    /** Màn chơi hiện tại, dùng để truy cập Paddle, Ball và ScoreManager. */
    private final PlayScreen screen;

    /**
     * Khởi tạo bộ quản lý Power-Up cho màn chơi.
     *
     * @param screen màn chơi hiện tại (PlayScreen).
     */
    public PowerUpManager(PlayScreen screen) {
        this.screen = screen;
    }

    /**
     * Kích hoạt hiệu ứng của một Power-Up khi người chơi hứng được vật phẩm.
     * <p>
     * Tác dụng cụ thể phụ thuộc vào {@link PowerUpType}:
     * <ul>
     *     <li><b>WidePaddle</b>: mở rộng thanh đỡ (tối đa 1000px).</li>
     *     <li><b>FastBall</b>: tăng tốc độ bóng lên 1.5 lần.</li>
     *     <li><b>SlowBall</b>: giảm tốc độ bóng xuống 0.8 lần.</li>
     *     <li><b>ExtraLife</b>: tăng thêm 1 mạng (không giới hạn thời gian).</li>
     * </ul>
     * </p>
     *
     * @param type loại Power-Up được kích hoạt.
     */
    public void applyPowerUp(PowerUpType type) {
        Paddle paddle = screen.getPaddle();
        Ball ball = screen.getBall();

        switch (type) {
            case WidePaddle:
                System.out.println("Wider Paddle");
                double oldWidth = paddle.getWidth();
                double newWidth = Math.min(oldWidth * 1.3, 1000);
                double centerX = paddle.getX() + oldWidth / 2;
                paddle.setWidth(newWidth);
                paddle.setX(centerX - newWidth / 2);
                break;

            case FastBall:
                System.out.println("Faster Ball");
                ball.setSpeed(ball.getSpeed() * 1.5);
                break;

            case SlowBall:
                System.out.println("Slower Ball");
                ball.setSpeed(ball.getSpeed() * 0.8);
                break;

            case ExtraLife:
                System.out.println("Extra Life");
                screen.getScoreManager().increaseHealth(1);
                return; // Không cần reset lại hiệu ứng này
        }

        // Lưu lại hiệu ứng cùng thời gian kích hoạt để theo dõi
        activePowerUps.add(new PowerUpInstance(type, System.currentTimeMillis()));
    }

    /**
     * Cập nhật trạng thái của các Power-Up đang hoạt động.
     * <p>
     * Nếu một hiệu ứng đã tồn tại quá thời gian quy định {@link PowerUpType#getDuration()},
     * thì nó sẽ được reset (hoàn tác lại về trạng thái ban đầu).
     * </p>
     */
    public void update() {
        long now = System.currentTimeMillis();

        for (PowerUpInstance powerUp : activePowerUps) {
            if (powerUp.time_created + powerUp.type.getDuration() < now) {
                resetPowerUp(powerUp.type);
            }
        }

        // Loại bỏ những Power-Up đã hết hạn
        activePowerUps.removeIf(powerUp ->
                powerUp.time_created + powerUp.type.getDuration() <= now
        );
    }

    /**
     * Hoàn tác lại hiệu ứng Power-Up khi hết thời gian hiệu lực.
     *
     * @param type loại Power-Up cần reset.
     */
    private void resetPowerUp(PowerUpType type) {
        Paddle paddle = screen.getPaddle();
        Ball ball = screen.getBall();

        switch (type) {
            case WidePaddle:
                System.out.println("Reset Wide Paddle");
                double oldWidth = paddle.getWidth();
                double newWidth = oldWidth / 1.3;
                double centerX = paddle.getX() + oldWidth / 2;
                paddle.setWidth(newWidth);
                paddle.setX(centerX - newWidth / 2);
                break;

            case FastBall:
                System.out.println("Reset Fast Ball");
                ball.setSpeed(ball.getSpeed() / 1.5);
                break;

            case SlowBall:
                System.out.println("Reset Slow Ball");
                ball.setSpeed(ball.getSpeed() / 0.8);
                break;

            default:
                break;
        }
    }
}
