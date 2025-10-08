package org.askeroid.objects.gameObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Balls extends GameObject {
    private double dx;
    private double dy;
    private double radius;
    private double canvasWidth;
    private double canvasHeight;
    private boolean stuckToPaddle = true;  // <-- new flag

    public Balls(double x, double y, double radius, double speed, double angle,
                double canvasWidth, double canvasHeight) {
        super(x, y, radius * 2, radius * 2, speed);
        this.radius = radius;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        // set initial velocity
        this.dx = Math.cos(Math.toRadians(angle)) * speed;
        this.dy = - Math.sin(Math.toRadians(angle)) * speed;
    }

    // Called every frame
    public void update(double dt, Paddle paddle) {
        dt /= 1_000_000_000.0;
        if (stuckToPaddle) {
            // stick to paddle's top center
            this.x = paddle.getX() + (paddle.getWidth() / 2) - radius;
            this.y = paddle.getY() - radius * 2 - 1;
            return; // don’t move while stuck
        }

        // Move ball
        x += dx * dt;
        y += dy * dt;

        // Wall collisions
        if (x <= 0 || x + radius * 4 >= canvasWidth) {
            dx *= -1;
            x = Math.max(0, Math.min(x, canvasWidth - radius * 2));
        }

        if (y <= 0) {
            dy *= -1;
            y = 0;
        }

        // Paddle collision
        if (checkCollision(paddle)) {
            dy *= -1;

            // Slight angle variation depending on hit position
            double hitPos = (x + radius) - (paddle.getX() + paddle.getWidth() / 2);
            dx = hitPos * 5; // tweak multiplier for control
            y = paddle.getY() - radius * 2;
        }

        // Fell below bottom
        if (y > canvasHeight + radius * 2) {
            reset(paddle);
        }
        // Print out all attributes for debugging
        System.out.println("Canvas Size: (" + canvasWidth + ", " + canvasHeight + ")");
        System.out.println("Ball Position: (" + x + ", " + y + "), Velocity: (" + dx + ", " + dy + ")");
        System.out.println("Paddle Position: (" + paddle.getX() + ", " + paddle.getY() + ")");
    }

    private boolean checkCollision(Paddle paddle) {
        return x + radius * 2 > paddle.getX() &&
                x < paddle.getX() + paddle.getWidth() &&
                y + radius * 2 > paddle.getY() &&
                y < paddle.getY() + paddle.getHeight();
    }

    // reset after fall
    public void reset(Paddle paddle) {
        System.out.println("Ball lost! Resetting position.");
        stuckToPaddle = true;
        this.dx = Math.cos(Math.toRadians(45)) * speed;
        this.dy = Math.sin(Math.toRadians(45)) * speed;
    }

    public void launch() {
        if (!stuckToPaddle) return;
        stuckToPaddle = false;
        dx = 0;
        dy = -speed;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(x, y, radius * 2, radius * 2);
    }

    public double getRadius() {
        return radius;
    }
}
