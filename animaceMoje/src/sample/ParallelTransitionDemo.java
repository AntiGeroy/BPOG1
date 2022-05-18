package sample;

import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class ParallelTransitionDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();

        Rectangle rectangle = new Rectangle(50, 50, 30, 30);
        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        rectangle.setFill(Color.CADETBLUE);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(2000), rectangle);
        rotateTransition.setByAngle(180);
        rotateTransition.setCycleCount(2);
        rotateTransition.setAutoReverse(true);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(2000), rectangle);
        scaleTransition.setByX(2);
        scaleTransition.setByY(2);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);

        FillTransition fillTransition = new FillTransition(Duration.millis(2000),
                rectangle, Color.CADETBLUE, Color.STEELBLUE);
        fillTransition.setCycleCount(2);
        fillTransition.setAutoReverse(true);

        root.getChildren().add(rectangle);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(rotateTransition, scaleTransition, fillTransition);
        parallelTransition.play();

        Scene scene = new Scene(root, 300, 250);

        stage.setTitle("ParallelTransition");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
