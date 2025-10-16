package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.PowerUp.PUObject;

public class PowerUpBrick extends Brick {
    public PowerUpBrick(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.hits = 1;
        this.color = Color.PINK;
    }

    @Override
    public void hit() {
        super.hit();
        if (!active) {
            PUObject.PUObjectList.add(new PUObject(x, y, width, height, 500));
        }
    }
}
