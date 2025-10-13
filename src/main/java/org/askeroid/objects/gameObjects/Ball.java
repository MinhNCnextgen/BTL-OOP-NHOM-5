package org.askeroid.objects.gameObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.Bricks.Brick;

import java.util.ArrayList;

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
    public void update(double dt, Paddle paddle, ArrayList<Brick> brickList) {
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

        // Check if ball collides

        for (Brick brick : brickList) {
            if (!brick.isActive()) continue;
            if (checkCollision(brick)) {

                // Compute overlap on both axes
                double overlapLeft   = (x + radius * 2) - brick.getX();
                double overlapRight  = (brick.getX() + brick.getWidth()) - x;
                double overlapTop    = (y + radius * 2) - brick.getY();
                double overlapBottom = (brick.getY() + brick.getHeight()) - y;

                // Find the smallest overlap → that’s the collision side
                double minOverlapX = Math.min(overlapLeft, overlapRight);
                double minOverlapY = Math.min(overlapTop, overlapBottom);

                if (minOverlapX < minOverlapY) {
                    // Horizontal collision (left or right)
                    dx *= -1;

                    if (overlapLeft < overlapRight) {
                        // Hit brick from left
                        x = brick.getX() - radius * 2 - 0.1;
                    } else {
                        // Hit brick from right
                        x = brick.getX() + brick.getWidth() + 0.1;
                    }

                } else {
                    // Vertical collision (top or bottom)
                    dy *= -1;

                    if (overlapTop < overlapBottom) {
                        // Hit from top
                        y = brick.getY() - radius * 2 - 0.1;
                    } else {
                        // Hit from bottom
                        y = brick.getY() + brick.getHeight() + 0.1;
                    }
                }

                // Optional: tweak horizontal bounce slightly
                double hitPos = (x + radius) - (brick.getX() + brick.getWidth() / 2);
                dx += hitPos * 1.2;

                brick.hit();
                break; // only handle one collision per frame
            }
        }


//        // Print out all attributes for debugging
//        System.out.println("Canvas Size: (" + canvasWidth + ", " + canvasHeight + ")");
//        System.out.println("Ball Position: (" + x + ", " + y + "), Velocity: (" + dx + ", " + dy + ")");
//        System.out.println("Paddle Position: (" + paddle.getX() + ", " + paddle.getY() + ")");
    }

    private boolean checkCollision(Paddle paddle) {
        return x + radius * 2 > paddle.getX() &&
                x < paddle.getX() + paddle.getWidth() &&
                y + radius * 2 > paddle.getY() &&
                y < paddle.getY() + paddle.getHeight();
    }

    private boolean checkCollision(Brick brick) {
        return x + radius * 2 > brick.getX() &&
                x < brick.getX() + brick.getWidth() &&
                y + radius * 2 > brick.getY() &&
                y < brick.getY() + brick.getHeight();
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
