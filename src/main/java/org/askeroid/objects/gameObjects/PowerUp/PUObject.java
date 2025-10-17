package org.askeroid.objects.gameObjects.PowerUp;

import javafx.scene.canvas.Canvas;
import org.askeroid.objects.gameObjects.GameObject;
import org.askeroid.objects.gameObjects.Paddle;
import org.askeroid.scenes.PlayScreen;

import java.util.ArrayList;
import java.util.Random;


public class PUObject extends GameObject {

    public static ArrayList<PUObject> PUObjectList = new ArrayList<PUObject>();

    private String imagePowerUp;
    private PowerUpType powerUp;
    public boolean isActive;
    public PUObject(double x, double y, double width, double height, double speed, PowerUpType type) {
        super(x, y, width, height, speed);
        if (type == null) {
            this.powerUp = getRandomPowerUp();
        } else {
            this.powerUp = type;
        }
        imagePowerUp = powerUp.getImagePath();
        isActive = true;
    }

    public void update(double dt, Canvas canvas, PlayScreen ps) {
        dt /= 1_000_000_000.0;
        y += speed * dt;
        if (intersects(ps.getPaddle())) {
            grantPowerUp(ps);
            isActive = false;
        }
        if (y > canvas.getHeight()) {
            isActive = false;
        }
    }

    private PowerUpType getRandomPowerUp() {
        int pick = new Random().nextInt(PowerUpType.values().length);
        return PowerUpType.values()[pick];
    }

    private void grantPowerUp(PlayScreen ps) {
        ps.getPowerUpManager().applyPowerUp(powerUp);
    }

    public String getImagePowerUp() {
        return imagePowerUp;
    }
}
