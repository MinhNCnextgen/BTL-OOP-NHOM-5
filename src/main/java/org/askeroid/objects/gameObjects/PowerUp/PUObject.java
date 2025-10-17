package org.askeroid.objects.gameObjects.PowerUp;

import javafx.scene.canvas.Canvas;
import org.askeroid.objects.gameObjects.GameObject;
import org.askeroid.objects.gameObjects.Paddle;
import org.askeroid.scenes.PlayScreen;

import java.util.ArrayList;
import java.util.Random;

public class PUObject extends GameObject {

    public static ArrayList<PUObject> PUObjectList = new ArrayList<PUObject>();
    private enum PowerUp {
        WidePaddle,
        FastBall,
        ExtraLife
    }
    private String imagePowerUp;
    private PowerUp powerUp;
    public boolean isActive;
    public float duration;
    public PUObject(double x, double y, double width, double height, double speed) {
        super(x, y, width, height, speed);
        powerUp = setPowerUp();
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

    private PowerUp setPowerUp() {
        PowerUp pu = getRandomPowerUp();
        switch (pu) {
            case WidePaddle:
                imagePowerUp = "images/powerup/wide_paddle.png";
                break;
            case FastBall:
                imagePowerUp = "images/powerup/fast_ball.png";
                break;
            case ExtraLife:
                imagePowerUp = "images/powerup/increase_health.png";
                break;
        }
        return pu;
    }

    private PowerUp getRandomPowerUp() {
        int pick = new Random().nextInt(PowerUp.values().length);
        return PowerUp.values()[pick];
    }

    private void grantPowerUp(PlayScreen ps) {
        switch (powerUp) {
            case WidePaddle:
                System.out.println("Wider Paddle");
                double oldWidth = ps.getPaddle().getWidth();
                double newWidth = Math.min(oldWidth * 1.3, 1000);
                double centerX = ps.getPaddle().getX() + oldWidth / 2;
                ps.getPaddle().setWidth(newWidth);
                ps.getPaddle().setX(centerX - newWidth / 2);
                break;
            case FastBall:
                System.out.println("Faster Ball");
                ps.getBall().setSpeed(ps.getBall().getSpeed() * 1.5);
                break;
            case ExtraLife:
                System.out.println("Extra Life");
                ps.getScoreManager().increaseHealth(1);
                break;
        }
    }

    public String getImagePowerUp() {
        return imagePowerUp;
    }
}
