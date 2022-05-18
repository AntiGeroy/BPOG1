package sample;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PathTransitionDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();

        Path path = new Path();
        path.getElements().add(new MoveTo(20, 120));
        path.getElements().add(new CubicCurveTo(180, 60, 250, 340, 420, 240));

        Circle circle = new Circle(20, 120, 10);
        circle.setFill(Color.CADETBLUE);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(6));
        pathTransition.setDelay(Duration.seconds(2));
        pathTransition.setPath(path);
        pathTransition.setNode(circle);
        pathTransition.setCycleCount(2);
        pathTransition.setAutoReverse(true);

        pathTransition.play();

        root.getChildren().addAll(circle, path);
        Scene scene = new Scene(root, 450, 300);
        stage.setTitle("PathTransition");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
