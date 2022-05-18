package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;

public class Main extends Application {

    private LineChart<Number, Number> histogram;
    private ImageView imageView;
    private Stage stage;

    private VBox imageViewVBox;
    private VBox histogramVBox;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        imageView = new ImageView();

        NumberAxis xAxis = new NumberAxis(0, 255, 10);
        NumberAxis yAxis = new NumberAxis();

        histogram = new LineChart<>(xAxis, yAxis);
        histogram.visibleProperty().bind(imageView.imageProperty().isNotNull());
        histogram.setCreateSymbols(false);
        histogram.setLegendVisible(false);

        BorderPane root = new BorderPane();

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem openFileMenuItem = new MenuItem("Open ...");
        openFileMenuItem.setOnAction(actionEvent -> {
            loadImage();
        });
        fileMenu.getItems().add(openFileMenuItem);
        menuBar.getMenus().add(fileMenu);

        root.setTop(menuBar);

        SplitPane splitPane = new SplitPane();

        imageViewVBox = new VBox();

        Label noImageLabel = new Label("No image loaded");
        imageViewVBox.setAlignment(Pos.CENTER);
        noImageLabel.setFont(new Font(28));
        noImageLabel.visibleProperty().bind(imageView.imageProperty().isNull());

        imageViewVBox.getChildren().addAll(noImageLabel);
        splitPane.getItems().add(imageViewVBox);

        histogramVBox = new VBox();
        histogramVBox.setAlignment(Pos.CENTER);

        Label noImageLabelGraph = new Label("Load image to display histogram");
        noImageLabelGraph.setFont(new Font(18));
        noImageLabelGraph.visibleProperty().bind(imageView.imageProperty().isNull());

        histogramVBox.getChildren().addAll(noImageLabelGraph);
        splitPane.getItems().add(histogramVBox);

        imageView.fitWidthProperty().bind(imageViewVBox.widthProperty().divide(100).multiply(80));
        imageView.fitHeightProperty().bind(imageViewVBox.heightProperty().divide(100).multiply(80));

        root.setCenter(splitPane);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());
        stage.setTitle("Histogram");
        stage.setScene(scene);
        stage.show();

    }

    private void loadImage(){
        histogram.getData().clear();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG image", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG image", "*.png"),
                new FileChooser.ExtensionFilter("BMP image", "*.bmp")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null){
            try {
                imageViewVBox.getChildren().clear();
                imageViewVBox.getChildren().add(imageView);
                histogramVBox.getChildren().clear();
                histogramVBox.getChildren().add(histogram);
                imageView.setImage(new Image(new FileInputStream(selectedFile)));
                updateHistogram();
            } catch (Exception e){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error loading");
                e.printStackTrace();
                errorAlert.setContentText("Error occurred while reading file " + selectedFile.getName());
                errorAlert.showAndWait();
            }

        }
    }

    private void updateHistogram(){
        Image image = imageView.getImage();
        PixelReader reader = image.getPixelReader();
        int imageWidth = (int) image.getWidth();
        int imageHeight = (int) image.getHeight();

        double[] redColorValues = new double[256];
        double[] greenColorValues = new double[256];
        double[] blueColorValues = new double[256];

        for (int y = 0; y < imageHeight; y++){
            for (int x = 0; x < imageWidth; x++){
                Color pixelColor = reader.getColor(x, y);
                //System.out.println("RED " + ((int)(pixelColor.getRed() * 256)));
                //System.out.println("GREEN " + ((int)(pixelColor.getRed() * 256)));
                //System.out.println("BLUE " + ((int)(pixelColor.getRed() * 256)));
                redColorValues[(int)(pixelColor.getRed() * 255)]++;
                greenColorValues[(int)(pixelColor.getGreen() * 255)]++;
                blueColorValues[(int)(pixelColor.getBlue() * 255)]++;
            }
        }

        XYChart.Series<Number, Number> redSeries = new XYChart.Series<>();

        redSeries.setName("Red");

        XYChart.Series<Number, Number> greenSeries = new XYChart.Series<>();
        greenSeries.setName("Green");


        XYChart.Series<Number, Number> blueSeries = new XYChart.Series<>();

        blueSeries.setName("Blue");

        for (int i = 0; i < 256; i++){
            redSeries.getData().add(new XYChart.Data<>(i, redColorValues[i]));
            greenSeries.getData().add(new XYChart.Data<>(i, greenColorValues[i]));
            blueSeries.getData().add(new XYChart.Data<>(i, blueColorValues[i]));
        }

        histogram.getData().addAll(redSeries, greenSeries, blueSeries);

    }


    public static void main(String[] args) {
        Application.launch();
    }
}
