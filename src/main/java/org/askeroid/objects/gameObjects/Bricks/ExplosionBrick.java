package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.GameObject;

import java.util.ArrayList;

public class ExplosionBrick extends Brick {

    private double explosionRadius;
    public static ArrayList<Mask> maskList = new ArrayList<Mask>();
    public ExplosionBrick(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.color = Color.RED;
        this.hits = 1;
        this.explosionRadius = Math.max(width, height) ; // 50% larger radius
    }

    // Circular mask centered on the explosion brick
    public class Mask extends GameObject {
        private double radius;
        private long timeCreated;
        public static ArrayList<Mask> maskList = new ArrayList<>();
        public Mask(double centerX, double centerY, double radius) {
            super(centerX - radius, centerY - radius, radius * 2, radius * 2, 0);
            this.radius = radius;
            this.timeCreated = System.nanoTime();
        }

        public boolean intersects(Brick b) {
            double dx = (b.getX() + b.getWidth() / 2) - (x + radius);
            double dy = (b.getY() + b.getHeight() / 2) - (y + radius);
            double distance = Math.sqrt(dx * dx + dy * dy);
            double brickRadius = Math.max(b.getWidth(), b.getHeight()) / 2.0;

            return distance < (radius + brickRadius);
        }

        public long getTimeCreated() { return timeCreated; }
    }

    @Override
    public void hit() {
        super.hit();
        if (!active) {
            explode();
        }
    }

    protected void explode() {
        System.out.println("ExplosionBrick at (" + x + ", " + y + ") exploded!");

        double centerX = x + width / 2;
        double centerY = y + height / 2;

        Mask mask = new Mask(centerX, centerY, explosionRadius);
        maskList.add(mask);
        for (Brick b : brickMap) {
            if (b != this && b.isActive() && mask.intersects(b)) {
                b.hit();
            }
        }
    }
}
