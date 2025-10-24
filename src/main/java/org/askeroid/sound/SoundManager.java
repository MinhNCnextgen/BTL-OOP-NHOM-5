package org.askeroid.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code SoundManager} là lớp quản lý âm thanh trung tâm của trò chơi.
 * Lớp này sử dụng mẫu Singleton để đảm bảo chỉ có một đối tượng duy nhất
 * điều khiển việc phát nhạc nền và hiệu ứng âm thanh (sound effects).
 * Âm thanh được tải từ thư mục {@code resources/sounds/} trong project.
 * Hỗ trợ bật/tắt toàn bộ âm thanh, phát nhạc nền lặp lại và điều chỉnh âm lượng.
 */
public class SoundManager {

    /** Thể hiện duy nhất của SoundManager (Singleton instance). */
    private static SoundManager instance;

    /** Lưu trữ các hiệu ứng âm thanh (sound effects) theo tên. */
    private final Map<String, MediaPlayer> soundEffects = new HashMap<>();

    /** Đối tượng MediaPlayer cho nhạc nền. */
    private MediaPlayer backgroundMusic;

    /** Cờ bật/tắt âm thanh. */
    private boolean soundEnabled = true;

    /** Âm lượng hiệu ứng âm thanh (0.0 - 1.0). */
    private double sfxVolume = 1.0;

    /** Âm lượng nhạc nền (0.0 - 1.0). */
    private double musicVolume = 0.2;

    /**
     * Hàm khởi tạo riêng tư để đảm bảo chỉ có một thể hiện duy nhất (Singleton).
     * Khi khởi tạo, hàm sẽ tự động tải toàn bộ âm thanh cần thiết.
     */
    private SoundManager() {
        loadSounds();
    }

    /**
     * Trả về thể hiện duy nhất của {@code SoundManager}.
     * Nếu chưa tồn tại, phương thức sẽ tạo mới.
     *
     * @return thể hiện duy nhất của {@code SoundManager}
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Tải tất cả các tệp âm thanh (sound effects và nhạc nền) vào bộ nhớ.
     * Các tệp cần được đặt trong thư mục {@code resources/sounds/}.
     * Nếu tệp không tồn tại, thông báo lỗi sẽ được in ra console.
     */
    private void loadSounds() {
        try {
            // load sound effects
            loadSound("click", "sounds/click.wav");
            loadSound("hit", "sounds/hit.wav");
            loadSound("gameover", "sounds/gameover.wav");
            loadSound("powerup", "sounds/powerup.wav");
            loadSound("win", "sounds/win.wav");

            // load background music
            URL bgUrl = getClass().getClassLoader().getResource("sounds/background.wav");
            if (bgUrl != null) {
                Media bgMedia = new Media(bgUrl.toExternalForm());
                backgroundMusic = new MediaPlayer(bgMedia);
                backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE); // lặp vô hạn
                backgroundMusic.setVolume(musicVolume);
            }
        } catch (Exception e) {
            System.err.println("Error loading sounds: " + e.getMessage());
        }
    }

    /**
     * Tải một hiệu ứng âm thanh cụ thể vào bộ nhớ.
     *
     * @param name tên định danh của hiệu ứng (ví dụ: "click", "hit")
     * @param path đường dẫn tới tệp âm thanh (ví dụ: "sounds/click.wav")
     */
    private void loadSound(String name, String path) {
        try {
            URL url = getClass().getClassLoader().getResource(path);
            if (url != null) {
                Media media = new Media(url.toExternalForm());
                MediaPlayer player = new MediaPlayer(media);
                player.setVolume(sfxVolume);
                soundEffects.put(name, player);
            } else {
                System.err.println("Sound file not found: " + path);
            }
        } catch (Exception e) {
            System.err.println("Error loading sound " + name + ": " + e.getMessage());
        }
    }

    /**
     * Phát một hiệu ứng âm thanh theo tên.
     * Nếu âm thanh bị tắt, phương thức sẽ không làm gì.
     * Nếu âm thanh tồn tại, nó sẽ được phát lại từ đầu.
     *
     * @param name tên hiệu ứng âm thanh cần phát
     */
    public void playSound(String name) {
        if (!soundEnabled) return;

        MediaPlayer player = soundEffects.get(name);
        if (player != null) {
            player.stop();
            player.seek(Duration.ZERO);
            player.play();
        }
    }

    /**
     * Bắt đầu phát nhạc nền (và lặp vô hạn).
     * Nếu âm thanh bị tắt, phương thức sẽ không làm gì.
     */
    public void playBackgroundMusic() {
        if (backgroundMusic != null && soundEnabled) {
            backgroundMusic.play();
        }
    }

    /**
     * Dừng phát nhạc nền.
     */
    public void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    /**
     * Bật hoặc tắt toàn bộ âm thanh (bao gồm cả nhạc nền và hiệu ứng).
     *
     * @param enabled {@code true} để bật âm thanh, {@code false} để tắt
     */
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
        if (!enabled) {
            stopBackgroundMusic();
        } else {
            playBackgroundMusic();
        }
    }

    /**
     * Kiểm tra trạng thái bật/tắt âm thanh.
     *
     * @return {@code true} nếu âm thanh được bật, ngược lại {@code false}
     */
    public boolean isSoundEnabled() {
        return soundEnabled;
    }
}
