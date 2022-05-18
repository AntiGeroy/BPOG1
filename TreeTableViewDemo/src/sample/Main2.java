package sample;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;


public class Main2 extends Application {

    List<Employee> employees = Arrays.<Employee>asList(
            new Employee("Ethan Williams", "ethan.williams@example.com"),
            new Employee("Emma Jones", "emma.jones@example.com"),
            new Employee("Michael Brown", "michael.brown@example.com"),
            new Employee("Anna Black", "anna.black@example.com"),
            new Employee("Rodger York", "roger.york@example.com"),
            new Employee("Susan Collins", "susan.collins@example.com")
    );

    TreeItem<Employee> root = new TreeItem<>(new Employee("Sales Department"));

    @Override
    public void start(Stage stage) throws Exception {
        root.setExpanded(true);

        employees.stream().forEach(employee -> {
            root.getChildren().add(new TreeItem<>(employee));
        });

        stage.setTitle("Tree Table View Demo");

        Group rootElem = new Group();
        Scene scene = new Scene(rootElem, 400, 400);
        scene.setFill(Color.LIGHTGRAY);

        TreeTableColumn<Employee, String> empColumn = new TreeTableColumn<>("Employee");
        empColumn.setPrefWidth(150);
        empColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getValue().getName()));

        TreeTableColumn<Employee, String> emailColumn = new TreeTableColumn<>("Email");
        emailColumn.setPrefWidth(190);
        emailColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getValue().getEmail()));

        TreeTableView<Employee> treeTableView = new TreeTableView<>(root);
        treeTableView.getColumns().addAll(empColumn, emailColumn);
        rootElem.getChildren().add(treeTableView);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
