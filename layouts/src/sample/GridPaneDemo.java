package sample;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GridPaneDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root = new BorderPane();

        GridPane gridPane = new GridPane();

        gridPane.setGridLinesVisible(true);

        Label firstNameLabel = new Label("First Name");
        Label lastNameLabel = new Label("Last Name");
        TextField firstNameTextField = new TextField();
        TextField lastNameTextField = new TextField();

        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(saveButton, cancelButton);

        gridPane.add(firstNameLabel, 0, 0, 1, 1);
        gridPane.add(firstNameTextField, 1, 0, 1, 1);
        gridPane.add(lastNameLabel, 0, 1, 1, 1);
        gridPane.add(lastNameTextField, 1, 1, 1, 1);
        gridPane.add(buttonBar, 0, 2, 2, 2);

        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10));

        ColumnConstraints column1 = new ColumnConstraints();
        ColumnConstraints column2 = new ColumnConstraints();

        gridPane.getColumnConstraints().addAll(column1, column2);

        //pixels
        column1.setPrefWidth(100);
        column2.setPrefWidth(200);

        //percentage
        column1.setPercentWidth(50);
        column2.setPercentWidth(200);

        GridPane.setHalignment(firstNameLabel, HPos.RIGHT);
        GridPane.setHalignment(lastNameLabel, HPos.RIGHT);
        GridPane.setHalignment(buttonBar, HPos.CENTER);

        GridPane.setHgrow(firstNameTextField, Priority.ALWAYS);
        GridPane.setHgrow(lastNameTextField, Priority.ALWAYS);

        gridPane.setPadding(new Insets(10));

        root.setCenter(gridPane);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
