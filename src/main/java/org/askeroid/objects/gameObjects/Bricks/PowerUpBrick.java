package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;

public class PowerUpBrick extends Brick {
    public PowerUpBrick(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.hits = 1;
        this.color = Color.GOLD;
    }

    @Override
    public void hit() {
        super.hit();
        if (!active) {
        }
    }
}
