package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PaneDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();

        Label label = new Label("Label");
        label.relocate(30, 30);

        TextField textField = new TextField();
        textField.relocate(100, 30);

        Button button = new Button("Button");
        button.relocate(275, 30);

        pane.getChildren().addAll(label, textField, button);

        Scene scene = new Scene(pane, 800, 500);
        scene.getStylesheets().add("sample/application.css");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch();
    }
}
