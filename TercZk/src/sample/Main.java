package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class Main extends Application {

    private ObservableList<Circle> circles = FXCollections.observableArrayList();

    PieChart.Data outerBoundsData = new PieChart.Data("Mimo", 0);
    PieChart.Data outerCircleData = new PieChart.Data("Vnejsi kruh", 0);
    PieChart.Data innerCircleData = new PieChart.Data("Vnitrni kruh", 0);
    private Label numOfCirclesLabel;

    private Pane pane;
    private PieChart pieChart;

    private Circle outerCircle;
    private Circle innerCircle;


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setOnCloseRequest(windowEvent -> {
            onCloseEvent(windowEvent);
        });
        BorderPane borderPane = new BorderPane();


        HBox workingAreaHBox = new HBox();
        workingAreaHBox.setAlignment(Pos.CENTER);

        outerCircle = new Circle();
        outerCircle.setFill(Color.TRANSPARENT);
        outerCircle.setStroke(Color.RED);
        innerCircle = new Circle();
        innerCircle.setFill(Color.TRANSPARENT);
        innerCircle.setStroke(Color.RED);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMaxWidth(Double.MAX_VALUE);

        pane = new Pane();
        pane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
        pane.widthProperty().addListener(change ->{
            moveBigCircles();
            clearSmallCircles();

        });
        pane.heightProperty().addListener(change ->{
            moveBigCircles();
            clearSmallCircles();

        });

        pane.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY){
                Circle circle = new Circle(event.getX(), event.getY(), 5);
                circle.setFill(Color.LIGHTBLUE);
                circle.setStroke(Color.BLACK);
                circles.add(circle);
                pane.getChildren().add(circle);
                numOfCirclesLabel.setText("Pocet kruhu - " + circles.size());

                if (circle.getCenterX() < innerCircle.getCenterX() + innerCircle.getRadius() && circle.getCenterY() < innerCircle.getCenterY() + innerCircle.getRadius()){
                    innerCircleData.setPieValue(innerCircleData.getPieValue() + 1);
                }
                else if (circle.getCenterX() < outerCircle.getCenterX() + outerCircle.getRadius() && circle.getCenterY() < outerCircle.getCenterY() + outerCircle.getRadius()){
                    outerCircleData.setPieValue(outerCircleData.getPieValue() + 1);
                }
                else{
                    outerBoundsData.setPieValue(outerBoundsData.getPieValue() + 1);
                }
            }
            else if (event.getButton() == MouseButton.SECONDARY){
                clearClosectCircle(event.getX(), event.getY());
            }

        });

        pane.getChildren().addAll(outerCircle, innerCircle);

        anchorPane.getChildren().add(pane);
        AnchorPane.setRightAnchor(pane, 0d);
        AnchorPane.setLeftAnchor(pane, 0d);
        AnchorPane.setTopAnchor(pane, 0d);
        AnchorPane.setBottomAnchor(pane, 0d);

        ObservableList<PieChart.Data> dataObservableList = FXCollections.observableArrayList(outerBoundsData, outerCircleData, innerCircleData);

        pieChart = new PieChart();
        pieChart.setTitle("Rozlozeni kruhu");
        pieChart.setLegendSide(Side.TOP);
        pieChart.setTitleSide(Side.TOP);
        pieChart.setData(dataObservableList);
        pieChart.setAnimated(true);
        pieChart.setMinWidth(400);
        pieChart.setPrefWidth(400);
        pieChart.setMaxWidth(400);

        HBox.setHgrow(anchorPane, Priority.SOMETIMES);
        workingAreaHBox.getChildren().addAll(anchorPane, pieChart);

        borderPane.setCenter(workingAreaHBox);

        HBox buttonsHBox = new HBox(10);
        buttonsHBox.setPadding(new Insets(10));
        buttonsHBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        buttonsHBox.setAlignment(Pos.CENTER);

        Button endButton =  new Button("Konec");
        endButton.setOnAction( actionEvent -> {
            onCloseEvent(new WindowEvent(primaryStage,  WindowEvent.WINDOW_CLOSE_REQUEST));
        });
        Button deleteCirclesButton = new Button("Vymaz kruhy");
        deleteCirclesButton.setOnAction(actionEvent -> {
            clearSmallCircles();
        });
        numOfCirclesLabel = new Label("Pocet kruhu - 0");

        buttonsHBox.getChildren().addAll(endButton, deleteCirclesButton, numOfCirclesLabel);

        borderPane.setBottom(buttonsHBox);

        primaryStage.setTitle("TercZk");
        primaryStage.setScene(new Scene(borderPane, 800, 600));
        primaryStage.show();


    }

    private void clearClosectCircle(double x, double y) {
        double closestDistance = Double.MAX_VALUE;
        Circle closestCircle = null;

        for (Circle circle : circles){
            double distance = Math.sqrt(
                    ((circle.getCenterX() - x) * (circle.getCenterX() - x)) +
                            ((circle.getCenterY() - y) * (circle.getCenterY() - y)));

            if (distance < closestDistance){
                closestDistance = distance;
                closestCircle = circle;
            }
        }

        if (closestCircle.getCenterX() < innerCircle.getCenterX() + innerCircle.getRadius() && closestCircle.getCenterY() < innerCircle.getCenterY() + innerCircle.getRadius()){
            innerCircleData.setPieValue(innerCircleData.getPieValue()  - 1);
        }
        else if (closestCircle.getCenterX() < outerCircle.getCenterX() + outerCircle.getRadius() && closestCircle.getCenterY() < outerCircle.getCenterY() + outerCircle.getRadius()){
            outerCircleData.setPieValue(outerCircleData.getPieValue() - 1);
        }
        else{
            outerBoundsData.setPieValue(outerBoundsData.getPieValue() - 1);
        }

        if (closestCircle != null){
            pane.getChildren().remove(closestCircle);
            circles.remove(closestCircle);
        }

        numOfCirclesLabel.setText("Pocet kruhu - " + circles.size());

    }

    private void clearSmallCircles() {
        for (Circle circle : circles){
            pane.getChildren().remove(circle);
        }

        outerBoundsData.setPieValue(0);
        outerCircleData.setPieValue(0);
        innerCircleData.setPieValue(0);

        circles.clear();
        numOfCirclesLabel.setText("Pocet kruhu - 0");

    }

    private void moveBigCircles() {
        outerCircle.setCenterX(pane.getWidth() / 2);
        outerCircle.setCenterY(pane.getHeight() / 2);
        double radius = Math.min(pane.getWidth(), pane.getHeight()) / 2;

        outerCircle.setRadius(radius);

        innerCircle.setCenterX(pane.getWidth() / 2);
        innerCircle.setCenterY(pane.getHeight() / 2);
        innerCircle.setRadius(radius / 2);
    }


    private void onCloseEvent(WindowEvent windowEvent) {
        Alert endAlert = new Alert(Alert.AlertType.CONFIRMATION);
        endAlert.setTitle("Konec");
        endAlert.setContentText("Opravdu chcete aplikaci ukončit?");
        Optional<ButtonType> result = endAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            Platform.exit();
        }
        else {
            windowEvent.consume();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
