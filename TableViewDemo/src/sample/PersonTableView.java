package sample;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

public class PersonTableView extends VBox {

    private TableView<Person> tableView;
    private TableColumn<Person, String> firstNameColumn;
    private TableColumn<Person, String> lastNameColumn;
    private TableColumn<Person, Integer> ageColumn;
    private TextField firstNameTextField;
    private TextField lastNameTextField;
    private TextField ageTextField;


    public PersonTableView() {
        buildUI();
    }

    private void buildUI() {
        tableView = new TableView<>();
        tableView.setEditable(true);

        firstNameColumn = new TableColumn<>("Name");
        lastNameColumn = new TableColumn<>("Surname");
        ageColumn = new TableColumn<>("Age");

        firstNameColumn.setOnEditCommit(new EventHandler<>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> personStringCellEditEvent) {
                Person person = personStringCellEditEvent.getRowValue();
                person.setFirstName(personStringCellEditEvent.getNewValue());
            }
        });
        lastNameColumn.setOnEditCommit(commitEvent -> {
            Person person = commitEvent.getRowValue();
            person.setLastName(commitEvent.getNewValue());
        });
        ageColumn.setOnEditCommit(commitEvent -> {
            Person person = commitEvent.getRowValue();
            person.setAge(commitEvent.getNewValue());
        });

        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        tableView.getColumns().add(firstNameColumn);
        tableView.getColumns().add(lastNameColumn);
        tableView.getColumns().add(ageColumn);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        firstNameTextField = new TextField();
        firstNameTextField.setPromptText("First Name");
        firstNameTextField.setTooltip(new Tooltip("Enter first fame"));
        lastNameTextField = new TextField();
        lastNameTextField.setPromptText("Last Name");
        lastNameTextField.setTooltip(new Tooltip("Enter last name"));
        ageTextField = new TextField();
        ageTextField.setPromptText("Age");
        ageTextField.setTooltip(new Tooltip("Enter age"));

        HBox textFieldsHBox = new HBox();
        textFieldsHBox.setAlignment(Pos.CENTER);;
        textFieldsHBox.setSpacing(10);
        textFieldsHBox.setPadding(new Insets(10));


        textFieldsHBox.getChildren().addAll(firstNameTextField, lastNameTextField, ageTextField);

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            tableView.getItems().add(new Person(
                    firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    Integer.parseInt(ageTextField.getText())
            ));
            clearFields();
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            Person toDelete = tableView.getSelectionModel().getSelectedItem();
            if (toDelete != null){
                tableView.getItems().remove(toDelete);
                tableView.getSelectionModel().clearSelection();
            }
        });
        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(addButton, deleteButton);

        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(10);
        buttonsHBox.setPadding(new Insets(10));
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.getChildren().addAll(buttonBar);

        getChildren().addAll(tableView, textFieldsHBox, buttonsHBox);
    }

    private void clearFields() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        ageTextField.clear();
        firstNameTextField.requestFocus();
    }

    public void add(Person person){
        tableView.getItems().add(person);
    }
}
