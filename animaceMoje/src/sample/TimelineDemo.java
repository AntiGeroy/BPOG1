package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimelineDemo extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();

        Rectangle rectangle = new Rectangle(20, 20, 60, 60);
        rectangle.setEffect(new Lighting());
        rectangle.setFill(Color.CADETBLUE);

        Timeline timeline = new Timeline();
        timeline.setCycleCount(2);
        timeline.setAutoReverse(true);

        KeyValue keyValue = new KeyValue(rectangle.translateXProperty(), 200);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(2000), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        root.getChildren().add(rectangle);

        Scene scene = new Scene(root, 350, 250);

        stage.setTitle("Timeline");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
