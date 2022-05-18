/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package terc;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author micha
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private AnchorPane paneKresly;
    @FXML
    private AnchorPane paneGraph;
    int count1;
    int count2;
    int count3;
    private PieChart pieChart;
    PieChart pie;
    
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        Circle circle = new Circle();
//        circle.setRadius(paneKresly.getPrefHeight()/3*2);
//        circle.setTranslateX(paneKresly.getPrefWidth()/2 + circle.getRadius()/2);
//        circle.setTranslateY(paneKresly.getPrefHeight()/2 + circle.getRadius()/2);
//        circle.setStroke(Color.BLACK);
//        circle.setFill(Color.TRANSPARENT);
//        paneKresly.getChildren().add(circle);
        count1 = 0;
        count2 = 0;
        count3 = 0;
        
        double prefWidth = paneGraph.getPrefWidth();
        double prefHeight = paneGraph.getPrefHeight();
        
        pie = new PieChart();
        pie.setTitle("Terc");
        pie.setPrefHeight(prefHeight);
        pie.setPrefWidth(prefWidth);
        paneGraph.getChildren().add(pie);
        
        Double rectWidth = ((paneKresly.getPrefWidth()/4)*3)/5*3;
        Double rectHeight = paneKresly.getPrefHeight()/3*2;
        
        Double centerX = (paneKresly.getPrefWidth()/2) ;
        Double centerY = (paneKresly.getPrefHeight()/2) ;
        
//        Rectangle rect = new Rectangle(rectWidth, rectHeight);
//        System.out.println(rectWidth);
//        rect.setX(centerX);
//        rect.setY(centerY);
        
        Circle circle = new Circle(centerX, centerY, rectWidth);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown()) {
                    count1++;
                    System.out.println(count1 + ":" + count2 + ":" + count3);
                    ChangeCount();
                }
            }
        });
        
        Circle circle2 = new Circle(centerX, centerY, rectWidth/4*3);
        circle2.setFill(Color.WHITE);
        circle2.setStroke(Color.BLACK);
        circle2.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown()) {
                    count2++;
                    System.out.println(count1 + ":" + count2 + ":" + count3);
                    ChangeCount();
                }
            }
        });
        
        Circle circle3 = new Circle(centerX, centerY, rectWidth/2);
        circle3.setFill(Color.WHITE);
        circle3.setStroke(Color.BLACK);
        circle3.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                if (event.isPrimaryButtonDown()) {
                    count3++;
                    System.out.println(count1 + ":" + count2 + ":" + count3);
                    ChangeCount();
                }
            }
        });
        
        paneKresly.getChildren().addAll(circle,circle2,circle3);
    }    
    private void ChangeCount(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Nejvetsi", count1),
                        new PieChart.Data("vetsi", count2),
                        new PieChart.Data("nejmensi", count3)
                );
        paneGraph.getChildren().remove(pie);
        double prefWidth = paneGraph.getWidth();
        double prefHeight = paneGraph.getHeight();
        
        pie = new PieChart(pieChartData);
        pie.setTitle("Terc");
        pie.setPrefHeight(prefHeight);
        pie.setPrefWidth(prefWidth);
        paneGraph.getChildren().add(pie);
    }
}
