package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BorderPaneDemo extends Application {

    private BorderPane borderPane;

    @Override
    public void start(Stage stage) throws Exception {
        borderPane = new BorderPane();

        Button topButton = createButton("TOP");
        Button leftButton = createButton("LEFT");
        Button centerButton = createButton("CENTER");
        Button rightButton = createButton("RIGHT");
        Button bottomButton = createButton("BOTTOM");

        borderPane.setCenter(centerButton);
        borderPane.setTop(topButton);
        borderPane.setBottom(bottomButton);
        borderPane.setRight(rightButton);
        borderPane.setLeft(leftButton);

        stage.setScene(new Scene(borderPane, 400, 400));
        stage.show();
    }

    private Button createButton(String text){
        Button button = new Button(text);
        //button.setMaxWidth(Double.MAX_VALUE);
        //button.setMaxHeight(Double.MAX_VALUE);
        //button.setMinWidth(150);
        BorderPane.setMargin(button, new Insets(10));
        BorderPane.setAlignment(button, Pos.CENTER);
        return button;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
