package sample;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ListView<Person> listView;

    @FXML
    private TextField firstField;

    @FXML
    private TextField lastField;

    @FXML
    private TextArea notesArea;

    @FXML
    private Button newButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    private ObservableList<Person> personList;

    private void handleKeyAction(KeyEvent event){
        Person person = listView.getSelectionModel().getSelectedItem();
        if (person != null){
            person.setModified(true);
        }
    }

    private ChangeListener<Person> changeListener =
            (observable, oldValue, newValue) -> {
                newValue.modifiedProperty().set(false);
                if (newValue != null){
                    firstField.setText(newValue.getFirstName());
                    lastField.setText(newValue.getLastName());
                    notesArea.setText(newValue.getNotes());
                } else {
                    firstField.setText("");
                    lastField.setText("");
                    notesArea.setText("");
                }
            };


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.getSelectionModel().selectedItemProperty().addListener(
              changeListener
        );

        personList = FXCollections.observableArrayList(Person.extractor);

        SortedList<Person> sortedList = new SortedList<>(personList);
        sortedList.setComparator((p1, p2) -> {
            int result = p1.getFirstName().compareToIgnoreCase(p2.getFirstName());
            if (result == 0) {
                result = p1.getLastName().compareTo(p2.getLastName());
            }
            return result;
        });
        listView.setItems(sortedList);

        newButton.disableProperty().bind(
                listView.getSelectionModel().selectedItemProperty().isNotNull()
                        .or(firstField.textProperty().isEmpty())
                        .or(lastField.textProperty().isEmpty())
        );

        newButton.setOnAction(event -> {
            Person person = new Person(firstField.getText(), lastField.getText(), notesArea.getText());
            personList.add(person);
            listView.getSelectionModel().select(person);
        });

        updateButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull()
                //.or(listView.getSelectionModel().getSelectedItem().modifiedProperty().not())
                .or(firstField.textProperty().isEmpty())
                .or(lastField.textProperty().isEmpty()));

        updateButton.setOnAction(event -> {
            Person p = listView.getSelectionModel().getSelectedItem();
            listView.getSelectionModel().selectedItemProperty().removeListener(changeListener);
            p.setFirstName(firstField.getText());
            p.setLastName(lastField.getText());
            p.setNotes(notesArea.getText());
            listView.getSelectionModel().selectedItemProperty().addListener(changeListener);
            p.modifiedProperty().set(false);
        });

        deleteButton.disableProperty().bind(listView.getSelectionModel().selectedItemProperty().isNull());

        deleteButton.setOnAction(event -> {
            personList.remove(listView.getSelectionModel().getSelectedItem());
        });

    }
}
