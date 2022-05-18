package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Simple Table View");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 500, 250, Color.WHITE);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        root.setCenter(gridPane);

        Label candidatesLabel = new Label("Boss");
        GridPane.setHalignment(candidatesLabel, HPos.CENTER);
        gridPane.add(candidatesLabel, 0, 0);

        ObservableList<Person> leaders = getPeople();
        final ListView<Person> leadersListView = new ListView<>(leaders);
        leadersListView.setPrefWidth(150);
        leadersListView.setMaxWidth(Double.MAX_VALUE);
        leadersListView.setPrefHeight(150);

        leadersListView.setCellFactory(new Callback<ListView<Person>, ListCell<Person>>() {
            @Override
            public ListCell<Person> call(ListView<Person> personListView) {
                Label leadLabel = new Label();
                Tooltip tooltip = new Tooltip();
                ListCell<Person> cell = new ListCell<Person>() {
                    @Override
                    protected void updateItem(Person item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null){
                            leadLabel.setText(item.getAliasName());
                            setText(item.getFirstName() + " " + item.getLastName());
                            tooltip.setText(item.getAliasName());
                            setTooltip(tooltip);
                        }
                    }
                };

                return cell;
            }
        });

        gridPane.add(leadersListView, 0, 1);

        Label emplLabel = new Label("Employees");
        gridPane.add(emplLabel, 2, 0);
        GridPane.setHalignment(emplLabel, HPos.CENTER);

        final TableView<Person> employeeTableView = new TableView<>();
        employeeTableView.setPrefWidth(300);

        final ObservableList<Person> teamMembers = FXCollections.observableArrayList();
        employeeTableView.setItems(teamMembers);

        TableColumn<Person, String> aliasNameColumn = new TableColumn<>("Alias");
        aliasNameColumn.setEditable(true);
        aliasNameColumn.setCellValueFactory(new PropertyValueFactory<>("aliasName"));
        aliasNameColumn.setPrefWidth(employeeTableView.getPrefWidth() / 3);

        TableColumn<Person, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setPrefWidth(employeeTableView.getPrefWidth() / 3);

        TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setPrefWidth(employeeTableView.getPrefWidth() / 3);

        employeeTableView.getColumns().setAll(aliasNameColumn, firstNameColumn, lastNameColumn);
        gridPane.add(employeeTableView, 2, 1);

        leadersListView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (observableValue != null && observableValue.getValue() != null){
                teamMembers.clear();
                teamMembers.addAll(observableValue.getValue().employeesProperty());
            }
        }));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Person> getPeople() {
        ObservableList<Person> people = FXCollections.<Person>observableArrayList();
        Person docX = new Person("Professor X", "Charles", "Xavier");
        docX.employeesProperty().add(new Person("Wolverine", "James", "Howlett"));
        docX.employeesProperty().add(new Person("Cyclops", "Scott", "Summers"));
        docX.employeesProperty().add(new Person("Storm", "Ororo", "Munroe"));
        Person magneto = new Person("Magneto", "Max", "Eisenhardt");
        // ...code to add employees
        Person biker = new Person("Mountain Biker", "Jonathan", "Gennick");
        // ...code to add employees
        people.addAll(docX, magneto, biker);
        return people;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
