package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;

import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class AnimationTimer extends Application {

    private double opacity = 1;
    private Circle circle;

    private Direction direction;

    private Pane root;

    enum Direction {UP, DOWN};

    @Override
    public void start(Stage primaryStage) throws Exception{
        initUI(primaryStage);
    }

    private void initUI(Stage stage){
        root = new Pane();

        circle = new Circle(30);
        circle.setFill(Color.RED);
        root.getChildren().add(circle);
        direction = Direction.UP;

        javafx.animation.AnimationTimer timer = new MyTimer();
        timer.start();

        Scene scene = new Scene(root, 300, 250);
        root.widthProperty().addListener(observable -> {
            circle.setTranslateX(root.getWidth() / 2);
        });
        circle.setTranslateX(root.getWidth() / 2);
        circle.setTranslateY(root.getHeight() / 2);

        stage.setTitle("AnimationTimer");
        stage.setScene(scene);
        stage.show();
    }

    private class MyTimer extends javafx.animation.AnimationTimer {
        @Override
        public void handle(long l) {

            if (direction == Direction.UP){
                double y = circle.getTranslateY();
                y -= 1;
                if (y - circle.getRadius() <= 0){
                    direction = Direction.DOWN;
                }
                circle.setTranslateY(y);

            }
            else {
                double y = circle.getTranslateY();
                y += 1;
                if (y + circle.getRadius() >= root.getHeight()){
                    direction = Direction.UP;
                }
                circle.setTranslateY(y);
            }


        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }


}
