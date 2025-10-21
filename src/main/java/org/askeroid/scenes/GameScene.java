package org.askeroid.scenes;

import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;
import org.askeroid.input.Keyboard;

/**
 * Lớp trừu tượng {@code GameScene} định nghĩa cấu trúc chung cho mọi màn chơi (scene)
 * trong trò chơi Askeroid.
 * <p>
 * Mỗi màn chơi bao gồm một {@link Scene}, {@link Canvas} để vẽ,
 * {@link Renderer} để hiển thị đồ họa, và {@link Keyboard} để xử lý đầu vào.
 * <p>
 * Các lớp con (như MenuScene, PlayScene, v.v.) sẽ kế thừa và định nghĩa cụ thể
 * hành vi cập nhật (update) và hiển thị (render).
 */
public abstract class GameScene {

    protected Scene scene;
    protected Pane root;
    protected Canvas canvas;
    protected GraphicsContext gc;
    protected final Renderer renderer;
    protected Keyboard keyboard;
    protected SceneManager sceneManager;
    protected Stage primaryStage;

    /**
     * Khởi tạo một {@code GameScene} mới.
     *
     * @param renderer      bộ vẽ (renderer) dùng để hiển thị nội dung game
     * @param sceneManager  bộ quản lý cảnh (chuyển đổi giữa các màn)
     * @param primaryStage  cửa sổ chính của trò chơi
     */
    public GameScene(Renderer renderer, SceneManager sceneManager, Stage primaryStage) {
        this.renderer = renderer;
        this.root = new Pane();
        this.canvas = new Canvas();

        // Canvas tự động mở rộng theo kích thước Pane
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());

        this.gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        // Khởi tạo Scene với kích thước mặc định
        this.scene = new Scene(root, 600, 800);

        this.keyboard = new Keyboard(scene);
        this.sceneManager = sceneManager;
        this.primaryStage = primaryStage;
    }

    /** Cập nhật logic của màn chơi, được gọi mỗi khung hình  */
    public abstract void update(double dt);

    /** Vẽ toàn bộ nội dung màn chơi lên màn hình. */
    public abstract void renderScreen(Renderer renderer);

    /** Liên kết các thao tác bàn phím hoặc chuột với hành động cụ thể trong game. */
    protected abstract void bindInputs();

    /** Gọi khi màn chơi bắt đầu được hiển thị. */
    public void onEnter() {}

    /** Gọi khi rời khỏi màn chơi hiện tại. */
    public void onExit() {}

    // ----------------------------------------------------------------------
    // Getter / Setter
    // ----------------------------------------------------------------------

    public Canvas getCanvas() {
        return canvas;
    }

    public GraphicsContext getGraphicsContext() {
        return gc;
    }

    public Scene getScene() {
        return scene;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
