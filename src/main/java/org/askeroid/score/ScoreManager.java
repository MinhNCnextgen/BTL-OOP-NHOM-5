package org.askeroid.score;

public class ScoreManager {
    private double score;
    private double multiplier;

    public ScoreManager() {
        this.score = 0;
        this.multiplier = 1;
    }

    public void increaseScore(double points) {
        score += points;
    }

    public void addScore(double basePoints) {
        score += basePoints * multiplier;
    }

    public void increaseMultiplier() {
        multiplier += 0.1; // Increase by 10%
    }

    public void resetMultiplier() {
        multiplier = 1;
    }

    public double getScore() {
        return score;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void resetScore() {
        score = 0;
        resetMultiplier();
    }
}
