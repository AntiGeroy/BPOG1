package booksregister1;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.ListSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Window;

/**
 * Controller FXML dokumentu
 * 
 * @author Tomáš Marný
 */

public class FXMLDocumentController implements Initializable {

    /**
     * Deklarace <code>fx-id</code> prvků
     */
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldISBN;
    @FXML
    private TextField textFieldSearch;
    @FXML
    private ComboBox<String> comboBoxAuthor;
    @FXML
    private Spinner<EnumGenre> spinnerGenre;
    @FXML
    private Spinner<Integer> spinnerQuantity;
    @FXML
    private Spinner<Integer> spinnerPrize;
    @FXML
    private ImageView imageViewStatusName;
    @FXML
    private ImageView imageViewStatusIsbn;
    @FXML
    private ImageView imageViewStatusAuthor;
    @FXML
    private ImageView imageViewStatusGenre;
    @FXML
    private ImageView imageViewStatusPrize;
    @FXML
    private ImageView imageViewStatusQuantity;
    @FXML
    private ComboBox<EnumAction> comboBoxAction;
    @FXML
    private ComboBox<EnumFilterBy> comboBoxFilterBy;
    @FXML
    private ComboBox<EnumGenre> comboBoxFilterGenre;
    @FXML
    private ComboBox<String> comboBoxFilterAuthor;
    @FXML
    private ComboBox<EnumSearch> comboBoxSearchBy;
    @FXML
    private ListView<Book> listView;
    @FXML
    private FlowPane flowPaneFilter;
    @FXML
    private FlowPane flowPaneSearch;
    
    /**
     * Deklarace proměnných
     */
    private EnumFilterBy filterBy;
    private Predicate<Book> filter;
    private ObservableList<String> authors;
    private ObservableList<Book> books;
    private ObservableList<Book> listViewBookItems;

    /**
     * Inicializace třídy controller. Nejprve jsou provedeny incializace
     * proměnných {@link #filter}, {@link #authors} a {@link #listViewBookItems}.
     * Následdně jsou nastaveny potřebné továrny hodnot číselníkům {@link Spinner}
     * {@link #spinnerGenre}, {@link #spinnerQuantity} a {@link #spinnerPrize}
     * metodou {@link Spinner#setValueFactory(javafx.scene.control.SpinnerValueFactory) }.
     * Potom jsou přidány hodnoty metodou {@link ObservableList#addAll(java.lang.Object...) }
     * polím s rozbalovacím seznamem {@link ComboBox}. Na závěr je vybrána
     * hodnota prvku {@link #comboBoxSearchBy}.
     * 
     * @param url
     * Používá k vyřešení relativních cest kořenového objektu.
     * Nabývá hodnoty <tt>null</tt>, pokud není nalezeno jeho umístění.
     * @param rb 
     * Používá se k lokalizaci cesty ke kořenovému objektu.
     * Nabývá hodnoty <tt>null</tt>, pokud kořenový objekt nebyl nalezen.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.filter = book -> true;
        this.authors = FXCollections.observableArrayList(
                "Václav Havel",
                "Karel Čapek"
        );
        this.books = FXCollections.observableArrayList();
        this.listViewBookItems = this.listView.getItems();
        
        this.spinnerGenre.setValueFactory(
                new ListSpinnerValueFactory(
                        FXCollections.observableArrayList(EnumGenre.values())
                )
        );
        this.spinnerQuantity.setValueFactory(
                new IntegerSpinnerValueFactory(1, 100, 1)
        );
        this.spinnerPrize.setValueFactory(
                new IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1)
        );
        
        this.comboBoxAuthor.setItems(this.authors);
        this.comboBoxFilterBy.getItems().addAll(EnumFilterBy.values());
        this.comboBoxSearchBy.getItems().addAll(EnumSearch.values());
        this.comboBoxAction.getItems().addAll(EnumAction.values());
        this.comboBoxFilterGenre.getItems().addAll(EnumGenre.values());
        
        this.comboBoxSearchBy.getSelectionModel().select(EnumSearch.NAME);
    }

    /**
     * Metoda je volána událostí {@link ActionEvent} při změně výběru položky
     * pole rozbalovacího seznamu {@link ComboBox}. Nejprve je vytvořena
     * proměnná <code>action</code> incializována metodu {@link ComboBox#getValue() }.
     * V případě prázdné hodnoty, bude provádění metody ukonšeno. Následně
     * se zneviditelní a znepřístupní prvky {@link #flowPaneFilter}
     * a {@link #flowPaneSearch}. Dále je zde blok přepínače,
     * <code>switch</code>. Zde se objevují jednotlivé větve <code>case</code>,
     * ve kterých se program dále větví. V první větvi, {@link EnumAction#FILTER},
     * je zobrazen a zpřístupněn prvek {@link #flowPaneFilter}. Ve větvi
     * {@link EnumAction#SEARCH} podobné jako v první, ale je tady zviditelněn
     * a zpřístupněn prvek {@link #flowPaneSearch}. Pokud uživatel vybere
     * možnost {@link EnumAction#NONE}, je na prvku {@link #comboBoxAction}
     * nastaven na výchozí prázdnou hodnotu.
     * 
     * @param event vlastnosti obsluhované události
     */
    @FXML
    private void action(ActionEvent event) {
        final EnumAction action = this.comboBoxAction.getValue();
        if(Objects.isNull(action))
            return;
        this.flowPaneFilter.setDisable(true);
        this.flowPaneFilter.setVisible(false);
        this.flowPaneSearch.setDisable(true);
        this.flowPaneSearch.setVisible(false);
        switch (action) {
            case FILTER:
                this.flowPaneFilter.setDisable(false);
                this.flowPaneFilter.setVisible(true);
                break;
            case SEARCH:
                this.flowPaneSearch.setDisable(false);
                this.flowPaneSearch.setVisible(true);
                break;
            case NONE:
                this.comboBoxAction.getSelectionModel().clearSelection();
                break;
        }
    }

    /**
     * Metoda je obsloužena událostí {@link ActionEvent}.
     * <p>Na začátku je atribut třídy <code>filterBy</code> incializován metodou
     * {@link ComboBox#getValue() }. V případě prázdné proměnné, bude
     * provádění metody ukončeno. Následně je provedeno znepřístupnění
     * a zneviditelnění ovládacích prvků {@link #comboBoxFilterAuthor}
     * a {@link #comboBoxFilterGenre}. Potom přichází na řadu přepánací
     * blok <code>switch</code>, kterým se provede ve větvi:
     * <ul>
     * <li>{@link EnumFilterBy#AUTHOR} obnova hodnot prvku
     * {@link #comboBoxFilterAuthor} a zpřístupnění a zviditepnění prvku
     * {@link #comboBoxFilterAuthor}.</li>
     * <li>{@link EnumFilterBy#GENRE} zpřístupnění a zviditepnění prvku
     * {@link #comboBoxFilterGenre}.</li>
     * <li>{@link EnumFilterBy#NONE} zrušení výbrané hodnoty prvku
     * {@link #comboBoxFilterBy}.</li>
     * </ul>
     * Závěrem je nastaven atribut třídy {@link #filter} a volána metoda
     * {@link #updateListView() }.</p>
     *
     * @param event vlastnosti obsluhované události
     */
    @FXML
    private void filterBy(ActionEvent event) {
        this.filterBy = this.comboBoxFilterBy.getValue();
        if(Objects.isNull(this.filterBy))
            return;
        this.comboBoxFilterAuthor.setDisable(true);
        this.comboBoxFilterAuthor.setVisible(false);
        this.comboBoxFilterGenre.setDisable(true);
        this.comboBoxFilterGenre.setVisible(false);
        switch (this.filterBy) {
            case AUTHOR:
                this.comboBoxFilterAuthor.getItems().addAll(this.authors);
                this.comboBoxFilterAuthor.setDisable(false);
                this.comboBoxFilterAuthor.setVisible(true);
                break;
            case GENRE:
                this.comboBoxFilterGenre.setDisable(false);
                this.comboBoxFilterGenre.setVisible(true);
                break;
            default:
                this.comboBoxFilterBy.getSelectionModel().clearSelection();
                break;
        }
        this.filter = book -> true;
        this.updateListView();
    }

    /**
     * Metoda je volána událostí {@link ActionEvent} při změně výběru položky
     * prvku {@link #filterBy}. Nejprve je pomocí přepínacího bloku
     * <code>switch</code> je provedeno filtrování ve formě příslušné hodnoty
     * lambda výrazu. Na závěr je volána metoda {@link #updateListView()}.
     *
     * @param event vlastnosti obsluhované události
     */
    @FXML
    private void filterValue(ActionEvent event) {
        switch (this.filterBy) {
            case AUTHOR:
                final String author = this.comboBoxFilterAuthor.getValue();
                this.filter = book -> book.getAuthor().equals(author);
                break;
            case GENRE:
                final EnumGenre genre = this.comboBoxFilterGenre.getValue();
                this.filter = book -> book.getGenre().equals(genre);
                break;
        }
        this.updateListView();
    }

    /**
     * Metoda je volána událostí {@link ActionEvent}. Nejprve se deklaruje
     * lokální konstanta <code>type</code>, do které je přiřazena
     * hodnota prvku {@link #comboBoxSearchBy} získaná metodou
     * {@link ComboBox#getValue() }. Následně je vytvořena další lokální
     * konstanta pojmenovaná <code>value</code>. Ta je definována hodnotou prvku
     * {@link #textFieldSearch} metodou {@link TextField#getText() }. Potom
     * se přejde na přepínací blok, <code>switch</code>, který nastaví
     * příslušnou hodnotu atributu třídy {@link #filter} v podobě lambda výrazu.
     * Na závěr je volána metoda {@link #updateListView()}.
     *
     * @param event vlastnosti obsuhované události
     */
    @FXML
    private void search(ActionEvent event) {
        final EnumSearch type = this.comboBoxSearchBy.getValue();
        final String value = this.textFieldSearch.getText();
        switch (type) {
            case NAME:
                this.filter = book -> book.getName().equals(value);
                break;
            case WORDS_FROM_NAME:
                final String[] words = value.split(" ");
                int i = 0;
                for (String word : words) {
                    final Predicate<Book> predicate = book -> book.getName()
                            .toLowerCase().contains(word.toLowerCase());
                    this.filter = i == 0 ? predicate : this.filter.or(predicate);
                    i++;
                }
                break;
        }
        this.updateListView();
    }

    /**
     * Metoda je obsloužena událostí {@link ActionEvent}.
     * Nejdřív je lokální konstanta <code>errors</code> nastavena konstruktorem
     * {@link Errors#Errors() }. Následně jsou volány příslušné statické metody
     * {@link CheckValue#checkString(TextField, ImageView, String, String) },
     * {@link CheckValue#checkInteger(TextField, ImageView, String, String)  },
     * {@link CheckValue#checkComboBox(ComboBox, ImageView, String, String) }
     * a {@link CheckValue#checkSpinner(javafx.scene.control.Spinner, javafx.scene.image.ImageView, java.lang.String, booksregister.Errors) }
     * pro kontrolu vstupních ovládacích prvku {@link #textFieldName},
     * {@link #textFieldISBN}, {@link #comboBoxAuthor}, {@link #spinnerGenre},
     * {@link #spinnerPrize} a {@link #spinnerQuantity}. Potom je provedena
     * podmínka. V ní se zjišťuje, zda správce chyb je prázdný. Pokud tomu tak
     * je, provede se přidání nové instance knihy do kolekce {@link #books}
     * metodou {@link ObservableList#add(java.lang.Object) } a zavolá se metoda
     * {@link #updateListView() }. Jinak se provede vytvoření a zobrazení
     * dialogu typu {@link Alert} s výpisem chybně zadaných a vybraných hodnot.
     *
     * @param event vlastnosti obsluhované události
     */
    @FXML
    private void addBook(ActionEvent event) {
        final Errors errors = new Errors();
        
        CheckValue.checkTextFieldString(
                this.textFieldName, this.imageViewStatusName, "název", errors
        );
        CheckValue.checkTextFieldString(
                this.textFieldISBN, this.imageViewStatusIsbn, "ISBN", errors
        );
        CheckValue.checkComboBox(
                this.comboBoxAuthor, this.imageViewStatusAuthor, "autor", errors
        );
        CheckValue.checkSpinner(
                this.spinnerGenre, this.imageViewStatusGenre, "žánr", errors
        );
        CheckValue.checkSpinner(
                this.spinnerPrize, this.imageViewStatusPrize, "cena", errors
        );
        CheckValue.checkSpinner(
                this.spinnerQuantity, this.imageViewStatusQuantity, "počet",
                errors
        );
        
        if (errors.isEmpty()) {
            this.books.add(
                    new Book(
                            this.textFieldName.getText(),
                            this.comboBoxAuthor.getValue(),
                            this.spinnerGenre.getValue(),
                            this.textFieldISBN.getText(),
                            this.spinnerQuantity.getValue(),
                            this.spinnerPrize.getValue()
                    )
            );
            this.updateListView();
        } else {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(this.getWindow());
            alert.setTitle("Chybová hláška");
            alert.setHeaderText(null);
            alert.setContentText("Zadané hodnoty nejsou zadány správně:\n"
                    + errors);
            alert.show();
        }
    }

    /**
     * Metoda je volána událostí {@link ActionEvent}.
     * Nejprve je deklarována lokální konstanta <code>book</code> s definovanou
     * hodnotou výbranné hodnoty prvku {@link #listView}. Následně je provedena
     * podmínka, která kontroluje nenulovou hodnotu předchozí konstanty. 
     * V případě <code>true</code> je vytvořen a zobrazen dialog typu
     * {@link Alert} vyžadující potvrzení odebrání knihy. Pokud uživatel stiskne
     * tlačítko dialogu {@link ButtonType#OK}, provede se odstranění z kolekce
     * {@link #books} metodou {@link ObservableList#remove(java.lang.Object) }.
     * 
     * @param event vlastnosti obsluhované události
     */
    @FXML
    private void removeBook(ActionEvent event) {
        final Book book = listView.getSelectionModel().getSelectedItem();
        if (Objects.nonNull(book)) {
            final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(this.getWindow());
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Opravdu chcete odebrat knihu z databáze?\n"
                    + book);

            final Optional<ButtonType> optional = alert.showAndWait();
            
            if (optional.get().equals(ButtonType.OK))
                this.books.remove(book);
        }
    }

    /**
     * Metoda je obsloužena událostí {@link ActionEvent}.
     * Nejpre se uživateli zobrazí dialog s textovým polem {@link TextInputDialog},
     * pomocí kterého zadá jméno nového autora. Dále je deklarována lokální
     * konstanta <code>authorName</code>, do níž se nastaví hodnota textového
     * pole zadaného v dialogu {@link TextInputDialog}. Pokud autorovo jméno
     * je již v kolekci {@link #authors}, zobrazí se oznamující dialog
     * typu {@link Alert} se zprávou o jeho existenci. V případě prázdné hodnoty
     * se vystaví chybový dialog typu {@link Alert}, jinak bude autor
     * přidán mezi ostatní do kolekce {@link #authors}.
     *
     * @param event vlastnosti obsluhované události
     */
    @FXML
    private void addAuthor(ActionEvent event) {
        final TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.initOwner(this.getWindow());
        textInputDialog.setTitle("Přídání autora do databáze");
        textInputDialog.setHeaderText(null);
        textInputDialog.setContentText("Zadajte celé jméno autora: ");
        
        final Optional<String> optional = textInputDialog.showAndWait();
        if (optional.isPresent()) {
            final String authorName = optional.get();
            if (this.authors.contains(authorName)) {
                final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initOwner(this.getWindow());
                alert.setTitle("Informační zpráva");
                alert.setHeaderText(null);
                alert.setContentText("Zadaný autor je již v databázi.");
                alert.showAndWait();
            } else if (authorName.isEmpty()) {
                final Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(this.getWindow());
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(
                        "Autora jste vůbec nezadali. Zkusíte to znovu?"
                );
                alert.showAndWait();
            } else
                this.authors.add(authorName);
        }
    }

    /**
     * Metoda je volána událostí {@link ActionEvent}.
     * Nejprve je zobrazen dialog výběru {@link ChoiceDialog}, ve kterém
     * uživatel vybere jméno autora určeného k odebrání. Pokud stiskne tlačítko
     * dialogu {@link ButtonType#OK}, bude autorovo jméno smazáno metodou
     * {@link ObservableList#remove(Object) } z kolekce {@link #authors}.
     *
     * @param event vlastnosti obsluhované události
     */
    @FXML
    private void removeAuthor(ActionEvent event) {
        final ChoiceDialog choiceDialog = new ChoiceDialog(
                "vyberte autora, kterého chcete odstranit", this.authors
        );
        choiceDialog.initOwner(this.getWindow());
        choiceDialog.setHeaderText(null);
        choiceDialog.initOwner(this.getWindow());
        final Optional optional = choiceDialog.showAndWait();
        if (optional.isPresent())
            this.authors.remove((String) optional.get());
    }

    /**
     * Metoda aktualizuje výpis prvku {@link #listView}.
     * Nejprve jsou vymázána data z datove struktury {@link #listViewBookItems}.
     * Následně je za pomocí metody {@link ObservableList#stream() }
     * se vytvoří datovod. Poté metoda {@link java.util.stream.Stream#filter(Predicate) }
     * s parametrem {@link #filter} vyfiltruje požadované knihy.
     * Ty jsou následně metodou {@link java.util.stream.Stream#forEach(Consumer) }
     * s parametrem lambda výrazu, který zajistí přidání knih do kolekce
     * {@link #listViewBookItems}.
     */
    private void updateListView() {
        this.listViewBookItems.clear();
        this.books.stream().filter(this.filter).forEach(
                book -> this.listViewBookItems.add(book)
        );
    }
    
    /**
     * Metoda získá instanci okna. Pokud hodnota nabývá {@code null}, pak nemusí
     * být přiřazena scéna nebo scéna nemá přiřazené jeviště.
     * 
     * @return Window
     */
    private Window getWindow() {
        final Scene scene = this.listView.getScene();
        return Objects.isNull(scene) ? null : scene.getWindow();
    }

}