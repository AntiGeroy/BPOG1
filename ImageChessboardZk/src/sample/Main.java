package sample;

import javafx.application.Application;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;


public class Main extends Application {

    private Stage stage;
    private StackPane stackPane;

    private ImageView imageView;
    private Pane drawingPane;
    private Spinner<Integer> numOfRowsSpinner;
    private Spinner<Integer> numOfColumnsSpinner;


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        imageView = new ImageView();
        imageView.setVisible(false);

        drawingPane = new Pane();
        drawingPane.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));

        BorderPane root = new BorderPane();

        stackPane = new StackPane();
        stackPane.getChildren().addAll(imageView, drawingPane);

        root.setCenter(stackPane);

        HBox buttonsHBox = new HBox(10);
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.setPadding(new Insets(10));


        Button loadImageButton = new Button("Load picture");
        loadImageButton.setOnAction(actionEvent -> {
            loadImage();
        });

        Label numOfRowsLabel = new Label("Number of rows");

        numOfRowsSpinner = new Spinner<>(1, 10, 4);

        numOfRowsSpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            drawChessboard();
        });

        Label numOfColumnsLabel = new Label("Number of columns");

        numOfColumnsSpinner = new Spinner<>(1, 10, 3);
        numOfColumnsSpinner.valueProperty().addListener((observableValue, integer, t1) -> {
            drawChessboard();
        });

        buttonsHBox.getChildren().addAll(loadImageButton, numOfRowsLabel, numOfRowsSpinner, numOfColumnsLabel, numOfColumnsSpinner);
        root.setBottom(buttonsHBox);

        imageView.setPreserveRatio(false);
        imageView.fitWidthProperty().bind(root.widthProperty());
        imageView.fitHeightProperty().bind(root.heightProperty().subtract(buttonsHBox.heightProperty()));


        drawChessboard();

        stage.setTitle("Image Chessboard");
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void drawChessboard() {
        int numOfRows = numOfRowsSpinner.getValue();
        int numOfColumns = numOfColumnsSpinner.getValue();

        drawingPane.getChildren().clear();

        for (int i = 0; i < numOfRows; i++){
            for (int j = 0; j < numOfColumns; j++){
                Rectangle rectangle = new Rectangle();

                rectangle.xProperty().bind(drawingPane.widthProperty().divide(numOfColumnsSpinner.getValue()).multiply(j));
                rectangle.yProperty().bind(drawingPane.heightProperty().divide(numOfRowsSpinner.getValue()).multiply(i));
                rectangle.widthProperty().bind(drawingPane.widthProperty().divide(numOfColumnsSpinner.getValue()));
                rectangle.heightProperty().bind(drawingPane.heightProperty().divide(numOfRowsSpinner.getValue()));

                rectangle.setFill(Color.BLACK);
                rectangle.setStroke(Color.RED);
                rectangle.setStrokeWidth(1);

                rectangle.setOnMouseClicked(event -> {
                    Rectangle rect = (Rectangle)event.getSource();
                    if (rect.getFill().equals(Color.BLACK)){
                        rect.setFill(Color.TRANSPARENT);
                    }
                    else {
                        rect.setFill(Color.BLACK);
                    }
                });
                drawingPane.getChildren().add(rectangle);
            }
        }
    }

    private void loadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        File file = fileChooser.showOpenDialog(stage);
        if (file != null){
            try {
                imageView.setImage(new Image(new FileInputStream(file)));
                imageView.setVisible(true);

            } catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Image load error");
                alert.setContentText("Error occured while loading image");
                alert.showAndWait();
            }
        }
    }


    public static void main(String[] args) {
        Application.launch();
    }
}