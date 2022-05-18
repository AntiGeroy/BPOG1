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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Pane root = new Pane();

        root.setPrefWidth(400);
        root.setPrefHeight(400);

        Rectangle rect = new Rectangle(0, 0, 70, 100);
        rect.setFill(Color.YELLOW);
        rect.setStroke(Color.BLUE);
        rect.setStrokeWidth(3);

        root.getChildren().add(rect);

        Line line = new Line(90, 40, 230, 40);
        line.setStroke(Color.BLACK);

        root.getChildren().addAll(line);

        Circle circle = new Circle(70, 100, 50);
        circle.setFill(Color.color(1.0, 0.0, 0.0, 0.75));

        root.getChildren().add(circle);

        CubicCurve curve = new CubicCurve(100, 200, 150, 250, 200, 120, 350, 180);
        curve.setStroke(Color.AQUA);
        curve.setStrokeWidth(5);
        curve.setFill(null);

        root.getChildren().add(curve);

        primaryStage.setScene(new Scene(root));

        primaryStage.setTitle("Pane and shapes");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
