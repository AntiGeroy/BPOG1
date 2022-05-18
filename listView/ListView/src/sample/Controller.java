package sample;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private ListView<Book> bookListView;

    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<String> authorCombobox;

    @FXML
    private Spinner<String> genreSpinner;

    @FXML
    private TextField priceTextField;

    @FXML
    private Spinner<Integer> countSpinner;

    private Book selectedBook;

    private void actualizeValues(){
        nameTextField.setText(selectedBook.getName());
        genreSpinner.getValueFactory().setValue(selectedBook.getGenre());
        authorCombobox.setValue(selectedBook.getAuthor());
        priceTextField.setText(selectedBook.getPrice().toString());
        countSpinner.getValueFactory().setValue(selectedBook.getCount());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> genres = FXCollections.observableArrayList("Sci-fi", "Non-fiction");
        ObservableList<String>  authors = FXCollections.observableArrayList("Steven King", "Elton Jonh");
        ObservableList<Book> books = FXCollections.observableArrayList(
                new Book("Space Odyssey", "Sci-fi", "Elton Jonh", 1, 150),
                new Book("How to graduate", "Non-fiction", "Steven King", 1, 250)
        );

        selectedBook = books.get(0);
        bookListView.setItems(books);
        bookListView.getSelectionModel().select(selectedBook);
        bookListView.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            selectedBook = newValue;
            actualizeValues();
        }));

        bookListView.setCellFactory(v -> new ListCell<Book>() {
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName() + ", " + item.getAuthor() + ", Cena: " + item.getPrice() + ", Počet: " + item.getCount() + "ks");
                } else {
                    setText(null);
                }
            }
        });

        authorCombobox.setItems(authors);
        authorCombobox.setValue(authors.get(0));

        genreSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(genres));
        genreSpinner.setEditable(true);


        countSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        genreSpinner.setEditable(true);

    }
}
