package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StackPaneDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        StackPane stackPane = new StackPane();
        ImageView imageView = new ImageView("sample/yoda.jpg");

        stackPane.getChildren().add(imageView);

        Label label = new Label("Do, or do not! There is no try!");
        label.setFont(new Font(32));
        label.setBackground(new Background(new BackgroundFill(Color.SALMON, null, null)));
        StackPane.setAlignment(label, Pos.BOTTOM_CENTER);
        stackPane.getChildren().add(label);

        Button button = new Button("Click");
        button.setOnAction(actionEvent -> {
            label.setText("Always pass on what you have learned.");
        });
        StackPane.setAlignment(button, Pos.TOP_RIGHT);
        stackPane.getChildren().add(button);

        Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
