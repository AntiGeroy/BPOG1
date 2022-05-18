package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;


public class Main extends Application {

    private Pane drawingPane;
    private Rectangle rectangle;

    private ColorPicker colorPicker;

    private ObservableList<Circle> circles = FXCollections.observableArrayList();
    private Circle currentCircle = null;
    private Point2D startPoint = null;
    private Point2D endPoint = null;

    @Override
    public void start(Stage stage) throws Exception {

        stage.setOnCloseRequest(windowEvent -> {
            onCloseRequest(windowEvent);
        });

        BorderPane borderPane = new BorderPane();

        drawingPane = new Pane();
        drawingPane.setBackground(new Background(new BackgroundFill(new LinearGradient(
                0.5, 0, 0.5, 0.5, true, CycleMethod.REFLECT, new Stop(0, Color.LIGHTBLUE
        ), new Stop(1, Color.YELLOW)), null, null)));


        rectangle = new Rectangle();
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.RED);
        rectangle.setStrokeWidth(1);


        drawingPane.widthProperty().addListener((InvalidationListener) event -> {
            resizeRectangle();
            deleteUnseen();
            updateCirclesColor();
        });
        drawingPane.heightProperty().addListener((InvalidationListener) event -> {
            resizeRectangle();
            deleteUnseen();
            updateCirclesColor();
        });

        drawingPane.getChildren().add(rectangle);

        borderPane.setCenter(drawingPane);

        HBox buttonsHBox = new HBox(10);
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        buttonsHBox.setPadding(new Insets(10));

        Button endButton = new Button("Konec");
        endButton.setOnAction(actionEvent -> {
            onCloseRequest(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        });

        Button deleteAllButton = new Button("Vymaz vsechny kruhy");
        deleteAllButton.setOnAction(actionEvent -> {
            deleteAll();
        });

        Label colorPickerLabel = new Label("Barva kruznice");
        colorPicker = new ColorPicker();
        colorPicker.valueProperty().addListener(change -> {
            updateCirclesStroke();
        });
        colorPicker.setValue(Color.BLACK);

        buttonsHBox.getChildren().addAll(endButton, deleteAllButton, colorPickerLabel, colorPicker);

        borderPane.setBottom(buttonsHBox);

        drawingPane.setOnMousePressed(event -> {
            onMouseClicked(event.getX(), event.getY());
        });

        drawingPane.setOnMouseDragged(event -> {
            onMouseDragged(event.getX(), event.getY());
        });

        drawingPane.setOnMouseReleased(event -> {
            onMouseReleased();
        });


        stage.setTitle("Kruhy v pravouhelniku");
        Scene scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);
        stage.show();

    }

    private void updateCirclesColor() {
        for (Circle circle : circles){
            circle.setFill(getColor(circle));
        }
    }

    private void deleteUnseen() {
        ObservableList<Circle> newCircles = FXCollections.observableArrayList();
        for (Circle circle : circles){
            drawingPane.getChildren().remove(circle);
            if (circle.getCenterX() - circle.getRadius() < 0 ||
                    circle.getCenterX() + circle.getRadius() > drawingPane.getWidth() ||
                    circle.getCenterY() - circle.getRadius() < 0 ||
                    circle.getCenterY() + circle.getRadius() > drawingPane.getHeight()){
                continue;
            }
            newCircles.add(circle);
        }
        circles = newCircles;
        for (Circle circle : circles){
            drawingPane.getChildren().add(circle);
        }

    }

    private void updateCirclesStroke() {

        for (Circle circle : circles){
            circle.setStroke(colorPicker.getValue());
        }
    }

    private void onMouseDragged(double x, double y){
        if (startPoint == null){
            return;
        }
        if (currentCircle != null){
            drawingPane.getChildren().remove(currentCircle);
        }
        endPoint = new Point2D(x, y);
        double radius = calculateRadius();
        currentCircle = new Circle(startPoint.getX(), startPoint.getY(), radius);
        Color strokeColor = colorPicker.getValue();
        currentCircle.setFill(Color.TRANSPARENT);
        currentCircle.setStroke(strokeColor);
        currentCircle.setStrokeWidth(3);
        drawingPane.getChildren().add(currentCircle);

    }

    private double calculateRadius() {
        return Math.sqrt((endPoint.getX() - startPoint.getX()) * (endPoint.getX() - startPoint.getX()) +
                ((endPoint.getY() - startPoint.getY()) * (endPoint.getY() - startPoint.getY())));
    }

    private void onMouseReleased() {
        if (currentCircle == null){
            return;
        }

        drawingPane.getChildren().remove(currentCircle);

        Circle finalCircle = new Circle(currentCircle.getCenterX(), currentCircle.getCenterY(), currentCircle.getRadius());
        finalCircle.setStroke(currentCircle.getStroke());
        finalCircle.setStrokeWidth(currentCircle.getStrokeWidth());
        finalCircle.setFill(getColor(finalCircle));
        circles.add(finalCircle);
        drawingPane.getChildren().add(finalCircle);
    }

    private Color getColor(Circle currentCircle) {

        double rectangleWidth = currentCircle.getRadius() * 2;
        double rectangleHeight = currentCircle.getRadius() * 2;

        Color notTransparent = null;

        Rectangle circleRectangle = new Rectangle(currentCircle.getCenterX() - (rectangleWidth / 2), currentCircle.getCenterY() - (rectangleWidth / 2), rectangleWidth, rectangleHeight);


        Point2D l2 = new Point2D(rectangle.getX(), rectangle.getY());
        Point2D r2 = new Point2D(rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight());

        Rectangle centralRectangle = new Rectangle(l2.getX(), l2.getY(), r2.getX() - l2.getX(), r2.getY() - l2.getY());



        if (circleRectangle.getX() > centralRectangle.getX() &&
                (circleRectangle.getX() + circleRectangle.getWidth()) < (centralRectangle.getX() + centralRectangle.getWidth()) &&
                circleRectangle.getY() > centralRectangle.getY() &&
                circleRectangle.getY() + circleRectangle.getHeight() < (centralRectangle.getY() + centralRectangle.getHeight())){
            notTransparent = Color.BLUE;
        }

        else if (isOverlapping(circleRectangle, centralRectangle)){
            notTransparent = Color.WHITE;
        }
        else {
            notTransparent = Color.GREEN;
        }


        return new Color(notTransparent.getRed(), notTransparent.getGreen(), notTransparent.getBlue(), 0.5);

    }

    private void onMouseClicked(double x, double y) {
        this.startPoint = new Point2D(x, y);
    }

    public boolean isOverlapping(Rectangle rectangle1, Rectangle rectangle2) {
        if (rectangle1.intersects(rectangle2.getBoundsInParent())){
            return true;
        }

        return false;
    }

    private void deleteAll() {
        for (Circle circle : circles){
            drawingPane.getChildren().remove(circle);
        }
    }

    private void onCloseRequest(WindowEvent windowEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konec");
        alert.setContentText("Opravdu chcete aplikaci ukoncit");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            Platform.exit();
        }
        else{
            windowEvent.consume();
        }
    }

    private void resizeRectangle() {
        rectangle.setWidth(drawingPane.getWidth() / 2);
        rectangle.setHeight(drawingPane.getHeight() / 2);
        rectangle.setX(drawingPane.getWidth() / 2 - rectangle.getWidth() / 2);
        rectangle.setY(drawingPane.getHeight() / 2 - rectangle.getHeight() / 2);
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
