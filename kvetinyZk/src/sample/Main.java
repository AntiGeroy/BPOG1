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
import javafx.collections.ObservableList;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

public class Main extends Application {

    private Image pattern = null;
    private Rectangle imagePattern = null;

    private ColorPicker colorPicker;
    private TextField radiusTextField;

    private CheckBox checkBox;

    private Slider slider;

    private Pane pane;

    @Override
    public void start(Stage primaryStage) {

        HBox hBox = new HBox();
        //hBox.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        VBox outerVBox = new VBox();
        outerVBox.setAlignment(Pos.CENTER);

        VBox innerVBox = new VBox(10);
        innerVBox.setPadding(new Insets(10));
        VBox.setMargin(innerVBox, new Insets(10));
        VBox.setVgrow(innerVBox, Priority.ALWAYS);
        innerVBox.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, new CornerRadii(10), null)));

        HBox firstRow = new HBox();
        firstRow.setSpacing(10);
        Label colorLabel = new Label("Barva okraje:");
        colorPicker = new ColorPicker(Color.RED);
        HBox.setHgrow(colorPicker, Priority.ALWAYS);
        firstRow.getChildren().addAll(colorLabel, colorPicker);

        HBox secondRow = new HBox();
        secondRow.setSpacing(10);
        Label raduiusLabel = new Label("Polomer");
        radiusTextField = new TextField();
        HBox.setHgrow(radiusTextField, Priority.ALWAYS);
        secondRow.getChildren().addAll(raduiusLabel, radiusTextField);

        HBox thirdRow = new HBox();
        thirdRow.setSpacing(10);
        Label checkBoxLabel = new Label("Kreslit okraj");
        checkBox = new CheckBox();
        checkBox.setSelected(true);
        thirdRow.getChildren().addAll(checkBoxLabel, checkBox);

        HBox fourthRow = new HBox();
        fourthRow.setSpacing(10);
        Label countLabel = new Label("Pocet");
        slider = new Slider(1, 5, 1);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        HBox.setHgrow(slider, Priority.ALWAYS);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
        fourthRow.getChildren().addAll(countLabel, slider);

        HBox imageHBox = new HBox();
        imagePattern = new Rectangle(0, 0, 200, 200);
        imagePattern.setOnMouseClicked(event -> {
            if (event.getButton() != MouseButton.PRIMARY){
                return;
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BMP+JPG+PNG+GIF", "*.bmp", "*.jpg", "*.png", "*.gif"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Všechny soubory", "*.*"));

            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null){
                try {
                    pattern = new Image(new FileInputStream(file));
                    imagePattern.setFill(new ImagePattern(pattern));

                } catch (Exception e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Chyba nacitani obrazku");
                    alert.setContentText("Vybrany obrazek se nepovedlo nacist");
                    alert.showAndWait();
                }
            }
        });
        imagePattern.setFill(Color.GRAY);
        imagePattern.setStroke(Color.BLUE);
        imageHBox.setAlignment(Pos.CENTER);
        imageHBox.getChildren().add(imagePattern);

        HBox lastRow = new HBox();
        lastRow.setAlignment(Pos.CENTER);
        Button generateButton = new Button("Generuj");
        generateButton.setOnAction(actionEvent -> {
            generateCircles();
        });
        generateButton.setMaxWidth(Double.MAX_VALUE);
        lastRow.getChildren().add(generateButton);
        HBox.setHgrow(generateButton, Priority.ALWAYS);

        innerVBox.getChildren().addAll(firstRow, secondRow, thirdRow, fourthRow, imageHBox, lastRow);
        innerVBox.setAlignment(Pos.CENTER);
        outerVBox.getChildren().add(innerVBox);
        hBox.getChildren().add(outerVBox);


        pane = new Pane();
        pane.heightProperty().addListener(observable -> {clearPane();});
        pane.widthProperty().addListener(observable -> {clearPane();});
        pane.setBackground(new Background(new BackgroundFill(Color.web("C0FFC0"), null, null)));
        hBox.getChildren().add(pane);
        HBox.setHgrow(pane, Priority.ALWAYS);


        Scene scene = new Scene(hBox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void generateCircles(){
        Double radius = 0d;
        String radiusString = radiusTextField.getText();
        try {
            radius = Double.parseDouble(radiusString);
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba vstupnich hodnot");
            alert.setContentText("Zvolena hodnota neni v rozsahu <10, 200>");
            alert.showAndWait();
            return;
        }

        if (radius < 10 || radius > 200){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba vstupnich hodnot");
            alert.setContentText("Zvolena hodnota neni v rozsahu <10, 200>");
            alert.showAndWait();
            return;
        }

        Color color = colorPicker.getValue();
        boolean addBorder = checkBox.isSelected();
        int count = (int) slider.getValue();

        double paneWidth = pane.getWidth();
        double paneHeight = pane.getHeight();
        System.out.println("Pane Width: " + paneWidth);
        System.out.println("Pane Height: " + paneHeight);
        if (2 * radius > paneHeight || 2 * radius > paneWidth){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba generovani");
            alert.setContentText("Okno je prilis male pro zadany polomer. Kruznice nelze generovat.");
            alert.showAndWait();
            return;
        }

        for (int i = 0; i < count; i++){
            Circle circle = new Circle();
            circle.setRadius(radius);
            if (pattern == null){
                circle.setFill(Color.TRANSPARENT);
            }
            else {
                circle.setFill(new ImagePattern(pattern));
            }
            if (addBorder){
                circle.strokeWidthProperty().setValue(3);
                circle.setStroke(color);
            }

            Random r = new Random();
            double randomXValue = radius + (paneWidth - 2 * radius) * r.nextDouble();                                  //rangeMin + (rangeMax - rangeMin) * r.nextDouble()
            double randomYValue = radius + (paneHeight - 2 * radius) * r.nextDouble();

            circle.setTranslateX(randomXValue);
            circle.setTranslateY(randomYValue);

            circle.setOnMouseClicked(event -> {
                if (event.getButton() != MouseButton.SECONDARY){
                    return;
                }
                pane.getChildren().remove(circle);
            });

            pane.getChildren().add(circle);

        }

    }

    private void clearPane(){
        pane.getChildren().clear();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }


}
