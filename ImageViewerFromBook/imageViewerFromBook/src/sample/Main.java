package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main extends Application {

    private final double MINIMAL_VIEWPORT_DIMENSION = 50;

    private final List<String> imageFiles = new ArrayList<>();
    private int currentIndex = -1;
    public enum ButtonMove{NEXT, PREV};
    private ImageView currentImageView;

    private Rectangle2D viewPort;
    private Rectangle2D originalViewPort;
    private ProgressIndicator progressIndicator;
    private AtomicBoolean loading = new AtomicBoolean();

    double dragStartX = 0;
    double dragStartY = 0;
    double dragEndX = 0;
    double dragEndY = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Photo Viewer");
        Group root = new Group();
        Scene scene = new Scene(root, 551, 400, Color.BLACK);
        scene.getStylesheets().add(
                getClass().getResource("photo-viewer.css").toExternalForm()
        );

        primaryStage.setScene(scene);

        currentImageView = createImageView(scene.widthProperty());

        setupDragNDrop(scene);

        Group buttonGroup = createButtonPanel(scene);
        progressIndicator = createProgressIndicator(scene);

        root.getChildren().addAll(currentImageView, buttonGroup, progressIndicator);
        primaryStage.show();
    }

    private void printInfoAboutViewPort(String name, Rectangle2D viewPort){
        System.out.println("ViewPort " + name + ":");
        System.out.println("Min X: " + viewPort.getMinX() + ", Min Y: " + viewPort.getMinY() + ", width: " + viewPort.getWidth() + ", height: " + viewPort.getHeight());
    }

    private ImageView createImageView(ReadOnlyDoubleProperty widthProperty){
        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(false);
        //originalViewPort = new Rectangle2D(0, 0, imageView.getFitWidth(), imageView.getFitWidth());
        //printInfoAboutViewPort("original", originalViewPort);
        //Rectangle2D viewPortRectangle = new Rectangle2D(originalViewPort.getMinX(), originalViewPort.getMinY(), originalViewPort.getWidth(), originalViewPort.getHeight());
        //imageView.setViewport(viewPortRectangle);
        imageView.fitWidthProperty().bind(widthProperty);

        imageView.setOnScroll(scrollEvent -> {
            imageView.setScaleX(imageView.getScaleX() + (scrollEvent.getDeltaY() / 40));
            imageView.setScaleY(imageView.getScaleY() + (scrollEvent.getDeltaY() / 40));
        });

        /*imageView.setOnScroll(scrollEvent -> {
            double deltaScroll = scrollEvent.getDeltaY();

            double newMinX = viewPort.getMinX() + deltaScroll;
            double newMinY = viewPort.getMinY() + deltaScroll;
            double newWidth = viewPort.getWidth() - newMinX;
            double newHeight = viewPort.getHeight() - newMinY;


            Rectangle2D newViewPort = new Rectangle2D(newMinX, newMinY, newWidth, newHeight);
            printInfoAboutViewPort("Scrolled", newViewPort);
            imageView.setViewport(newViewPort);
            viewPort = newViewPort;


            *//*Rectangle2D newViewPort = new Rectangle2D(
                    //imageView.getViewport().getMinX() + (deltaY / 2) > 0 ? imageView.getViewport().getMinX() + (deltaY / 2) : 0,
                    //imageView.getViewport().getMinY() + (deltaY / 2) > 0 ? imageView.getViewport().getMinY() + (deltaY / 2) : 0,
                    //imageView.getViewport().getWidth() - (deltaY / 2) > 0 ? imageView.getViewport().getWidth() - (deltaY / 2) : 0,
                    //imageView.getViewport().getHeight() - (deltaY / 2) > 0 ? imageView.getViewport().getHeight() - (deltaY / 2) : 0
                    newMinX, newMinY, newWidth, newHeight
            );*//*

        });


        imageView.setOnMousePressed(event -> {
            dragStartX = event.getX();
            dragStartY = event.getY();
        });

        imageView.setOnMouseDragged(event -> {
            dragEndX = event.getX();
            dragEndY = event.getY();
            double deltaX = dragEndX - dragStartX;
            double deltaY = dragEndY - dragStartX;

            double newMinX = viewPort.getMinX() + deltaX;
            if (newMinX < 0){
                newMinX = 0;
            }
            else if (newMinX > originalViewPort.getWidth()){
                newMinX = originalViewPort.getWidth();
            }

            double newMinY = viewPort.getMinY() + deltaY;
            if (newMinY < 0){
                newMinY = 0;
            }
            else if (newMinY > originalViewPort.getHeight()){
                newMinY = originalViewPort.getHeight();
            }

            double newWidth = viewPort.getWidth() + deltaX;
            if (newWidth < 0){
                newWidth = 0;
            }
            else if (newWidth > originalViewPort.getWidth()){
                newWidth = originalViewPort.getWidth();
            }

            double newHeight = viewPort.getHeight() + deltaY;
            if (newHeight < 0){
                newHeight = 0;
            }
            else if (newHeight < originalViewPort.getHeight()){
                newHeight = originalViewPort.getHeight();
            }

            Rectangle2D newViewPort = new Rectangle2D(newMinX, newMinY, newWidth, newHeight);
            printInfoAboutViewPort("New after moving", newViewPort);

            viewPort = newViewPort;
            currentImageView.setViewport(viewPort);

            dragStartX = 0;
            dragStartY = 0;

        });*/

        return imageView;
    }

    private void setupDragNDrop(Scene scene){
        scene.setOnDragOver(dragEvent -> {
            Dragboard db = dragEvent.getDragboard();
            if (db.hasFiles() || (db.hasUrl() && isValidImageFile(db.getUrl()))){
                dragEvent.acceptTransferModes(TransferMode.LINK);
            }
            else {
                dragEvent.consume();
            }
        });

        scene.setOnDragDropped(dragEvent -> {
            Dragboard db = dragEvent.getDragboard();
            if (db.hasFiles() && !db.hasUrl()){
                db.getFiles()
                        .stream()
                        .forEach(file -> {
                            try{
                                addImage(file.toURI().toURL().toString());
                            } catch (MalformedURLException ex) {
                                ex.printStackTrace();
                            }
                        });
            }
            else {
                addImage(db.getUrl());
            }
            if (currentIndex > -1){
                loadImage(imageFiles.get(currentIndex));
            }

            dragEvent.setDropCompleted(true);
            dragEvent.consume();
        });
    }

    private Group createButtonPanel(Scene scene){
        Group buttonGroup = new Group();

        Rectangle buttonArea = new Rectangle(0, 0, 60, 30);
        buttonArea.getStyleClass().add("button-panel");
        buttonGroup.getChildren().add(buttonArea);

        Arc leftButton = new Arc(12, 16, 15, 15, -30, 60);
        leftButton.setType(ArcType.ROUND);
        leftButton.getStyleClass().add("left-arrow");

        leftButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (currentIndex == 0 || loading.get())
                return;

            int indx = gotoImageIndex(ButtonMove.PREV);
            if (indx > -1){
                loadImage(imageFiles.get(indx));
            }
        });

        Arc rightButton = new Arc(12, 16, 15, 15,180 - 30, 60);
        rightButton.setType(ArcType.ROUND);
        rightButton.getStyleClass().add("right-arrow");

        rightButton.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            if (currentIndex == imageFiles.size() - 1 || loading.get())
                return;

            int indx = gotoImageIndex(ButtonMove.NEXT);
            if (indx > -1){
                loadImage(imageFiles.get(indx));
            }
        });

        buttonGroup.getChildren().addAll(leftButton, rightButton);

        buttonGroup.translateXProperty().bind(
                scene.widthProperty().subtract(buttonArea.getWidth() + 6)
        );

        buttonGroup.translateYProperty().bind(
                scene.heightProperty().subtract(buttonArea.getHeight() + 6)
        );

        return buttonGroup;
    }

    private ProgressIndicator createProgressIndicator(Scene scene){
        ProgressIndicator progress = new ProgressIndicator(0);
        progress.setVisible(false);
        progress.layoutXProperty().bind(
                scene.widthProperty().subtract(progress.widthProperty()).divide(2)
        );
        progress.layoutYProperty().bind(
                scene.heightProperty().subtract(progress.heightProperty()).divide(2)
        );
        return progress;
    }

    private boolean isValidImageFile(String url){
        List<String> imgTypes = Arrays.asList(".jpg", ".jpeg", ".png", ".gif", ".bmp");
        return imgTypes.stream().anyMatch(t -> url.endsWith(t));
    }

    private void addImage(String url){
        if (isValidImageFile(url)){
            currentIndex += 1;
            imageFiles.add(currentIndex, url);
        }
    }

    private int gotoImageIndex(ButtonMove direction){
        int size = imageFiles.size();
        if (size == 0){
            currentIndex = -1;
        }
        else if (direction == ButtonMove.NEXT && size > 1 && currentIndex < size - 1){
            currentIndex += 1;
        }
        else if (direction == ButtonMove.PREV && size > 1 && currentIndex > 0){
            currentIndex -= 1;
        }

        return currentIndex;
    }

    private Task createWorker(final String url){
        return new Task() {
            @Override
            protected Object call() throws Exception {
                Image image = new Image(url, false);
                Platform.runLater(() -> {
                    currentImageView.setImage(image);
                    originalViewPort = new Rectangle2D(0, 0, image.getWidth(), image.getHeight());
                    viewPort = new Rectangle2D(0, 0, image.getWidth(), image.getHeight());
                    //viewPort = new Rectangle2D(200, 200, image.getWidth() - 200, image.getHeight() - 200);
                    currentImageView.setViewport(viewPort);
                    printInfoAboutViewPort("original", originalViewPort);
                    progressIndicator.setVisible(false);
                    loading.set(false);
                });
                return true;
            }
        };
    }

    private void loadImage(String url){
        if (!loading.getAndSet(true)){
            Task loadImage = createWorker(url);
            progressIndicator.setVisible(true);
            progressIndicator.progressProperty().bind(loadImage.progressProperty());
            new Thread(loadImage).start();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
