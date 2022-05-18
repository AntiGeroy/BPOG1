package sample;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AnchorPaneDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();

        TextArea textArea = new TextArea();
        Button button1 = new Button("Open");
        button1.setPrefWidth(75);
        Button button2 = new Button("Close");
        button2.setPrefWidth(75);

        AnchorPane.setTopAnchor(textArea, 10d);
        AnchorPane.setLeftAnchor(textArea, 10d);
        AnchorPane.setBottomAnchor(textArea, 10d);
        AnchorPane.setRightAnchor(textArea, 100d);

        AnchorPane.setTopAnchor(button1, 10d);
        AnchorPane.setRightAnchor(button1, 10d);

        AnchorPane.setRightAnchor(button2, 10d);
        AnchorPane.setBottomAnchor(button2, 10d);

        anchorPane.getChildren().addAll(textArea, button1, button2);


        stage.setScene(new Scene(anchorPane, 450, 250, Color.KHAKI));
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
