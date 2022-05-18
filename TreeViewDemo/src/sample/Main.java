package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;



public class Main extends Application {

    private TreeView<String> treeView;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tree View Demo");

        ImageView rootIcon = new ImageView("sample/folder.jpg");

        rootIcon.setFitHeight(16);
        rootIcon.setFitWidth(16);

        TreeItem<String> rootItem = new TreeItem<>("Inbox", rootIcon);
        rootItem.setExpanded(true);
        for (int i = 0; i < 5; i++){
            TreeItem<String> item = new TreeItem<>("Message " + (i +1));
            rootItem.getChildren().add(item);
        }

        treeView = new TreeView<>(rootItem);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(treeView);
        stage.setScene(new Scene(stackPane, 300, 250));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
