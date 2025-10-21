package org.askeroid.score;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreManager {
    public int score;
    private double multiplier;
    private int health;

    public ScoreManager(int hp) {
        this.score = 0;
        this.multiplier = 1.0;
        this.health = hp;
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

    public int getHealth() {
        return health;
    }

    public void decreaseHealth(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    public void increaseHealth(int amount) {
        health += amount;
    }

    public void resetScore() {
        score = 0;
    }
}
