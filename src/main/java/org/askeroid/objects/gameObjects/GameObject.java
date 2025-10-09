package org.askeroid.objects.gameObjects;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import org.askeroid.graphics.Renderer;

public abstract class GameObject {
    protected double x, y, width, height, speed;

    public GameObject(double x, double y, double width, double height, double speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public boolean intersects(GameObject other) {
        return x < other.x + other.width &&
                x + width > other.x &&
                y < other.y + other.height &&
                y + height > other.y;
    }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}
