package sample;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


public class PersonDialog extends Dialog<Person> {

    private Person person;

    private TextField firstNameTextField;
    private TextField lastNameTextField;

    public PersonDialog(Person person) {
        super();
        this.setTitle("Add person");
        this.person = person;
        buildUI();
        setResultConverter();
    }

    private void buildUI(){
        Pane pane = createGridPane();
        getDialogPane().setContent(pane);
        ImageView imageView = new ImageView("sample/addImage.png");
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        getDialogPane().setGraphic(imageView);

        if (person != null && person.getFirstName() != null){
            firstNameTextField.setText(person.getFirstName());
        }
        if (person != null && person.getLastName() != null){
            lastNameTextField.setText(person.getLastName());
        }

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button button = (Button) getDialogPane().lookupButton(ButtonType.OK);
        button.addEventFilter(ActionEvent.ACTION, event -> {
            if (!validateDialog()){
                event.consume();
            }
            else {
                person.setFirstName(firstNameTextField.getText());
                person.setLastName(lastNameTextField.getText());
            }
        });

        getDialogPane().expandableContentProperty().set(new Label("This is the expandable content area"));
        getDialogPane().setExpanded(true);
    }

    private boolean validateDialog(){
        return !(firstNameTextField.getText().isBlank() || lastNameTextField.getText().isBlank());
    }

    private Pane createGridPane(){
        VBox content = new VBox(10);

        Label firstNameLabel = new Label("First Name");
        Label lastNameLabel = new Label("Last Name");
        firstNameTextField = new TextField();
        lastNameTextField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setVgap(5);
        gridPane.setHgap(10);
        gridPane.add(firstNameLabel, 0, 0);
        gridPane.add(firstNameTextField, 1, 0);
        gridPane.add(lastNameLabel, 0, 1);
        gridPane.add(lastNameTextField, 1, 1);
        GridPane.setHgrow(firstNameTextField, Priority.ALWAYS);
        GridPane.setHgrow(lastNameTextField, Priority.ALWAYS);

        content.getChildren().add(gridPane);
        return content;
    }

    private void setResultConverter(){
        Callback<ButtonType, Person> personResultConverter = buttonType -> {
            if (buttonType == ButtonType.OK){
                return person;
            }
            else {
                return null;
            }
        };
        setResultConverter(personResultConverter);
    }
}
