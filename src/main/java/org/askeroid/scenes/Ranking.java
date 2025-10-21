package org.askeroid.scenes;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;
import org.askeroid.objects.buttons.GameButton;
import org.askeroid.sound.SoundManager;

import java.util.ArrayList;
import java.util.HashMap;
import org.askeroid.utils.ScoreRanking;

public class Ranking extends GameScene {
    private final HashMap<String, GameButton> buttons;
    private SoundManager soundManager;

    public Ranking(Renderer renderer, SceneManager sceneManager, Stage primaryStage) {
        super(renderer, sceneManager, primaryStage);
        soundManager = SoundManager.getInstance();
        buttons = new HashMap<>();
        buttons.put("home", new GameButton(15, root.getHeight() - 110, 100, 100, "images/buttons/home.png", this));
        bindInputs();
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void renderScreen(Renderer renderer) {
        this.gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        renderer.drawBackground(gc, "images/background/main_background.png");
        for (GameButton button : buttons.values()) {
            button.renderButton();
        }
        RenderScore();
    }

    @Override
    protected void bindInputs() {
        buttons.get("home").setCallback(button -> {
            soundManager.playSound("click");
            System.out.println("Returning to Main Menu...");
            sceneManager.setScene("menu", primaryStage);
        });

        // Bind Mouse Clicks
        keyboard.setOnMouseClick(e -> {
                    for (GameButton button : buttons.values()) {
                        if (keyboard.isOverRegion(button.getX(), button.getY(), button.getWidth(), button.getHeight())) {
                            soundManager.playSound("click");
                            button.triggerCallback();
                        }
                    }
                }
        );
        // Hover Effect
        keyboard.setOnMouseMove(e -> {
            boolean hovered = false;
            for (GameButton button : buttons.values()) {
                if (keyboard.isOverRegion(button.getX(), button.getY(), button.getWidth(), button.getHeight())) {
                    hovered = true;
                    break;
                }
            }
            if (hovered) {
                scene.setCursor(Cursor.HAND);
            } else {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
    }

    @Override
    public void onEnter() {
        System.out.println("Entered Ranking Scene");
        scene.setCursor(javafx.scene.Cursor.DEFAULT);
    }

    private void RenderScore() {
        ArrayList<Integer> scores = ScoreRanking.readScore();
        int startY = 250;
        int rank = 1;

        for (Integer score : scores) {
            String scoreText = rank + ". " + score;
            renderer.drawText(gc, scoreText, root.getWidth() / 2 - 50, startY, "Times New Roman", 36, javafx.scene.paint.Color.WHITE);
            startY += 60;
            rank++;
        }
    }
}
