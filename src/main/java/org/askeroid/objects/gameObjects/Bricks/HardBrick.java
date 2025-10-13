package org.askeroid.objects.gameObjects.Bricks;

import javafx.scene.paint.Color;

public class HardBrick extends Brick {
    public HardBrick(double x, double y, double width, double height) {
        super(x, y, width, height); // Hard brick takes 3 hits
        this.color = Color.ORANGE;
        this.hits = 3;
    }

    private Color getColorForHits(int hits) {
        switch (hits) {
            case 1: return Color.GREEN;
            case 2: return Color.YELLOW;
            case 3: return Color.ORANGE;
            default: return Color.RED;
        }
    }

    @Override
    public void hit() {
        super.hit();
        if (active) {
            this.color = getColorForHits(hits);
        }
    }

}
