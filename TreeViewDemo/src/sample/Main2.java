package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.List;


public class Main2 extends Application {

    private TreeView<String> treeView;
    private List<Employee> employees = Arrays.<Employee>asList(
            new Employee("Ethan Williams", "Sales Department"),
            new Employee("Emma Jones", "Sales Department"),
            new Employee("Michael Brown", "Sales Department"),
            new Employee("Anna Black", "Sales Department"),
            new Employee("Rodger York", "Sales Department"),
            new Employee("Susan Collins", "Sales Department"),
            new Employee("Mike Graham", "IT Support"),
            new Employee("Judy Mayer", "IT Support"),
            new Employee("Gregory Smith", "IT Support"),
            new Employee("Jacob Smith", "Accounts Department"),
            new Employee("Isabella Johnson", "Accounts Department")
    );

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Tree View Demo");

        ImageView rootIcon = new ImageView("sample/root.jpg");
        rootIcon.setFitHeight(16);
        rootIcon.setFitWidth(16);

        Image departmentIcon = new Image("sample/folder.jpg");



        TreeItem<String> rootItem = new TreeItem<>("MyCompany Human Resources", rootIcon);
        rootItem.setExpanded(true);


        for (Employee employee : employees){
            TreeItem<String> empLeaf = new TreeItem<>(employee.getName());
            boolean found = false;
            for (TreeItem<String> depNode : rootItem.getChildren()){
                if (depNode.getValue().contentEquals(employee.getDepartment())){
                    depNode.getChildren().add(empLeaf);
                    found = true;
                    break;
                }
            }

            if (!found){
                ImageView imageView = new ImageView(departmentIcon);
                imageView.setFitWidth(16);
                imageView.setFitHeight(16);
                TreeItem<String> depNode = new TreeItem<>(employee.getDepartment(), imageView);
                rootItem.getChildren().add(depNode);
                depNode.getChildren().add(empLeaf);
            }
        }

        treeView = new TreeView<>(rootItem);

        treeView.setEditable(true);
        treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> stringTreeView) {
                return new TextFieldTreeCellImpl();
            }
        });

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(treeView);
        stage.setScene(new Scene(stackPane, 300, 250));
        stage.show();
    }

    private final class TextFieldTreeCellImpl extends TreeCell<String>{
        private TextField textField;
        private ContextMenu addMenu = new ContextMenu();

        public TextFieldTreeCellImpl() {
            MenuItem addMenuItem = new MenuItem("Add Employee");
            addMenu.getItems().add(addMenuItem);
            addMenuItem.setOnAction(actionEvent -> {
                TreeItem<String> newEmployee = new TreeItem<>("New employee");
                getTreeItem().getChildren().add(newEmployee);
            });
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (textField == null){
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        protected void updateItem(String s, boolean empty) {
            super.updateItem(s, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            }
            else {
                if (isEditing()){
                    if (textField != null){
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                }
                else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                    if (!getTreeItem().isLeaf() && getTreeItem().getParent() != null){
                        setContextMenu(addMenu);
                    }
                }
            }
        }

        private void createTextField(){
            textField = new TextField(getString());
            textField.setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.ENTER){
                    commitEdit(textField.getText());
                } else if (event.getCode() == KeyCode.ESCAPE){
                    cancelEdit();
                }
            });
        }

        private String getString(){
            return getItem() == null ? "" : getItem().toString();
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
