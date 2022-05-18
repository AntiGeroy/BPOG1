package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SequentialTimelineDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();

        Circle circle = new Circle(50, 100, 10);
        circle.setFill(Color.CADETBLUE);

        KeyValue keyValue1 = new KeyValue(circle.scaleXProperty(),4);
        KeyValue keyValue2 = new KeyValue(circle.scaleYProperty(),4);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(3000), keyValue1, keyValue2);

        Timeline scale = new Timeline();
        scale.getKeyFrames().add(keyFrame);

        KeyValue keyValue3 = new KeyValue(circle.centerXProperty(), 250);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(5000), keyValue3);

        Timeline move = new Timeline();
        move.getKeyFrames().add(keyFrame2);

        KeyValue keyValue4 = new KeyValue(circle.scaleXProperty(), 1);
        KeyValue keyValue5 = new KeyValue(circle.scaleYProperty(), 1);
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(3000), keyValue4, keyValue5);

        Timeline scale2 = new Timeline();
        scale2.getKeyFrames().addAll(keyFrame3);

        SequentialTransition sequentialTransition = new SequentialTransition(scale, move, scale2);
        sequentialTransition.play();

        root.getChildren().add(circle);
        Scene scene = new Scene(root, 300, 250);

        stage.setTitle("Sequential Timeline animation");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
