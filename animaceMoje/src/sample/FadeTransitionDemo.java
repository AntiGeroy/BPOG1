package sample;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class FadeTransitionDemo extends Application {

    private FadeTransition fadeTransition;
    private Rectangle rectangle;

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();

        rectangle = new Rectangle(20, 20, 150, 150);
        fadeTransition = new FadeTransition(Duration.millis(5000), rectangle);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        rectangle.setOnMouseClicked(event -> {
            double opacity = rectangle.getOpacity();
            if ((int)opacity == 0){
                return;
            }
            Animation.Status status = fadeTransition.getStatus();
            if (status == Animation.Status.RUNNING){
                return;
            }
            if (status == Animation.Status.STOPPED){
                fadeTransition.play();
            }
        });

        root.getChildren().add(rectangle);

        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
