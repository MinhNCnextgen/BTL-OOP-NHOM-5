package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.GameObject;

import java.util.ArrayList;

public abstract class Brick extends GameObject {
    protected Color color;
    protected boolean active;
    protected int hits;
    protected int maxHits;
    public static ArrayList<Brick> brickMap ;
    public Brick(double x, double y, double width, double height) {
        super(x, y, width, height, 0); // Brick don't move
        this.hits = hits;
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
