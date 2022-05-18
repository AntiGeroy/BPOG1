package sample;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    private ObservableList<Node> nodes;
    private ObservableList<CubicCurve> curves;
    private ObservableList<Circle> circles;
    private ObservableList<Point2D> points;
    private ObservableList<PathElement> pathElements;

    private Path path;
    private PathTransition pathTransition;

    private final int R = 5;

    @FXML
    private Slider sliderTime;
    @FXML
    private CheckBox checkReverse;
    @FXML
    private CheckBox checkReturnFromEnd;
    @FXML
    private CheckBox checkDirection;
    @FXML
    private Pane pane;
    @FXML
    private Button buttonStart;
    @FXML
    private Button buttonStop;
    @FXML
    private ImageView imageView;

    private boolean isPlaying = false;
    private boolean isReversed = true;
    private boolean isDirected = true;
    private boolean isReturning = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nodes = pane.getChildren();
        circles = FXCollections.observableArrayList();
        points = FXCollections.observableArrayList();
        curves = FXCollections.observableArrayList();

        path = new Path();
        pathTransition = new PathTransition();
        pathElements = path.getElements();
        pathTransition.setNode(imageView);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setAutoReverse(isReversed);
        pathTransition.setCycleCount(Integer.MAX_VALUE);

        buttonStart.setDisable(true);
        buttonStop.setDisable(true);
        checkReverse.setSelected(true);
        checkDirection.setSelected(true);
        imageView.setImage(new Image("sample/lokomotiva.png"));
        imageView.setVisible(false);
    }

    @FXML
    private void autoReverseChanged(ActionEvent event) {
        stopAnimation(event);
        isReversed = !isReversed;
        pathTransition.setAutoReverse(isReversed);
        startAnimation(event);
    }

    @FXML
    private void returnPropertyChanged(ActionEvent event) {
        stopAnimation(event);
        isReturning = !isReturning;
        startAnimation(event);
    }

    @FXML
    private void directionChanged(ActionEvent event) {
        stopAnimation(event);
        isDirected = !isDirected;
        if (isDirected) {
            pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        } else {
            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        }
        startAnimation(event);
    }

    @FXML
    private void clearAll(ActionEvent event) {
        nodes.clear();
        circles.clear();
        curves.clear();
        pathElements.clear();
        points.clear();
    }

    @FXML
    private void startAnimation(ActionEvent event) {
        pathTransition.play();
        buttonStop.setDisable(false);
        buttonStart.setDisable(true);
    }

    @FXML
    private void stopAnimation(ActionEvent event) {
        pathTransition.stop();
        buttonStop.setDisable(true);
        buttonStart.setDisable(false);
    }

    @FXML
    private void sliderChanged(DragEvent event) {
        stopAnimation(null);
        pathTransition.setDuration(new Duration(sliderTime.getValue()));
        startAnimation(null);
    }

    @FXML
    private void mouseClicked(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        switch (event.getButton()) {
            case PRIMARY:
                Point2D point = new Point2D(x, y);
                Circle circ = new Circle(R);
                circ.setStroke(Color.BLACK);
                circ.setFill(Color.YELLOW);
                circ.setTranslateX(x);
                circ.setTranslateY(y);
                circles.add(circ);
                points.add(point);
                nodes.add(circ);
                if ((points.size() - 1) % 3 == 0 && points.size() > 3) {
                    checkCurves();
                }
                break;
        }
    }

    private void checkCurves() {
        nodes.clear();
        pathElements.clear();
        for (int i = 0; i < points.size() - 1; i += 3) {
            Point2D st = points.get(i);
            Point2D c1 = points.get(i + 1);
            Point2D c2 = points.get(i + 2);
            Point2D end = points.get(i + 3);
            CubicCurve curve = new CubicCurve(st.getX(), st.getY(), c1.getX(), c1.getY(), c2.getX(), c2.getY(), end.getX(), end.getY());
            curve.setFill(Color.TRANSPARENT);
            curve.setStroke(Color.RED);
            if (i == 0) {
                pathElements.add(new MoveTo(st.getX(), st.getY()));
                imageView.setTranslateX(st.getX());
                imageView.setTranslateY(st.getY());
                imageView.setVisible(true);
                nodes.add(imageView);
            }
            pathElements.add(new CubicCurveTo(c1.getX(), c1.getY(), c2.getX(), c2.getY(), end.getX(), end.getY()));
            imageView.toFront();
            curves.add(curve);
        }
        buttonStart.setDisable(false);
        nodes.addAll(curves);
        nodes.addAll(circles);
    }

}
