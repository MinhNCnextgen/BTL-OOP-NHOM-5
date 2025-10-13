package org.askeroid.scenes;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;
import org.askeroid.objects.buttons.GameButton;

import java.util.HashMap;

public class  MainMenu extends GameScene {

    private final HashMap<String, GameButton> buttons;

    public MainMenu(Renderer renderer, SceneManager sceneManager, Stage primaryStage) {
        super(renderer, sceneManager, primaryStage);
        buttons = new HashMap<>();
        buttons.put("play", new GameButton(150, 270, 300, 150, "images/buttons/play.png", this));
        buttons.put("exit", new GameButton(150, 480, 300, 150, "images/buttons/quit.png", this));
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
    }

    protected void bindInputs() {
        // Set button callbacks
        buttons.get("play").setCallback(button -> {
            System.out.println("Starting Game...");
            sceneManager.setScene("play", primaryStage);
        });
        buttons.get("exit").setCallback(button -> {
            System.out.println("Exiting Game...");
            System.exit(0);
        });
        //Bind Mouse Clicks
        keyboard.setOnMouseClick(e -> {
            for (GameButton button : buttons.values()) {
                if (keyboard.isOverRegion(button.getX(), button.getY(), button.getWidth(), button.getHeight())) {
                    button.triggerCallback();
                }
            }
        });
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
        System.out.println("Entered Main Menu");
    }
}
