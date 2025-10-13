package org.askeroid.scenes;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.askeroid.graphics.Renderer;
import org.askeroid.objects.buttons.GameButton;

import java.util.HashMap;

public class EndScreen extends GameScene{
    private final HashMap<String, GameButton> buttons;
    private String resultMessage;
    private int finalScore;
    private boolean isWin = false;

    public EndScreen(Renderer renderer, SceneManager sceneManager, Stage primaryStage) {
        super(renderer, sceneManager, primaryStage);
        buttons = new HashMap<>();

        buttons.put("home", new GameButton(200, 400, 80, 80, "images/buttons/home.png", this));
        buttons.put("replay", new GameButton(320, 400, 80, 80, "images/buttons/replay.png", this));
        bindInputs();
    }

    public void setGameResult(boolean isWin, int score) {
        this.isWin = isWin;
        this.finalScore = score;
        this.resultMessage = isWin ? "YOU WIN!" : "GAME OVER";
    }

    @Override
    public void update(double dt) {
    }

    @Override
    public void renderScreen(Renderer renderer) {
        this.gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        renderer.drawBackground(gc, "images/background/play_background.jpg");

        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Color resultColor = isWin ? Color.rgb(0, 255, 100) : Color.rgb(255, 50, 50);
        gc.setFill(resultColor);
        gc.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 60));

        javafx.scene.text.Text tempText = new javafx.scene.text.Text(resultMessage);
        tempText.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 60));
        double textWidth = tempText.getLayoutBounds().getWidth();
        gc.fillText(resultMessage, (canvas.getWidth() - textWidth) / 2, 200);

        String scoreText = "Score: " + finalScore;
        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.NORMAL, 30));
        tempText.setText(scoreText);
        tempText.setFont(javafx.scene.text.Font.font("Arial", 30));
        textWidth = tempText.getLayoutBounds().getWidth();
        gc.fillText(scoreText, (canvas.getWidth() - textWidth) / 2, 280);

        for (GameButton button : buttons.values()) {
            button.renderButton();
        }
    }

    @Override
    protected void bindInputs() {
        buttons.get("home").setCallback(button -> {
            System.out.println("Returning to Main Menu...");
            sceneManager.setScene("menu", primaryStage);
        });
        buttons.get("replay").setCallback(button -> {
            System.out.println("Replaying Game...");
            sceneManager.setScene("play", primaryStage);
        });
        keyboard.setOnMouseClick(e -> {
            for (GameButton button : buttons.values()) {
                if (keyboard.isOverRegion(button.getX(), button.getY(), button.getWidth(), button.getHeight())) {
                    button.triggerCallback();
                }
            }
        });
        keyboard.setOnMouseMove(e -> {
            boolean hovered = false;
            for (GameButton button : buttons.values()) {
                if (keyboard.isOverRegion(button.getX(), button.getY(), button.getWidth(), button.getHeight())) {
                    hovered = true;
                    break;
                }
            }
            scene.setCursor(hovered ? javafx.scene.Cursor.HAND : javafx.scene.Cursor.DEFAULT);
        });
    }
    @Override
    public void onEnter() {
        scene.setCursor(javafx.scene.Cursor.DEFAULT);
    }
}
