package sample;

import com.sun.javafx.geom.Curve;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlaneAnimation extends Application {

    private Pane pane;

    private PathTransition pathTransition;
    private Path path;

    private ObservableList<Point2D> points = FXCollections.observableArrayList();
    private ObservableList<CubicCurve> curves = FXCollections.observableArrayList();
    private ObservableList<Circle> circles = FXCollections.observableArrayList();

    private ObservableList<PathElement> roadBack = FXCollections.observableArrayList();

    private ImageView imageView;

    private CheckBox animationRunCheckBox;

    private CheckBox returnBackCheckBox;

    private Label sliderText;

    private Slider animationSpeedSlider;

    @Override
    public void start(Stage stage) {

        File f = new File(PlaneAnimation.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println("PATH to class: " + f.getPath());

        imageView = new ImageView(new Image("sample/plane.png"));
        imageView.setVisible(false);
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        BorderPane root = new BorderPane();

        pathTransition = new PathTransition();
        path = new Path();
        pathTransition.setPath(path);
        pathTransition.setNode(imageView);
        pathTransition.setDuration(Duration.millis(5000));
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setOnFinished(actionEvent -> {
            animationRunCheckBox.setSelected(false);
            imageView.setVisible(false);
        });

        pane = new Pane();
        pane.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY){
                return;
            }
            double x = event.getX();
            double y = event.getY();
            Circle circlePoint = new Circle(x, y, 5, Color.TURQUOISE);
            circles.add(circlePoint);
            circlePoint.setStroke(Color.BLACK);
            circlePoint.setStrokeWidth(1);
            pane.getChildren().add(circlePoint);
            Point2D newPoint = new Point2D(x, y);
            points.add(newPoint);

            if (curves.size() == 0 && points.size() == 4){
                CubicCurve curve = new CubicCurve(
                        points.get(0).getX(),
                        points.get(0).getY(),
                        points.get(1).getX(),
                        points.get(1).getY(),
                        points.get(2).getX(),
                        points.get(2).getY(),
                        points.get(3).getX(),
                        points.get(3).getY()
                        );
                path.getElements().add(new MoveTo(points.get(0).getX(),points.get(0).getY()));
                path.getElements().add(new CubicCurveTo(
                        points.get(1).getX(),
                        points.get(1).getY(),
                        points.get(2).getX(),
                        points.get(2).getY(),
                        points.get(3).getX(),
                        points.get(3).getY()
                ));
                curve.setStroke(Color.RED);
                curve.setFill(Color.TRANSPARENT);
                curves.add(curve);
                pane.getChildren().add(curve);
            }else if (curves.size() > 0 && (points.size() - 4) % 3 == 0){
                CubicCurve curve = new CubicCurve(
                        points.get(points.size() - 1 - 3).getX(),
                        points.get(points.size() - 1 - 3).getY(),
                        points.get(points.size() - 1 - 2).getX(),
                        points.get(points.size() - 1 - 2).getY(),
                        points.get(points.size() - 1 - 1).getX(),
                        points.get(points.size() - 1 - 1).getY(),
                        points.get(points.size() - 1).getX(),
                        points.get(points.size() - 1).getY()
                );
                path.getElements().add(new CubicCurveTo(
                        points.get(points.size() - 1 - 2).getX(),
                        points.get(points.size() - 1 - 2).getY(),
                        points.get(points.size() - 1 - 1).getX(),
                        points.get(points.size() - 1 - 1).getY(),
                        points.get(points.size() - 1).getX(),
                        points.get(points.size() - 1).getY()
                ));
                curve.setStroke(Color.RED);
                curve.setFill(Color.TRANSPARENT);
                curves.add(curve);
                pane.getChildren().add(curve);
            }
        });
        root.setCenter(pane);

        pane.getChildren().add(imageView);

        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(10);
        buttonsHBox.setPadding(new Insets(10));
        buttonsHBox.setAlignment(Pos.CENTER_LEFT);
        Button endButton = new Button("Konec");
        endButton.setOnAction(actionEvent -> {
            Alert exitDialog = new Alert(Alert.AlertType.CONFIRMATION);
            exitDialog.setTitle("Konec");
            exitDialog.setHeaderText("Ukonceni aplikace");
            exitDialog.setContentText("Opravdu chcete ukončit aplikaci?");
            Optional<ButtonType> result = exitDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Platform.exit();
            }
        });

        Button clearButton = new Button("Vymazat");
        clearButton.setOnAction( actionEvent -> {
            animationRunCheckBox.setSelected(false);
            imageView.setVisible(false);
            returnBackCheckBox.setSelected(false);
            pathTransition.stop();
            path.getElements().clear();

            for (Circle circle : circles){
                pane.getChildren().remove(circle);
            }
            for (CubicCurve curve : curves){
                pane.getChildren().remove(curve);
            }
            roadBack.clear();
            points.clear();
            curves.clear();
        });

        animationRunCheckBox = new CheckBox("Animace");
        animationRunCheckBox.setSelected(false);
        animationRunCheckBox.disableProperty().bind(Bindings.isEmpty(curves));
        animationRunCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                if (!imageView.isVisible()){
                    imageView.setVisible(true);
                }
                pathTransition.play();
            }
            else {
                pathTransition.pause();
            }
        });

        returnBackCheckBox = new CheckBox("Pohyb zpatky");
        returnBackCheckBox.setSelected(false);
        returnBackCheckBox.disableProperty().bind(Bindings.or(animationRunCheckBox.disableProperty(), Bindings.isEmpty(curves)));
        returnBackCheckBox.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (t1){
                ObservableList<PathElement> pathElements = path.getElements();
                for (int i = pathElements.size() - 1; i > 0; i--){
                    CubicCurveTo cubicCurveTo = (CubicCurveTo) pathElements.get(i);
                    double previousPointX = i > 1 ? ((CubicCurveTo) pathElements.get(i - 1)).getX() : ((MoveTo) pathElements.get(0)).getX();
                    double previousPointY = i > 1 ? ((CubicCurveTo) pathElements.get(i - 1)).getY() : ((MoveTo) pathElements.get(0)).getY();
                    CubicCurveTo cubicCurveBack = new CubicCurveTo(
                            cubicCurveTo.getControlX2(),
                            cubicCurveTo.getControlY2(),
                            cubicCurveTo.getControlX1(),
                            cubicCurveTo.getControlY1(),
                            previousPointX,
                            previousPointY
                    );

                    //MoveTo moveTo = new MoveTo(cubicCurveTo.getX(), cubicCurveTo.getY());
                    roadBack.add(cubicCurveBack);
                }
                for (PathElement curve : roadBack){
                    path.getElements().add(curve);
                }
            }
            else {
                for (PathElement curve : roadBack){
                    ObservableList<PathElement> newElements = FXCollections.observableArrayList(path.getElements().stream().filter(pathElement -> (pathElement != curve)).collect(Collectors.toList()));
                    path.getElements().clear();
                    path.getElements().addAll(newElements);
                    roadBack = FXCollections.observableArrayList();
                }
            }
        });

        sliderText = new Label("Rychlost: ");
        sliderText.disableProperty().bind(Bindings.or(animationRunCheckBox.disableProperty(), Bindings.isEmpty(curves)));

        animationSpeedSlider = new Slider(5, 30, 1);
        animationSpeedSlider.setShowTickMarks(true);
        animationSpeedSlider.setShowTickLabels(true);
        animationSpeedSlider.setMinorTickCount(0);
        animationSpeedSlider.setMajorTickUnit(1d);
        animationSpeedSlider.setValue(5);
        animationSpeedSlider.valueProperty().addListener((observableValue, number, t1) -> {
            pathTransition.setDuration(Duration.seconds(t1.doubleValue()));
        });
        animationSpeedSlider.disableProperty().bind(Bindings.or(animationRunCheckBox.disableProperty(), Bindings.isEmpty(curves)));;

        buttonsHBox.getChildren().addAll(endButton, clearButton, animationRunCheckBox, returnBackCheckBox, sliderText, animationSpeedSlider);
        root.setBottom(buttonsHBox);

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Plane animation");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
