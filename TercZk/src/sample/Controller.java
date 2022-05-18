package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private Circle yellowCircle;

    @FXML
    private Circle redCircle;

    @FXML
    private Circle blackCircle;

    @FXML
    private Circle blueCircle;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private MenuItem restoreMenuItem;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Blue zone", 0),
                        new PieChart.Data("Black zone", 0),
                        new PieChart.Data("Red zone", 0),
                        new PieChart.Data("Yellow zone", 0)
                );

        ObservableList<XYChart.Series<String, Number>> barChartData =
                FXCollections.observableArrayList(
                                                new XYChart.Series<>(FXCollections.observableArrayList(new XYChart.Data<String, Number>("Blue zone", 0))),
                                                new XYChart.Series<>(FXCollections.observableArrayList(new XYChart.Data<String, Number>("Blue zone", 0))),
                                                new XYChart.Series<>(FXCollections.observableArrayList(new XYChart.Data<String, Number>("Blue zone", 0))),
                                                new XYChart.Series<>(FXCollections.observableArrayList(new XYChart.Data<String, Number>("Blue zone", 0)))
                );

        /*ObservableList<XYChart.Data<String, Number>> barChartData =
                FXCollections.observableArrayList(
                        new XYChart.Data<String, Number>("Blue zone", 0),
                        new XYChart.Data<String, Number>("Black zone", 0),
                        new XYChart.Data<String, Number>("Red zone", 0),
                        new XYChart.Data<String, Number>("Yellow zone", 0)
                );*/

        //XYChart.Series<String, Number> barChartDataSeries = new XYChart.Series<>();
        //barChartDataSeries.setData(barChartData);

        barChart.setData(barChartData);

        restoreMenuItem.setOnAction(actionEvent -> {
            barChartData.get(0).getData().get(0).setYValue(0);
            barChartData.get(1).getData().get(0).setYValue(0);
            barChartData.get(2).getData().get(0).setYValue(0);
            barChartData.get(3).getData().get(0).setYValue(0);
            pieChartData.get(0).setPieValue(0);
            pieChartData.get(1).setPieValue(0);
            pieChartData.get(2).setPieValue(0);
            pieChartData.get(3).setPieValue(0);
        });

        blueCircle.radiusProperty().bind(
                Bindings.min(
                        stackPane.widthProperty(),
                        stackPane.heightProperty()
                ).divide(2)
        );

        blueCircle.setOnMouseClicked(mouseEvent -> {
            System.out.println("Blue circle clicked");
            pieChartData.get(0).setPieValue(pieChartData.get(0).getPieValue() + 1);
            //barChartData.get(0).setYValue(barChartData.get(0).getYValue().intValue() + 1);
            barChartData.get(0).getData().get(0).setYValue(barChartData.get(0).getData().get(0).getYValue().intValue() + 1);
        });

        blackCircle.radiusProperty().bind(
                blueCircle.radiusProperty()
                        .subtract(blueCircle.radiusProperty().divide(100).multiply(15))
        );

        blackCircle.setOnMouseClicked(mouseEvent -> {
            System.out.println("Black circle clicked");
            pieChartData.get(1).setPieValue(pieChartData.get(1).getPieValue() + 1);
            //barChartData.get(1).setYValue(barChartData.get(1).getYValue().intValue() + 1);
            barChartData.get(1).getData().get(0).setYValue(barChartData.get(1).getData().get(0).getYValue().intValue() + 1);
        });

        redCircle.radiusProperty().bind(
                blueCircle.radiusProperty()
                        .subtract(blueCircle.radiusProperty().divide(100).multiply(35))
        );

        redCircle.setOnMouseClicked(mouseEvent -> {
            System.out.println("Red circle clicked");
            pieChartData.get(2).setPieValue(pieChartData.get(1).getPieValue() + 1);
           // barChartData.get(2).setYValue(barChartData.get(2).getYValue().intValue() + 1);
            barChartData.get(2).getData().get(0).setYValue(barChartData.get(2).getData().get(0).getYValue().intValue() + 1);
        });

        yellowCircle.radiusProperty().bind(
                redCircle.radiusProperty()
                        .subtract(redCircle.radiusProperty().divide(100).multiply(45))
        );

        yellowCircle.setOnMouseClicked(mouseEvent -> {
            System.out.println("Yellow circle clicked");
            pieChartData.get(3).setPieValue(pieChartData.get(1).getPieValue() + 1);
            //barChartData.get(3).setYValue(barChartData.get(3).getYValue().intValue() + 1);
            barChartData.get(3).getData().get(0).setYValue(barChartData.get(3).getData().get(0).getYValue().intValue() + 1);
        });



        /*ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Blue zone", 10),
                        new PieChart.Data("Black zone", 20),
                        new PieChart.Data("Red zone", 10),
                        new PieChart.Data("Yellow zone", 20));*/

        pieChart.setData(pieChartData);


    }
}
