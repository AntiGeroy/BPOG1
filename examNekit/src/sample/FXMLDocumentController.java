package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FXMLDocumentController implements Initializable {

    private ObservableList<Node> nodes;
    private ObservableList<Circle> circles;

    private LinearGradient linearGradient;
    private final Stop stops[] = {new Stop(0, Color.LIGHTBLUE), new Stop(0.5, Color.YELLOW), new Stop(1, Color.LIGHTBLUE)};

    private Point2D startPoint;
    private Circle actualni = null;

    @FXML
    private Pane pane;
    @FXML
    private ColorPicker colorPicker;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nodes = pane.getChildren();
        circles = FXCollections.observableArrayList();
        pane.widthProperty().addListener(evt -> resize());
        pane.heightProperty().addListener(evt -> resize());
        colorPicker.setValue(Color.BLACK);
    }

    @FXML
    private void end(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ukončení aplikace");
        alert.setHeaderText(null);
        alert.setContentText("Opravdu chcete ukončit aplikaci?");

        Optional<ButtonType> res = alert.showAndWait();
        if (res.get().equals(ButtonType.OK)) {
            Platform.exit();
            System.exit(0);
        }
    }

    @FXML
    private void clearAll(ActionEvent event) {
        nodes.clear();
        initRect();
    }

    @FXML
    private void mousePressed(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        startPoint = new Point2D(x, y);
        drawCircle(x, y);
    }

    @FXML
    private void mouseDragged(MouseEvent event) {
        drawCircle(event.getX(), event.getY());
    }

    @FXML
    private void mouseReleased(MouseEvent event) {
        drawCircle(event.getX(), event.getY());
        if (!isCircleShowedFull(actualni)) {
            nodes.remove(actualni);
            circles.remove(actualni);
        }
        else{
            System.out.println(actualni.getLayoutBounds().);
        }
        actualni = null;
    }

    private void drawCircle(double endX, double endY) {
        if (actualni != null) {
            nodes.remove(actualni);
        }
        double radius = calcDist(startPoint.getX(), startPoint.getY(), endX, endY);
        Circle circle = new Circle(radius, Color.TRANSPARENT);
        circle.setTranslateX(startPoint.getX());
        circle.setTranslateY(startPoint.getY());
        circle.setStroke(colorPicker.getValue());
        //circle.setStrokeWidth(5);
        circle.setOpacity(0.5);
        circle.setFill(determineCircleColor(circle));
        actualni = circle;
        nodes.add(circle);
        circles.add(circle);
    }

    private Color determineCircleColor(Circle c) {
        double x = startPoint.getX();
        double y = startPoint.getY();
        double rad = c.getRadius();
        double width = pane.getWidth();
        double height = pane.getHeight();
        // if (startPoint inside rectangle) { if(} else  }
        if (x > width / 4 && y > height / 4 && x < width * 3 / 4 && y < height * 3 / 4) {
            if (x - rad < width / 4 || y - rad < height / 4 || x + rad > width * 3 / 4 || y + rad > height * 3 / 4) {
                return Color.WHITE;
            } else {
                return Color.BLUE;
            }
        } else {
            if (x < width / 4) {
                return x + rad < width / 4 ? Color.GREEN : Color.WHITE;
            } else if (x > width * 3 / 4) {
                return x - rad > width * 3 / 4 ? Color.GREEN : Color.WHITE;
            } else {
                if (y < height / 4) {
                    return y + rad < height / 4 ? Color.GREEN : Color.WHITE;
                } else {
                    return y - rad > height * 3 / 4 ? Color.GREEN : Color.WHITE;
                }
            }
        }
    }

    private boolean isCircleShowedFull(Circle c) {
        double x = c.getTranslateX();
        double y = c.getTranslateY();
        double rad = c.getRadius();
        if (x - rad < 0 || x + rad > pane.getWidth() || y - rad < 0 || y + rad > pane.getHeight()) {
            return false;
        } else {
            return true;
        }
    }

    private double calcDist(Node n, Node m) {
        return calcDist(n.getTranslateX(), n.getTranslateY(), m.getTranslateX(), m.getTranslateY());
    }

    private double calcDist(double x, double y, double dx, double dy) {
        return Math.sqrt((dx - x) * (dx - x) + (dy - y) * (dy - y));
    }

    private void initRect() {
        if (nodes.size() > 0) {
            nodes.remove(0);
        }
        double width = pane.getWidth();
        double height = pane.getHeight();
        Rectangle r = new Rectangle(width / 2, height / 2);
        r.setTranslateX(width / 4);
        r.setTranslateY(height / 4);
        r.setStroke(Color.RED);
        r.setStrokeWidth(1);
        r.setFill(Color.TRANSPARENT);
        nodes.add(0, r);
    }

    private void resize() {
        initRect();
        double width = pane.getWidth();
        double height = pane.getHeight();
        linearGradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
        pane.setBackground(new Background(new BackgroundFill(linearGradient, CornerRadii.EMPTY, Insets.EMPTY)));
    }

}
