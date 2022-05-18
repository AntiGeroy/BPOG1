package sample;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FlowPaneDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FlowPane flowPane = new FlowPane();

        Button button1 = new Button("One");
        Button button2 = new Button("Two");
        Button button3 = new Button("Three");
        Button button4 = new Button("Four");


        flowPane.getChildren().addAll(button1, button2, button3, button4);
        flowPane.setOrientation(Orientation.VERTICAL);
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(flowPane, 300, 275));
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
