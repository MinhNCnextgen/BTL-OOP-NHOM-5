package org.askeroid.score;

/**
 * Lớp ScoreManager quản lý điểm số, máu (health)
 * và hệ số nhân điểm (multiplier) trong trò chơi.
 *
 * Cung cấp các phương thức để cộng điểm, điều chỉnh multiplier,
 * và xử lý khi người chơi mất hoặc hồi máu.
 */
public class ScoreManager {

    private int score;
    private double multiplier;
    private int health;

    /**
     * Khởi tạo đối tượng ScoreManager với lượng máu ban đầu.
     *
     * @param hp máu khởi tạo của người chơi
     */
    public ScoreManager(int hp) {
        this.score = 0;
        this.multiplier = 1.0;
        this.health = hp;
    }

    /**
     * Cộng điểm vào tổng điểm hiện tại.
     *
     * @param points      số điểm cộng thêm
     * @param multiplier  hệ số nhân điểm hiện tại
     */
    public void addScore(int points, double multiplier) {
        score += points * multiplier;
    }

    /**
     * Tăng hệ số nhân điểm (multiplier) hiện tại.
     *
     * @param amount giá trị tăng thêm
     * @return hệ số multiplier sau khi tăng
     */
    public double increaseMultiplier(double amount) {
        multiplier += amount;
        return multiplier;
    }

    /**
     * Đặt lại hệ số nhân điểm về 1.0 (trạng thái mặc định).
     */
    public void resetMultiplier() {
        multiplier = 1.0;
    }

    /**
     * Lấy tổng điểm hiện tại.
     *
     * @return điểm số hiện tại
     */
    public int getScore() {
        return score;
    }

    /**
     * Lấy hệ số nhân điểm hiện tại.
     *
     * @return multiplier hiện tại
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * Lấy lượng máu hiện tại của người chơi.
     *
     * @return giá trị health hiện tại
     */
    public double getHealth() {
        return health;
    }

    /**
     * Giảm lượng máu của người chơi.
     * Nếu máu giảm xuống dưới 0 thì được đặt về 0.
     *
     * @param amount số máu bị trừ
     */
    public void decreaseHealth(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    /**
     * Tăng lượng máu của người chơi.
     *
     * @param amount số máu hồi thêm
     */
    public void increaseHealth(int amount) {
        health += amount;
    }

    /**
     * Đặt lại điểm số về 0.
     */
    public void resetScore() {
        score = 0;
    }
}
