package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class HBoxDemo  extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        HBox hBox = new HBox(10);
        Button button1 = new Button("One");
        button1.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(button1, Priority.SOMETIMES);

        Button button2 = new Button("Two");
        button2.setMaxWidth(300);
        HBox.setHgrow(button2, Priority.SOMETIMES);

        Button button3 = new Button("Three");
        button3.setMaxWidth(400);
        HBox.setHgrow(button3, Priority.SOMETIMES);

        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        hBox.getChildren().addAll(button1, button2, region, button3);

        Scene scene = new Scene(hBox, 400, 200);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
