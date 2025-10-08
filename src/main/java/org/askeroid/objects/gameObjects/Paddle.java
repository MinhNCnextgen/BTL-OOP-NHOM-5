package org.askeroid.objects.gameObjects;

import javafx.scene.input.KeyCode;
import org.askeroid.input.Keyboard;

public class Paddle extends GameObject {
    public Paddle(double x, double y, double width, double height, double speed) {
        super(x, y, width, height, speed);
    }

    public void update(Keyboard keyboard, double dt, double canvasWidth) {
        double dx = 0;
        dt /= 1_000_000_000.0;
        if (keyboard.isKeyPressed(KeyCode.LEFT) || keyboard.isKeyPressed(KeyCode.A)) {
            dx -= speed*dt;
        }


        if (keyboard.isKeyPressed(KeyCode.RIGHT) || keyboard.isKeyPressed(KeyCode.D)) {
            dx += speed * dt;
        }


        x += dx;

        // Keep paddle inside screen bounds
        if (x < 0) x = 0;
        if (x + width > canvasWidth) x = canvasWidth - width;
    }
}
