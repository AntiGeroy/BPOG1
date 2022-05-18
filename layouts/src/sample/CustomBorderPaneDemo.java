package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class CustomBorderPaneDemo extends Application {


    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();

        HBox menu = createMenu();
        HBox toolBar = createToolBar();

        VBox topVBox = new VBox(menu, toolBar);
        borderPane.setTop(topVBox);

        VBox formVBox = createForm();
        borderPane.setLeft(formVBox);

        VBox table = createTable();
        borderPane.setCenter(table);

        HBox statusBar = createStatusBar();
        borderPane.setBottom(statusBar);

        stage.setScene(new Scene(borderPane, 800, 500));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private HBox createMenu(){
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As ...");
        fileMenu.getItems().addAll(openItem, saveItem, saveAsItem);

        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");

        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
        HBox hBox = new HBox(menuBar);
        HBox.setHgrow(menuBar, Priority.ALWAYS);
        return hBox;
    }

    private HBox createToolBar(){
        Button openButton = createButton("Open");
        Button saveButton = createButton("Save");
        Button exitButton = createButton("Exit");

        ToolBar toolBar = new ToolBar(openButton, saveButton, exitButton);
        HBox hBox = new HBox(toolBar);
        HBox.setHgrow(toolBar, Priority.ALWAYS);
        return hBox;
    }

    private Button createButton(String text){
        Button button = new Button(text);
        return button;
    }

    private VBox createForm(){
        GridPane gridPane = new GridPane();

        Label firstNameLabel = new Label("First Name");
        Label lastNameLabel = new Label("Last Name");

        TextField firstNameTextField = new TextField();
        TextField lastNameTextField = new TextField();

        Button saveButton = new Button("Save");

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(saveButton);

        gridPane.add(firstNameLabel, 0, 0, 1, 1);
        gridPane.add(firstNameTextField, 1, 0, 1, 1);
        gridPane.add(lastNameLabel, 0, 1, 1, 1);
        gridPane.add(lastNameTextField, 1, 1, 1, 1);
        gridPane.add(buttonBar, 0, 2, 2, 1);

        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(gridPane);
        vBox.setPadding(new Insets(10));
        VBox.setVgrow(gridPane, Priority.ALWAYS);
        return vBox;
    }

    private VBox createTable(){
        TableView<Person> tableView = new TableView<>();
        TableColumn<Person, String> firstNameColumn = new TableColumn<>("First Name");
        TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
        tableView.getColumns().addAll(firstNameColumn, lastNameColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tableView);
        vBox.setPadding(new Insets(10));
        VBox.setVgrow(tableView, Priority.ALWAYS);
        return vBox;
    }

    private HBox createStatusBar(){
        Label label = new Label("Ready");
        HBox hBox = new HBox(label);
        hBox.setPadding(new Insets(3));
        HBox.setHgrow(label, Priority.ALWAYS);
        return hBox;
    }
}
