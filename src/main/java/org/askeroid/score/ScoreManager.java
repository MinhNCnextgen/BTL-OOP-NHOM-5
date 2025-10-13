package org.askeroid.score;

public class ScoreManager {
    private int score;
    private double multiplier;

    public ScoreManager() {
        this.score = 0;
        this.multiplier = 1.0;
    }

    public void addScore(int points, double multiplier) {
        score += points * multiplier;
    }

    public double increaseMultiplier(double amount) {
        multiplier += amount;
        return multiplier;
    }

    public void resetMultiplier() {
        multiplier = 1.0;
    }

    public int getScore() {
        return score;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void resetScore() {
        score = 0;
    }
}
