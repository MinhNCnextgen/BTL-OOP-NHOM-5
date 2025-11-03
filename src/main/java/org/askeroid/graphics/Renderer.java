package org.askeroid.graphics;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.GaussianBlur;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.Ball;
import org.askeroid.objects.gameObjects.Bricks.Brick;
import org.askeroid.objects.gameObjects.Bricks.ExplosionBrick;
import org.askeroid.objects.gameObjects.Paddle;

/**
 * Lớp Renderer chịu trách nhiệm vẽ (render) các thành phần trong trò chơi Askeroid
 * lên màn hình, bao gồm nền, thanh paddle, quả bóng, gạch và hiệu ứng nổ.
 */
public class Renderer {
    private Stage primaryStage;

    /**
     * Khởi tạo đối tượng Renderer với cửa sổ chính của game.
     *
     * @param primaryStage cửa sổ chính của ứng dụng.
     */
    public Renderer(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Thiết lập cấu hình ban đầu cho cửa sổ trò chơi.
     * Tắt chế độ thay đổi kích thước và đặt tiêu đề cho cửa sổ.
     */
    public void createWindow() {
        primaryStage.setResizable(false);
        primaryStage.setTitle("Askeroid Game");
    }

    /**
     * Vẽ một hình ảnh lên canvas tại vị trí và kích thước chỉ định.
     *
     * @param gc         đối tượng GraphicsContext dùng để vẽ
     * @param imagePath  đường dẫn đến ảnh
     * @param x          tọa độ X
     * @param y          tọa độ Y
     * @param width      chiều rộng ảnh
     * @param height     chiều cao ảnh
     */
    public void drawImage(GraphicsContext gc, String imagePath, double x, double y, double width, double height) {
        gc.setImageSmoothing(false);
        Image image = new Image(imagePath, width, height, false, false);
        gc.drawImage(image, x, y);
    }

    /**
     * Vẽ hình nền trò chơi (background) phủ toàn bộ canvas.
     *
     * @param gc   đối tượng GraphicsContext dùng để vẽ
     * @param path đường dẫn đến ảnh nền
     */
    public void drawBackground(GraphicsContext gc, String path) {
        Image image = new Image(path, false);
        gc.setImageSmoothing(false);

        double cw = gc.getCanvas().getWidth();
        double ch = gc.getCanvas().getHeight();

        gc.clearRect(0, 0, cw, ch);
        gc.drawImage(image, 0, 0, cw, ch);
    }

    /**
     * Vẽ thanh paddle của người chơi.
     * Paddle có màu xanh dương, viền trắng.
     *
     * @param gc     đối tượng GraphicsContext
     * @param paddle đối tượng Paddle cần vẽ
     */
    public void drawPaddle(GraphicsContext gc, Paddle paddle) {
        gc.setFill(Color.DODGERBLUE);
        gc.fillRoundRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), 10, 10);

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRoundRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), 10, 10);
    }

    /**
     * Vẽ quả bóng trong trò chơi.
     *
     * @param gc   đối tượng GraphicsContext
     * @param ball đối tượng Ball cần vẽ
     */
    public void drawBall(GraphicsContext gc, Ball ball) {
        gc.setFill(Color.WHITE);
        gc.fillOval(ball.getX(), ball.getY(), ball.getRadius() * 2, ball.getRadius() * 2);
    }

    /**
     * Vẽ một viên gạch (Brick) nếu gạch đang hoạt động.
     * Gạch được vẽ với màu riêng và viền đen.
     *
     * @param gc    đối tượng {@link GraphicsContext}
     * @param brick đối tượng {@link Brick} cần vẽ
     */
    public void drawBrick(GraphicsContext gc, Brick brick) {
        if (!brick.isActive()) return;

        gc.setFill(brick.getColor());
        gc.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());

        gc.setLineWidth(2);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
    }

    /**
     * Vẽ văn bản lên canvas tại vị trí và kiểu chữ xác định.
     *
     * @param gc    đối tượng GraphicsContext
     * @param text  nội dung văn bản
     * @param x     tọa độ X
     * @param y     tọa độ Y
     * @param font  tên font chữ
     * @param size  kích cỡ chữ
     * @param color màu chữ
     */
    public void drawText(GraphicsContext gc, String text, double x, double y,
                         String font, double size, Color color) {
        gc.setFill(color);
        gc.setFont(javafx.scene.text.Font.font(font, size));
        gc.fillText(text, x, y);
    }

    /**
     * Vẽ vùng mask (hiệu ứng nổ) cho gạch nổ (ExplosionBrick).
     * Mask được thể hiện bằng hình tròn màu đỏ bán trong suốt.
     *
     * @param gc   đối tượng GraphicsContext
     * @param mask đối tượng ExplosionBrick.Mask đại diện vùng nổ
     */
    public void drawMask(GraphicsContext gc, ExplosionBrick.Mask mask) {
        gc.setFill(Color.color(1, 0, 0, 0.3));
        gc.fillOval(mask.getX(), mask.getY(), mask.getWidth(), mask.getHeight());
    }

    public void drawOverlay(GraphicsContext gc, Canvas canvas) {
        // Save current state
        gc.save();

        // Apply a Gaussian blur effect
        GaussianBlur blur = new GaussianBlur(20); // adjust blur radius (higher = blurrier)
        gc.applyEffect(blur);

        // Draw a semi-transparent rectangle over the entire canvas
        gc.setFill(Color.rgb(0, 0, 0, 0.3)); // black with 30% opacity
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Restore the original state
        gc.restore();
    }
}
