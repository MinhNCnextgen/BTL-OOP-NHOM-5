package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.GameObject;

public class Bricks extends GameObject {
    protected Color color;
    protected boolean active;
    protected int hits;
    protected int maxHits;

    public Bricks(double x, double y, double width, double height, int hits) {
        super(x, y, width, height, 0); // Bricks don't move
        this.hits = hits;
        this.maxHits = hits;
        this.color = getColorForHits(hits);
        this.active = true;
    }

    protected static Color getColorForHits(int hits) {
        switch (hits) {
            case 1: return Color.GREEN;
            case 2: return Color.YELLOW;
            case 3: return Color.ORANGE;
            default: return Color.RED;
        }
    }
    public void hit() {
        hits--;
        if (hits <= 0) {
            active = false;
        } else {
            color = getColorForHits(hits);
        }
    }
    public boolean isActive() { return active; }
    public Color getColor() { return color; }
}
