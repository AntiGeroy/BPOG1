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
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private double rectSize = 30;

    private Pane pane;
    private ColorPicker colorPicker;

    private Paint savedColor;

    @Override
    public void start(Stage primaryStage) {

        VBox root = new VBox();

        pane = new Pane();
        pane.setPrefWidth(400);
        pane.setPrefHeight(400);

        colorPicker = new ColorPicker(Color.RED);

        pane.setOnMouseClicked(mouseEvent -> {
            reactToMouseClickOnPane(mouseEvent);
        });



        root.getChildren().addAll(colorPicker, pane);

        primaryStage.setScene(new Scene(root));

        primaryStage.setTitle("Simple Shape Draw");
        primaryStage.show();
    }

    public void reactToMouseClickOnPane(MouseEvent mouseEvent){
        switch (mouseEvent.getButton()){
            case PRIMARY:
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();
                Rectangle rect = new Rectangle(x - rectSize / 2, y - rectSize / 2,
                        rectSize, rectSize);
                rect.setFill(colorPicker.getValue());
                rect.setOnMouseClicked(mouseEvent1 -> {
                    reactToMouseClickOnShape(mouseEvent1);
                });
                rect.setOnMouseEntered(mouseEvent2 -> {
                    savedColor = rect.getFill();
                    rect.setFill(Color.BLUE);
                });
                rect.setOnMouseExited(mouseEvent3 -> {
                    rect.setFill(savedColor);
                });

                pane.getChildren().add(rect);
                break;
        }
    }

    public void reactToMouseClickOnShape(MouseEvent mouseEvent){
        switch (mouseEvent.getButton()){
            case SECONDARY:
                pane.getChildren().remove(mouseEvent.getSource());
                break;
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}
