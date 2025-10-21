package org.askeroid.objects.gameObjects.PowerUp;

import javafx.scene.canvas.Canvas;
import org.askeroid.objects.gameObjects.GameObject;
import org.askeroid.scenes.PlayScreen;
import org.askeroid.sound.SoundManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Lớp đại diện cho một vật phẩm tăng sức mạnh (Power-Up) trong trò chơi.
 * Mỗi vật phẩm có thể mang lại hiệu ứng đặc biệt cho người chơi khi được hứng,
 * Vật phẩm sẽ rơi từ vị trí của viên gạch bị phá và biến mất khi ra khỏi màn hình
 * hoặc được người chơi hứng trúng.
 */
public class PUObject extends GameObject {

    /** Danh sách tất cả các vật phẩm Power-Up đang tồn tại trong trò chơi. */
    public static ArrayList<PUObject> PUObjectList = new ArrayList<>();

    /** Đường dẫn đến hình ảnh của vật phẩm Power-Up. */
    private String imagePowerUp;

    /** Kiểu của vật phẩm Power-Up (xác định loại hiệu ứng). */
    private PowerUpType powerUp;

    /** Cờ trạng thái: cho biết vật phẩm còn hoạt động hay đã biến mất. */
    public boolean isActive;

    private SoundManager soundManager;
     /**
     * Khởi tạo một vật phẩm Power-Up mới.
     *
     * @param x      tọa độ X ban đầu của vật phẩm.
     * @param y      tọa độ Y ban đầu của vật phẩm.
     * @param width  chiều rộng của vật phẩm.
     * @param height chiều cao của vật phẩm.
     * @param speed  tốc độ rơi của vật phẩm.
     * @param type   loại Power-Up (nếu null, sẽ chọn ngẫu nhiên).
     */
    public PUObject(double x, double y, double width, double height, double speed, PowerUpType type) {
        super(x, y, width, height, speed);
        this.soundManager = SoundManager.getInstance();
        if (type == null) {
            this.powerUp = getRandomPowerUp();
        } else {
            this.powerUp = type;
        }

        imagePowerUp = powerUp.getImagePath();
        isActive = true;
    }

    /**
     * Cập nhật trạng thái và vị trí của vật phẩm.
     * Vật phẩm sẽ rơi dần theo thời gian dựa trên tốc độ.
     * KHi chạm vào thanh đỡ, hiệu ứng Power-Up được kích hoạt.
     * Nếu rơi ra khỏi màn hình, vật phẩm bị hủy.
     *
     * @param dt      thời gian trôi qua kể từ khung hình trước (nano giây).
     * @param canvas  đối tượng Canvas để lấy chiều cao màn hình.
     * @param ps      màn chơi hiện tại, dùng để truy cập Paddle và PowerUpManager.
     */
    public void update(double dt, Canvas canvas, PlayScreen ps) {
        dt /= 1_000_000_000.0; // đổi sang giây
        y += speed * dt;

        if (intersects(ps.getPaddle())) {
            soundManager.playSound("powerup");
            grantPowerUp(ps);
            isActive = false;
        }

        if (y > canvas.getHeight()) {
            isActive = false;
        }
    }

    /**
     * Chọn ngẫu nhiên một loại Power-Up.
     *
     * @return một giá trị ngẫu nhiên từ danh sách {@link PowerUpType}.
     */
    private PowerUpType getRandomPowerUp() {
        int pick = new Random().nextInt(PowerUpType.values().length);
        return PowerUpType.values()[pick];
    }

    /**
     * Kích hoạt hiệu ứng của vật phẩm Power-Up cho người chơi.
     *
     * @param ps màn chơi hiện tại.
     */
    private void grantPowerUp(PlayScreen ps) {
        ps.getPowerUpManager().applyPowerUp(powerUp);
    }

    // Getters & Setters
    public String getImagePowerUp() {
        return imagePowerUp;
    }
}
