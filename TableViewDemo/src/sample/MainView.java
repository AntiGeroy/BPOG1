package sample;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainView {

    private Stage stage;
    private PersonTableView personTableView;

    public MainView() {
        buildUI();
    }

    private void buildUI() {
        stage = new Stage(StageStyle.DECORATED);

        BorderPane root = new BorderPane();

        personTableView = new PersonTableView();
        personTableView.add(new Person("John", "Doe", 32));
        personTableView.add(new Person("Jane", "Doe", 34));

        root.setCenter(personTableView);


        Scene scene = new Scene(root, 500, 300);

        stage.setScene(scene);
        stage.setTitle("TableView Demo");
        stage.show();
    }
}
