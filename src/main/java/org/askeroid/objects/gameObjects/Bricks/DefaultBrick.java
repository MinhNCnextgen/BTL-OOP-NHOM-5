package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;

public class DefaultBrick extends Brick {
    public DefaultBrick(double x, double y, double width, double height) {
        super(x, y, width, height); // Default brick takes 1 hit
        this.color = Color.GREEN;
        this.hits = 1;
    }
}
