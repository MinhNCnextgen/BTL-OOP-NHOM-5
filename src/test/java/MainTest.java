import org.junit.Test;

import org.askeroid.Main;
import org.askeroid.score.ScoreManager;

import static org.junit.Assert.assertEquals;

public class MainTest {
    @Test
    public void testDecreaseHealth_1() {
        ScoreManager scoreManager = new ScoreManager(2);
        scoreManager.decreaseHealth(1);
        assertEquals(1, scoreManager.getHealth());
    }

    @Test
    public void testDecreaseHealth_2() {
        ScoreManager scoreManager = new ScoreManager(2);
        scoreManager.decreaseHealth(1);
        assertEquals(1, scoreManager.getHealth());
    }

    @Test
    public void testDecreaseHealth_3() {
        ScoreManager scoreManager = new ScoreManager(1);
        scoreManager.decreaseHealth(2);
        assertEquals(0, scoreManager.getHealth());
    }

    @Test
    public void testIncreaseMultiplier_1() {
        ScoreManager scoreManager = new ScoreManager(3);
        double newMultiplier = scoreManager.increaseMultiplier(0.5);
        assertEquals(1.5, newMultiplier, 0.0001);
    }

    @Test
    public void testIncreaseMultiplier_2() {
        ScoreManager scoreManager = new ScoreManager(3);
        double newMultiplier = scoreManager.increaseMultiplier(2.0);
        assertEquals(3.0, newMultiplier, 0.0001);
    }

    @Test
    public void testInitialHealth_Zero() {
        ScoreManager scoreManager = new ScoreManager(0);
        assertEquals(0, scoreManager.getHealth());
    }

    @Test
    public void testInitialHealth() {
        ScoreManager scoreManager = new ScoreManager(4);
        assertEquals(4, scoreManager.getHealth());
    }

    @Test
    public void testInitialScore() {
        ScoreManager scoreManager = new ScoreManager(3);
        assertEquals(0, scoreManager.getScore());
    }

    @Test
    public void testGetHealth() {
        ScoreManager scoreManager = new ScoreManager(5);
        assertEquals(5, scoreManager.getHealth());
    }

    @Test
    public void testGetMultiplier() {
        ScoreManager scoreManager = new ScoreManager(3);
        assertEquals(1.0, scoreManager.getMultiplier(), 0.0001);
    }

    @Test
    public void testGetScore() {
        ScoreManager scoreManager = new ScoreManager(3);
        scoreManager.addScore(100, 1.0);
        assertEquals(100, scoreManager.getScore());
    }

    @Test
    public void testResetMultiplier() {
        ScoreManager scoreManager = new ScoreManager(3);
        scoreManager.resetMultiplier();
        assertEquals(1.0, scoreManager.getMultiplier(), 0.0001);
    }

    @Test
    public void testAddScore_1() {
        ScoreManager scoreManager = new ScoreManager(3);
        scoreManager.addScore(100, 2.0);
        assertEquals(200, scoreManager.getScore());
    }

    @Test
    public void testAddScore_2() {
        ScoreManager scoreManager = new ScoreManager(3);
        scoreManager.addScore(50, 1.5);
        assertEquals(75, scoreManager.getScore());
    }

    @Test
    public void testAddHealth_1() {
        ScoreManager scoreManager = new ScoreManager(1);
        scoreManager.increaseHealth(2);
        assertEquals(3, scoreManager.getHealth());
    }

    @Test
    public void testAddHealth_2() {
        ScoreManager scoreManager = new ScoreManager(0);
        scoreManager.increaseHealth(5);
        assertEquals(5, scoreManager.getHealth());
    }

    @Test
    public void testResetScore() {
        ScoreManager scoreManager = new ScoreManager(3);
        scoreManager.addScore(100, 1.0);
        scoreManager.resetScore();
        assertEquals(0, scoreManager.getScore());
    }
}
