package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Transformation extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Button button = new Button("Hello World!");
        BorderPane myPane = new BorderPane();
        myPane.setCenter(button);
        VBox menu = new VBox();

        Slider zoomSlider = new Slider(0.2, 4, 1);
        zoomSlider.valueProperty().addListener(e -> {
            button.setScaleX(zoomSlider.getValue());
        });

        Slider rotateSlider = new Slider(-180, 180, 0);
        rotateSlider.valueProperty().addListener(e -> {
            button.setRotate(rotateSlider.getValue());
        });

        menu.getChildren().addAll(zoomSlider, rotateSlider);
        myPane.setBottom(menu);
        myPane.setBackground(null);
        Scene myScene = new Scene(myPane);
        myScene.setFill(Color.DARKORANGE);

        primaryStage.setScene(myScene);
        primaryStage.setTitle("App");
        primaryStage.setWidth(300);
        primaryStage.setHeight(200);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
