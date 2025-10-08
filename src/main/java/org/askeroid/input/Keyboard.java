package org.askeroid.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.Cursor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Keyboard {

    private final Map<KeyCode, Boolean> keyStates = new HashMap<>();

    private Consumer<KeyCode> onKeyPress;
    private Consumer<KeyCode> onKeyRelease;
    private Consumer<MouseEvent> onMousePress;
    private Consumer<MouseEvent> onMouseRelease;
    private Consumer<MouseEvent> onMouseClick;
    private Consumer<MouseEvent> onMouseMove;

    private double mouseX, mouseY;
    private boolean mousePressed = false;

    public Keyboard(Scene scene) {
        // --- Keyboard ---
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            boolean wasPressed = keyStates.getOrDefault(key, false);
            keyStates.put(key, true);
            if (!wasPressed && onKeyPress != null) onKeyPress.accept(key);
        });

        scene.setOnKeyReleased(event -> {
            KeyCode key = event.getCode();
            keyStates.put(key, false);
            if (onKeyRelease != null) onKeyRelease.accept(key);
        });

        // --- Mouse Press/Release ---
        scene.setOnMousePressed(event -> {
            mousePressed = true;
            mouseX = event.getX();
            mouseY = event.getY();
            if (onMousePress != null) onMousePress.accept(event);
        });

        scene.setOnMouseReleased(event -> {
            mousePressed = false;
            mouseX = event.getX();
            mouseY = event.getY();
            if (onMouseRelease != null) onMouseRelease.accept(event);
        });

        // --- Mouse Click (press + release on same target) ---
        scene.setOnMouseClicked(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
            if (onMouseClick != null) onMouseClick.accept(event);
        });

        // --- Mouse Move / Hover ---
        scene.setOnMouseMoved(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
            if (onMouseMove != null) onMouseMove.accept(event);
        });

        scene.setOnMouseDragged(event -> {
            mouseX = event.getX();
            mouseY = event.getY();
        });

    }

    // --- Key State ---
    public boolean isKeyPressed(KeyCode key) {
        return keyStates.getOrDefault(key, false);
    }

    // --- Mouse State ---
    public boolean isMousePressed() {
        return mousePressed;
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    // --- Hover helper (custom area check) ---
    public boolean isOverRegion(double x, double y, double width, double height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    // --- Handlers ---
    public void setOnKeyPress(Consumer<KeyCode> handler) { this.onKeyPress = handler; }
    public void setOnKeyRelease(Consumer<KeyCode> handler) { this.onKeyRelease = handler; }
    public void setOnMousePress(Consumer<MouseEvent> handler) { this.onMousePress = handler; }
    public void setOnMouseRelease(Consumer<MouseEvent> handler) { this.onMouseRelease = handler; }
    public void setOnMouseClick(Consumer<MouseEvent> handler) { this.onMouseClick = handler; }
    public void setOnMouseMove(Consumer<MouseEvent> handler) { this.onMouseMove = handler; }

    public void clear() { keyStates.clear(); }
}
