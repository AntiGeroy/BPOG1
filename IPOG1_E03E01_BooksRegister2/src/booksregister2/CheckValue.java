package booksregister2;

import java.util.Objects;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Třída slouží ke kontrole zadaných hodnot vybraných ovládacích prvků. Jsou to
 * textové pole {@link TextField}, pole rozbalovacího seznamu {@link ComboBox}
 * a číselník {@link Spinner}. Návrhový vzor této třídy je knihovna.
 * 
 * @author Tomáš Marný
 */
public final class CheckValue {
    
    private CheckValue() {
        
    }

    /**
     * Metoda kontroluje prázdnost zadané textové hodnoty zadávacího pole
     * {@link TextField}. Nejprve je deklarována lokální proměnná <code>value</code>
     * definovanou na hodnotu {@link TextField#getText() } parametru
     * <code>textField</code>. Následně proběhne kontrola zadané hodnoty.
     * Pokud je prázdná, dojde k vložení chybové hodnoty do správce chyb.
     * Nakonec je volána metoda {@link #changeStyleClass(javafx.scene.control.Control, javafx.scene.image.ImageView, java.lang.String, booksregister.Errors) }.
     * 
     * @param textField zadavací textové pole
     * @param imageView status hodnoty
     * @param name název zadávané hodnoty
     * @param errors správce chybových hlášek
     */
    public static void checkTextFieldString(
            TextField textField, ImageView imageView, String name, Errors errors
    ) {
        final String value = textField.getText();
        if (value.isEmpty())
            errors.putError(name, "hodnota není zadána");
        changeStyleClass(textField, imageView, name, errors);
    }

    /**
     * Metoda kontroluje zadanou textovou hodnotu zadávacího pole
     * {@link TextField} je celým číslem. Nejprve se provede kontrola
     * prázdnosti volanou metodou {@link #checkTextFieldString(javafx.scene.control.TextField, javafx.scene.image.ImageView, java.lang.String, booksregister.Errors) }.
     * Následně je provedena metoda pro provedení podmínky. Jestli je název
     * hodnoty již ve správci obsažen, dojde k ukočení této metody. Jinak
     * bude v ní pokračováno. Vytvoří se lokální proměnná <code>value</code>,
     * která je definována hodnotou {@link TextField#getText() } parametru
     * {@code textField}. Následuje chráněný blok, ve kterém se provede převod
     * textového řetězce na číslo. V případě vystavení výjimky dojde k vložení
     * chyby do správce. Nakonec je volána metoda {@link #changeStyleClass(javafx.scene.control.Control, javafx.scene.image.ImageView, java.lang.String, booksregister.Errors) }.
     * 
     * @param textField zadavací textové pole
     * @param imageView status hodnoty
     * @param name název zadávané hodnoty
     * @param errors správce chybových hlášek
     */
    public static void checkTextFieldInteger(
            TextField textField, ImageView imageView, String name, Errors errors
    ) {
        checkTextFieldString(textField, imageView, name, errors);
        if(errors.containsName(name))
            return;
        final String value = textField.getText();
        try {
            int integer = Integer.valueOf(value);
        } catch (NumberFormatException e) {
            errors.putError(name, "zadaná hodnota není celým číslem");
        }
        changeStyleClass(textField, imageView, name, errors);
    }

    /**
     * Metoda kontroluje zadanou textovou hodnotu zadávacího pole
     * {@link TextField} je celým číslem, které musí být v intervalu
     * &lt;<code>minimum</code>, <code>maximum</code>&gt; včetně.
     * Nejprve se provede kontrola
     * prázdnosti volanou metodou {@link #checkTextFieldString(javafx.scene.control.TextField, javafx.scene.image.ImageView, java.lang.String, booksregister.Errors) }.
     * Následně je provedena metoda pro provedení podmínky. Jestli je název
     * hodnoty již ve správci obsažen, dojde k ukočení této metody. Jinak
     * bude v ní pokračováno. Vytvoří se lokální proměnná <code>value</code>,
     * která je definována hodnotou {@link TextField#getText() } parametru
     * {@code textField}. Následuje chráněný blok, ve kterém se provede převod
     * textového řetězce na číslo. Převedená hodnota je nastavena proměnné
     * <code>integer</code>. U tohoto čísla je provedena podmínka, zda je číslo
     * v zadaném intervalu. Pokud do něj nepatří nebo není celým číslem,
     * provede se přidání chybové zprávy do správce chyb. Nakonec je volána
     * metoda {@link #changeStyleClass(javafx.scene.control.Control, javafx.scene.image.ImageView, java.lang.String, booksregister.Errors) }.
     * 
     * @param minimum minimální číselná hodnota
     * @param maximum maximální číselná hodnota
     * @param textField zadavací textové pole
     * @param imageView status hodnoty
     * @param name název zadané hodnoty
     * @param errors správce chybových hlášek
     */
    public static void checkTextFieldInteger(
            int minimum, int maximum, TextField textField, ImageView imageView,
            String name, Errors errors
    ) {
        checkTextFieldString(textField, imageView, name, errors);
        if(errors.containsName(name))
            return;
        final String value = textField.getText();
        try {
            int integer = Integer.valueOf(value);
            if (integer < minimum || integer > maximum)
                errors.putError(
                        name,
                        value + " není v  intervalu <" + minimum + "; "
                                + maximum + ">"
                );
        } catch (NumberFormatException e) {
            errors.putError(name, "zadaná hodnota není číslem");
        }
        changeStyleClass(textField, imageView, name, errors);
    }

    /**
     * Metoda kontroluje vybránou hodnotu pole rozbalovacího seznamu
     * {@link ComboBox}. Pokud žádná nebyla vybrána, tak se do proměnné
     * vloží chybová zpráva do správce chyb. Nakonec je volána
     * metoda {@link #changeStyleClass(javafx.scene.control.Control, javafx.scene.image.ImageView, java.lang.String, booksregister.Errors) }.
     *
     * @param comboBox pole rozbalovacího seznamu
     * @param imageView status hodnoty
     * @param name název vybrané hodnoty
     * @param errors správce chybových hlášek
     */
    public static void checkComboBox(
            ComboBox comboBox, ImageView imageView, String name, Errors errors
    ) {
        if (comboBox.getSelectionModel().isEmpty())
            errors.putError(name, "hodnota není vybrána");
        changeStyleClass(comboBox, imageView, name, errors);
    }
    
    /**
     * Metoda kontroluje zadanou hodnotu čísleníku {@link Spinner}. Pokud žádná
     * nebyla vybrána, tak se do proměnné vloží chybová zpráva do správce chyb.
     * Nakonec je volána metoda {@link #changeStyleClass(javafx.scene.control.Control, javafx.scene.image.ImageView, java.lang.String, booksregister.Errors) }.
     *
     * @param spinner číselník
     * @param imageView status hodnoty
     * @param name název vybrané hodnoty
     * @param errors správce chybových hlášek
     */
    public static void checkSpinner(
            Spinner spinner, ImageView imageView, String name, Errors errors
    ) {
        if (Objects.isNull(spinner.getValue()))
            errors.putError(name, "hodnota není zadána");
        changeStyleClass(spinner, imageView, name, errors);
    }

    /**
     * Metoda přidává nebo odebírá styl třídy a mění obrázek indikující validitu
     * hodnoty. Nejprve je zjištěno, zda je ve spráci chyb obsažen název hodnoty.
     * Ten je přiřazen lokální konstantě <code>contains</code>. Následně je
     * deklarována lokální konstanta <code>styleClass</code>, která je deinována
     * na hdonotu {@link Control#getStyleClass() } parametru <code>control</code>.
     * Pak je vytvořena konstanta <code>image</code> nastavená konstruktorem
     * {@link Image#Image(java.io.InputStream) }. K získání názvu obrázku
     * je použit ternální operátor. Pokavaď je proměnná <code>contains</code>
     * je pravdivá, nastaví se název obrázku na "wrong". V opačném případě bude
     * mít hodnotu "checked". Potom je na řadě rozhodovací blok, který učí,
     * za bude styl třídy "error" přidán nebo odebrán. Potom se vytvoří prvek
     * datového typu {@link Tooltip}, kterému se nastaví titlek pro prvek
     * parametru <code>control</code>. Na závěr je provedena kontrola zadání
     * statusu typu {@link ImageView}. Pokud je zadaná, provede se nastavení
     * obrázku metodou {@link ImageView#setImage(javafx.scene.image.Image) }
     * prvku paramteru <code>imageView</code> a k němu bude instalován popisek
     * metodou {@link Tooltip#install(javafx.scene.Node, javafx.scene.control.Tooltip) }.
     *
     * @param control ovládácí prvek, kterým byla zadána/vybrána chybná hodnota
     * @param imageView status hodnoty
     * @param name název hodnoty
     * @param errors správce chybových hlášek
     */
    private static void changeStyleClass(
            Control control, ImageView imageView, String name, Errors errors
    ) {
        final boolean contains = errors.containsName(name);
        final ObservableList<String> styleClass = control.getStyleClass();
        final Image image = new Image(CheckValue.class.getResourceAsStream(
                "/images/" + (contains ? "wrong" : "checked") + ".png")
        );
        if (!contains)
            styleClass.remove("error");
        else if(!styleClass.contains("error"))
            styleClass.add("error");
        final Tooltip toolTip = new Tooltip(
                contains ? errors.getMessage(name) : "OK"
        );
        control.setTooltip(toolTip);
        if (Objects.nonNull(imageView)) {
            imageView.setImage(image);
            Tooltip.install(imageView, toolTip);
        }
    }

}