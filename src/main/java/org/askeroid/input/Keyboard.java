package org.askeroid.input;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class Keyboard {
    private boolean goLeft = false;
    private boolean goRight = false;

    public Keyboard(Scene scene) {
        scene.setOnKeyPressed(event -> {
           if (event.getCode() == KeyCode.LEFT) {
               goLeft = true;
           } else if (event.getCode() == KeyCode.RIGHT) {
               goRight = true;
           }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                goLeft = false;
            } else if (event.getCode() == KeyCode.RIGHT) {
                goRight = false;
            }
        });
    }

}
