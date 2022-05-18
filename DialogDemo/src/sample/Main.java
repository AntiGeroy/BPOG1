package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Optional;

public class Main extends Application {

    private Button addButton = new Button("Add Person");
    private ListView<Person> listView = new ListView<>();

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(addButton, listView);

        borderPane.setCenter(vBox);

        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Person> call(ListView<Person> personListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Person person, boolean b) {
                        super.updateItem(person, b);
                        if (b || person == null) {
                            setText(null);
                        } else {
                            setText(person.getFirstName() + " " + person.getLastName());
                        }
                    }
                };
            }
        });

        addButton.setOnAction(actionEvent -> {
            Dialog<Person> personDialog = new PersonDialog(new Person("John", "Dough"));
            Optional<Person> result = personDialog.showAndWait();
            if (result.isPresent()){
                Person person = result.get();
                listView.getItems().add(person);
            }
        });

        Scene scene = new Scene(borderPane, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Dialog Demo");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
