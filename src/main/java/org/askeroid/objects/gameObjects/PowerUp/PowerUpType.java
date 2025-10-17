package org.askeroid.objects.gameObjects.PowerUp;

public enum PowerUpType {
    WidePaddle("images/powerup/wide_paddle.png", 5000),
    FastBall("images/powerup/fast_ball.png", 5000),
    SlowBall("images/powerup/slow_ball.png", 5000),
    ExtraLife("images/powerup/increase_health.png", 0);

    private final String imagePath;
    private final long duration;
    PowerUpType(String path, long duration) {
        this.duration = duration;
        this.imagePath = path;
    }
    public long getDuration() {
        return duration;
    }
    public String getImagePath() {
        return imagePath;
    }
}

