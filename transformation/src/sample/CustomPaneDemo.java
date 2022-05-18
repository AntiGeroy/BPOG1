package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CustomPaneDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SortedPane myPane = new SortedPane();
        myPane.getChildren().addAll(
                new Button("Hello World"),
                new Button("Long Text..............."),
                new Button("short")
        );

        myPane.setPadding(new Insets(10));
        myPane.setSpacing(12d);

        Scene myScene = new Scene(myPane);
        stage.setScene(myScene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
