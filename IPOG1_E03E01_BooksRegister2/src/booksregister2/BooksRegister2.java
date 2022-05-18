package booksregister2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * <h1>Cvičení 3 - Příklad 1: Evidence knih</h1>
 * 
 * <h2>Cíl</h2>
 * <ul>
 *  <li> Vytvořit jednoduchou aplikaci pro evidenci knih v knihovně</li>
 *  <li> Možnost přidávat a odebírat autory</li>
 *  <li> Možnost přidávat a odebírat knihy</li>
 *  <li> Pracovat se základními formuláři</li>
 *  <li> Pochopit jaké výstupy má zkompilovaný projekt – webový i standardní</li>
 *  <li> Naučit se princip spřažení datových struktur a ovladačů</li>
 * </ul>
 * 
 * <h2>Tvorba vlastní třídy</h2>
 * <p>Vytvořte vlastní třídu, která bude uchjovávat informace o knize. Metodu
 * toString() zpracujte na rozdíl od příkladu tak, aby obsahovala všechny
 * údaje o knize (např. jako na obrázku 1).</p>
 * 
 * <h2>Samostatná práce</h2>
 * <p>Nastudujte dialogy ze stránky http://code.makery.ch/blog/javafx-dialogs-official/
 * Proveďte obsluhu tlačítek pro odstranění autora (využijte dialog
 * Choice Dialog) a přidání autora (využijte dialog typu Text Input Dialog).</p>
 * 
 * <h2>Zobrazení aplikace</h2>
 * <p>Vaše aplikace je ve složce připravena jako standardní program i jako
 * webová stránka. Vyzkoušejte spuštění vaší aplikace jako webové stránky.</p>
 * 
 * <p>
 * Projekt sdíleného řešení bylo vytvořeno pomocí vývojového prostředí
 * <b>Netbeans 8.2</b> a <b>Java 8</b>.
 * </p>
 * 
 * @author Tomáš Marný
 */

public class BooksRegister2 extends Application {
    
    /**
     * Metoda provede nahrání, vytvoření a zobrazení grafického uživatelského
     * rozhranní (GUI).
     * 
     * @param stage jeviště spouštěné aplikace
     * 
     * @throws Exception vystaví se v případě potíží při spouštění aplikace
     */
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metoda slouží ke spuštění aplikace jazyka Java.
     * 
     * @param args argumenty příkazové řádky
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}