package org.askeroid.objects.buttons;

import java.util.function.Consumer;
import org.askeroid.scenes.GameScene;

public class GameButton {
    private final double x;
    private final double y;
    private final double width;
    private final double height;
    private final GameScene gameScene;
    private final String imagePath;
    private Consumer<GameButton> callback;
    public GameButton(double x, double y, double width, double height, String imagePath, GameScene gameScene) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;
        this.gameScene = gameScene;
    }

    public void renderButton() {
        gameScene.getRenderer().drawImage(gameScene.getGraphicsContext(), this.imagePath, x, y, width, height);
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setCallback(Consumer<GameButton> callback) {
        this.callback = callback;
    }

    public void triggerCallback() {
        if (callback != null) {
            callback.accept(this);
        }
    }
}


