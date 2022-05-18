package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ScatterChartDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        HBox root = new HBox();

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis("USD/kg", 30, 50, 2);

        ScatterChart<String, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> data = new XYChart.Series<>();

        data.getData().add(new XYChart.Data<>("Mar 14", 43));
        data.getData().add(new XYChart.Data<>("Nov 14", 38.5));
        data.getData().add(new XYChart.Data<>("Jan 15", 41.8));
        data.getData().add(new XYChart.Data<>("Mar 15", 37));
        data.getData().add(new XYChart.Data<>("Dec 15", 33.7));
        data.getData().add(new XYChart.Data<>("Feb 16", 39.8));

        scatterChart.getData().add(data);
        scatterChart.setLegendVisible(false);
        root.getChildren().add(scatterChart);

        Scene scene = new Scene(root, 450, 330);

        stage.setTitle("Gold price");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
