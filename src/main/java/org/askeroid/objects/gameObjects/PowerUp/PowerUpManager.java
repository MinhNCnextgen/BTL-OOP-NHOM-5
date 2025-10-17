package org.askeroid.objects.gameObjects.PowerUp;

import org.askeroid.objects.gameObjects.Paddle;
import org.askeroid.objects.gameObjects.Ball;
import org.askeroid.scenes.PlayScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PowerUpManager {
    private class PowerUpInstance {
        PowerUpType type;
        long time_created;
        public PowerUpInstance(PowerUpType type, long time_created) {
            this.type = type;
            this.time_created = time_created;
        }
    }
    private static final ArrayList<PowerUpInstance> activePowerUps = new ArrayList<>();
    PlayScreen screen;
    public PowerUpManager(PlayScreen screen) {
       this.screen = screen;
    }
    public void applyPowerUp(PowerUpType type) {
        Paddle paddle = screen.getPaddle();
        Ball ball = screen.getBall();

        switch (type) {
            case WidePaddle:
                System.out.println("Wider Paddle");
                double oldWidth = paddle.getWidth();
                double newWidth = Math.min(oldWidth * 1.3, 1000);
                double centerX = paddle.getX() + oldWidth / 2;
                paddle.setWidth(newWidth);
                paddle.setX(centerX - newWidth / 2);
                break;
            case FastBall:
                System.out.println("Faster Ball");
                ball.setSpeed(ball.getSpeed() * 1.5);
                break;
            case SlowBall:
                System.out.println("Slower Ball");
                ball.setSpeed(ball.getSpeed() * 0.8);
                break;
            case ExtraLife:
                System.out.println("Extra Life");
                screen.getScoreManager().increaseHealth(1);
                return; // no duration, permanent
        }

        // store expiration timestamp
        activePowerUps.add(new PowerUpInstance(type, System.currentTimeMillis()));
    }

    public void update() {
        long now = System.currentTimeMillis();
        for (PowerUpInstance powerUp : activePowerUps) {
            if (powerUp.time_created + powerUp.type.getDuration() < now) {
                resetPowerUp(powerUp.type);
            }
        }
        activePowerUps.removeIf(powerUp -> powerUp.time_created + powerUp.type.getDuration() <= now);
    }

    private void resetPowerUp(PowerUpType type) {
        Paddle paddle = screen.getPaddle();
        Ball ball = screen.getBall();
        switch (type) {
            case WidePaddle:
                System.out.println("Reset Wide Paddle");
                double oldWidth = paddle.getWidth();
                double newWidth = oldWidth / 1.3;
                double centerX = paddle.getX() + oldWidth / 2;
                paddle.setWidth(newWidth);
                paddle.setX(centerX - newWidth / 2);
                break;

            case FastBall:
                System.out.println("Reset Fast Ball");
                ball.setSpeed(ball.getSpeed() / 1.5);
                break;

            case SlowBall:
                System.out.println("Reset Slow Ball");
                ball.setSpeed(ball.getSpeed() / 0.8);
                break;

            default:
                break;
        }
    }
}
