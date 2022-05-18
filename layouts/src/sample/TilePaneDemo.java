package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class TilePaneDemo extends Application {

    private Button[] buttons;

    @Override
    public void start(Stage stage) throws Exception {

        TilePane tilePane = new TilePane();

        buttons = new Button[50];

        for (int i = 0; i < 50; i++){
            Button button = new Button(Integer.toString(i + 1));
            button.setPrefWidth(50);
            button.setPrefHeight(50);
            buttons[i] = button;
            tilePane.getChildren().add(button);
        }

        tilePane.setOrientation(Orientation.HORIZONTAL);
        tilePane.setHgap(5);
        tilePane.setVgap(5);
        tilePane.setPadding(new Insets(10));
        tilePane.setPrefColumns(10);
        tilePane.setPrefRows(10);


        buttons[24].setPrefHeight(75);
        buttons[24].setPrefWidth(75);
        tilePane.setTileAlignment(Pos.TOP_LEFT);

        TilePane.setAlignment(buttons[0], Pos.BOTTOM_RIGHT);
        TilePane.setAlignment(buttons[49], Pos.BOTTOM_RIGHT);
        Scene scene = new Scene(tilePane);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
