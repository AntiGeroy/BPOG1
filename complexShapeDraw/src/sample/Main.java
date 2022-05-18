package sample;/*
        Licensed to the Apache Software Foundation (ASF) under one
        or more contributor license agreements.  See the NOTICE file
        distributed with this work for additional information
        regarding copyright ownership.  The ASF licenses this file
        to you under the Apache License, Version 2.0 (the
        "License"); you may not use this file except in compliance
        with the License.  You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing,
        software distributed under the License is distributed on an
        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
        KIND, either express or implied.  See the License for the
        specific language governing permissions and limitations
        under the License.
 */


import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Main extends Application {

    private double rectSize = 30;

    private Pane pane;

    private ColorPicker colorPicker;
    private Slider sizeSlider;
    private ComboBox<String> elementCombobox;


    private Paint savedColor;

    private Label coordinates;

    private double deltaX = 0;
    private double deltaY = 0;

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();
        HBox buttonsHBox = createButtons();

        pane = createPane();

        root.getChildren().addAll(pane, buttonsHBox);

        primaryStage.setScene(new Scene(root));

        primaryStage.setTitle("Complex Shape Draw");
        primaryStage.show();
    }

    private Label createCoordinatesLabel(){
        coordinates = new Label("X - 0, Y - 0");
        return coordinates;
    }

    private HBox createButtons(){
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(20, 20, 20, 20));
        container.setSpacing(10);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction((actionEvent) -> {System.exit(0);});

        Label colorLabel = new Label("Color:");

        colorPicker = new ColorPicker(Color.RED);

        Label sizeLabel = new Label("Size:");

        sizeSlider = new Slider();
        sizeSlider.setMin(10);
        sizeSlider.setMax(100);
        sizeSlider.setValue(50);
        sizeSlider.setShowTickLabels(true);
        sizeSlider.setShowTickMarks(true);

        Label elementLabel = new Label("Shape:");

        elementCombobox = new ComboBox<>(FXCollections.observableArrayList("Circle", "Rectangle", "Polygon"));
        elementCombobox.setValue("Circle");

        container.getChildren().addAll(exitButton, colorLabel, colorPicker, sizeLabel, sizeSlider, elementLabel, elementCombobox);

        return container;
    }

    private Pane createPane(){
        pane = new Pane();
        pane.setPrefWidth(400);
        pane.setPrefHeight(500);

        pane.setOnMouseClicked(mouseEvent -> {
            switch (mouseEvent.getButton()){
                case PRIMARY:
                    handleDrawing(mouseEvent);
                    break;
            }
        });

        pane.getChildren().add(createCoordinatesLabel());

        pane.setOnMouseMoved(mouseMovedEvent -> {
            coordinates.setText("X - " + mouseMovedEvent.getSceneX() + ", Y - " + mouseMovedEvent.getSceneY());
        });

        pane.setOnMouseDragged(mouseDraggedEvent -> {
            coordinates.setText("X - " + mouseDraggedEvent.getSceneX() + ", Y - " + mouseDraggedEvent.getSceneY());
        });



        return pane;
    }


    private void handleDrawing(MouseEvent mouseEvent){
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();

        double size = sizeSlider.getValue();

        Color color = colorPicker.getValue();

        String figure = elementCombobox.getValue();

        Shape resultShape = null;

        switch (figure){
            case "Circle":
                resultShape = drawCircle(x, y, size, color);
                break;
            case "Rectangle":
                resultShape = drawRectangle(x, y, size, color);
                break;
            case "Polygon":
                resultShape = drawPolygon(x, y, size, color);
                break;
        }

        Shape finalResultShape = resultShape;
        resultShape.setOnMouseClicked(click -> {
            if (click.getButton().equals(MouseButton.SECONDARY)){
                deleteShape(finalResultShape);
            }
            else if (click.getButton().equals(MouseButton.MIDDLE)){
                startDrag(click, finalResultShape);
            }

        });

        resultShape.setOnMouseEntered(mouseEnteredEvent -> {
            savedColor = finalResultShape.getFill();
            finalResultShape.setFill(Color.AQUA);
        });

        resultShape.setOnMouseExited(mouseExitedEvent -> {
            finalResultShape.setFill(savedColor);
        });

        resultShape.setOnMouseDragged(mouseDraggedEvent -> {
            if (mouseDraggedEvent.getButton().equals(MouseButton.MIDDLE)){
                drag(mouseDraggedEvent, finalResultShape);
            }
        });

        resultShape.setOnMouseReleased(mouseReleasedEvent -> {
            stopDrag(mouseReleasedEvent, finalResultShape);
        });


    }

    private Shape drawCircle(double x, double y, double size, Color color){
        Circle circle = new Circle(x, y, size / 2, color);
        pane.getChildren().add(circle);
        return circle;
    }

    private Shape drawRectangle(double x, double y, double size, Color color){
        Rectangle rectangle = new Rectangle(x - size / 2 , y - size / 2, size, size);
        rectangle.setFill(color);
        pane.getChildren().add(rectangle);
        return rectangle;
    }

    private Shape drawPolygon(double x, double y, double size, Color color){
        Polygon polygon = new Polygon(x - size / 2, y,
                                                x - size / 4, y - size / 2,
                                                x + size / 4, y - size / 2,
                                                x + size / 2, y,
                                                x + size / 4, y + size / 2,
                                                x - size / 4, y + size / 2);
        polygon.setFill(color);
        pane.getChildren().add(polygon);
        return polygon;
    }

    private void startDrag(MouseEvent event, Shape shape){

    }
    private void drag(MouseEvent event, Shape shape){

    }

    private void stopDrag(MouseEvent event, Shape shape){
    }

    private void deleteShape(Shape shape){
        pane.getChildren().remove(shape);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
