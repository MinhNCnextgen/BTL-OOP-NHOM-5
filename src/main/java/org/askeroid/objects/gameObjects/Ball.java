package org.askeroid.objects.gameObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.Bricks.Brick;
import org.askeroid.sound.SoundManager;

import java.util.ArrayList;

public class Ball extends GameObject {
    private double dx;
    private double dy;
    private double radius;
    private double canvasWidth;
    private double canvasHeight;
    private boolean stuckToPaddle = true;  // <-- new flag
    private SoundManager soundManager;

    public Ball(double x, double y, double radius, double speed, double angle,
                double canvasWidth, double canvasHeight) {
        super(x, y, radius * 2, radius * 2, speed);
        this.soundManager = SoundManager.getInstance();
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
            return;
        }

        // Move ball
        x += dx * dt;
        y += dy * dt;

        // Wall collisions
        if (x <= 0 || x + radius * 2 >= canvasWidth) {
            dx *= -1;
            x = Math.max(0, Math.min(x, canvasWidth - radius * 2));
        }

        if (y <= 0) {
            dy *= -1;
            y = 0;
        }

        // Paddle collision
        if (intersects(paddle)) {
            dy *= -1;

            // Slight angle variation depending on hit position
            double hitPos = (x + radius) - (paddle.getX() + paddle.getWidth() / 2);
            dx = hitPos * 5; // tweak multiplier for control
            y = paddle.getY() - radius * 2;
        }

        // Check if ball collides

        for (Brick brick : brickList) {
            if (!brick.isActive()) {
                continue;
            }
            if (intersects(brick)) {
                soundManager.playSound("hit");

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

                double hitPos = (x + radius) - (brick.getX() + brick.getWidth() / 2);
                dx += hitPos * 0.05;

                brick.hit();
                break;
            }
        }
    }

    // reset after fall
    public void reset(Paddle paddle) {
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

    public double getRadius() {return radius;}
    public double getSpeed() {return speed;}
    public void setSpeed(double val) {
        this.speed = val;
        // Preserve direction (normalize current velocity vector)
        double length = Math.sqrt(dx * dx + dy * dy);
        if (length != 0) {
            dx = (dx / length) * speed;
            dy = (dy / length) * speed;
        }
    }
}
