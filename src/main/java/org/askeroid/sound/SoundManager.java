package org.askeroid.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static SoundManager instance;
    private final Map<String, MediaPlayer> soundEffects = new HashMap<>();
    private MediaPlayer backgroundMusic;
    private boolean soundEnabled = true;
    private double sfxVolume = 1.0;
    private double musicVolume = 0.2;

    private SoundManager() {
        loadSounds();
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

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
                backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
                backgroundMusic.setVolume(musicVolume);
            }
        } catch (Exception e) {
            System.err.println("Error loading sounds: " + e.getMessage());
        }
    }

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

    public void playSound(String name) {
        if (!soundEnabled) return;

        MediaPlayer player = soundEffects.get(name);
        if (player != null) {
            player.stop();
            player.seek(Duration.ZERO);
            player.play();
        }
    }

    public void playBackgroundMusic() {
        if (backgroundMusic != null && soundEnabled) {
            backgroundMusic.play();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
        if (!enabled) {
            stopBackgroundMusic();
        } else {
            playBackgroundMusic();
        }
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }
}