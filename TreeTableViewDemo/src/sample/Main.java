package sample;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;



public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tree Table View Demo");
        Group root = new Group();
        Scene scene = new Scene(root, 200, 400);

        TreeItem<String> childNode1 = new TreeItem<>("Child Node 1");
        TreeItem<String> childNode2 = new TreeItem<>("Child Node 2");
        TreeItem<String> childNode3 = new TreeItem<>("Child Node 3");

        TreeItem<String> rootItem = new TreeItem<>("Root node");
        rootItem.setExpanded(true);
        rootItem.getChildren().addAll(childNode1, childNode2, childNode3);


        TreeTableColumn<String, String> column = new TreeTableColumn<>("Column");
        column.setPrefWidth(150);

        column.setCellValueFactory(cell -> new ReadOnlyStringWrapper(cell.getValue().getValue()));

        TreeTableView<String> treeTableView = new TreeTableView<>(rootItem);
        treeTableView.getColumns().add(column);
        treeTableView.setPrefWidth(152);
        treeTableView.setShowRoot(true);
        root.getChildren().add(treeTableView);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
