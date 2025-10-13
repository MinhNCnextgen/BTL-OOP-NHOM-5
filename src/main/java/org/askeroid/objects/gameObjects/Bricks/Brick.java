package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.GameObject;

public abstract class Bricks extends GameObject {
    protected Color color;
    protected boolean active;
    protected int hits;
    public Bricks(double x, double y, double width, double height) {
        super(x, y, width, height, 0);
        this.active = true;
    }

    public void hit() {
        hits--;
        if (hits <= 0) {
            active = false;
        }
    }

    public boolean isActive() { return active; }
    public Color getColor() { return color; }
}
