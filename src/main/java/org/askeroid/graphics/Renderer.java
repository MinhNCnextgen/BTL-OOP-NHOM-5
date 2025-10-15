package org.askeroid.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import org.askeroid.objects.gameObjects.Ball;
import org.askeroid.objects.gameObjects.Bricks.Brick;
import org.askeroid.objects.gameObjects.Bricks.ExplosionBrick;
import org.askeroid.objects.gameObjects.Paddle;

public class Renderer {
    private Stage primaryStage;

    public Renderer(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void createWindow() {
        primaryStage.setResizable(false);
        primaryStage.setTitle("Askeroid Game");
    }

    public void drawImage(GraphicsContext gc, String imagePath, double x, double y, double width, double height) {
        gc.setImageSmoothing(false);
        Image image = new Image(imagePath, width, height, false, false);
        gc.drawImage(image, x, y);
    }

    public void drawBackground(GraphicsContext gc, String path) {
        Image image = new Image(path, false);
        gc.setImageSmoothing(false);
          double cw = gc.getCanvas().getWidth();
        double ch = gc.getCanvas().getHeight();

        gc.clearRect(0, 0, cw, ch);
        gc.drawImage(image, 0, 0, cw, ch);
    }


    public void drawPaddle(GraphicsContext gc, Paddle paddle) {
        gc.setFill(Color.DODGERBLUE);
        gc.fillRoundRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), 10, 10);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.strokeRoundRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight(), 10, 10);
    }

    public void drawBall(GraphicsContext gc, Ball ball) {
        gc.setFill(Color.WHITE);
        gc.fillOval(ball.getX(), ball.getY(), ball.getRadius() * 2, ball.getRadius() * 2);
    }

    public void drawBrick(GraphicsContext gc, Brick brick) {
        if (!brick.isActive()) return;

        gc.setFill(brick.getColor());
        gc.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());

        gc.setLineWidth(2);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
    }

    public void drawText(GraphicsContext gc, String text, double x, double y, String font, double size, Color color) {
        gc.setFill(color);
        gc.setFont(javafx.scene.text.Font.font("Times New Roman", size));
        gc.fillText(text, x, y);
    }

    public void drawMask(GraphicsContext gc, ExplosionBrick.Mask mask) {
        gc.setFill(Color.color(1, 0, 0, 0.3)); // Semi-transparent red
        gc.fillOval(mask.getX(), mask.getY(), mask.getWidth(), mask.getHeight());
    }
}


