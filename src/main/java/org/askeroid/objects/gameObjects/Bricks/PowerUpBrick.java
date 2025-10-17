package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.PowerUp.PUObject;
import org.askeroid.objects.gameObjects.PowerUp.PowerUpType;

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
            PUObject.PUObjectList.add(new PUObject(x+10, y, 40, 40, 450, null));
        }
    }
}
