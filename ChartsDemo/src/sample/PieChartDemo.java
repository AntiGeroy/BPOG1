package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PieChartDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        HBox root = new HBox();
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 450, 330);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Apache", 52),
                new PieChart.Data("Nginx", 31),
                new PieChart.Data("IIS", 12),
                new PieChart.Data("LiteSpeed", 2),
                new PieChart.Data("Google server", 1),
                new PieChart.Data("Others", 2)
        );

        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Web servers market share (2016)");

        root.getChildren().add(pieChart);

        stage.setTitle("PieChart");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
