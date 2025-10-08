package org.askeroid.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.Balls;
import org.askeroid.objects.gameObjects.Paddle;

public class Renderer {
    private Stage primaryStage;

    public Renderer(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void createWindow() {
        primaryStage.setResizable(false);
        primaryStage.setHeight(800);
        primaryStage.setWidth(600);
        primaryStage.setTitle("Askeroid Game");
    }

    public void drawImage(GraphicsContext gc, String imagePath, double x, double y, double width, double height) {
        Image image = new Image(imagePath, width, height, false, false);
        gc.drawImage(image, x, y);
    }

    public void drawBackground(GraphicsContext gc, String imagePath) {
        Image image = new Image(imagePath, gc.getCanvas().getWidth(), gc.getCanvas().getHeight(), false, false);
        gc.drawImage(image, 0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public void drawPaddle(GraphicsContext gc, Paddle paddle) {
        gc.setFill(Color.DODGERBLUE);
        gc.fillRoundRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), 10, 10);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRoundRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), 10, 10);
    }

    public void drawBall(GraphicsContext gc, Balls ball) {
        gc.setFill(Color.WHITE);
        gc.fillOval(ball.getX(), ball.getY(), ball.getRadius() * 2, ball.getRadius() * 2);
    }
}
