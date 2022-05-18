package booksregister1;

/**
 * Výčet jednotlivých způsobů vyhledávání.
 * 
 * @author Tomáš Marný
 */

public enum EnumSearch {
    NAME("Název"),
    WORDS_FROM_NAME("Slova z názvu");
    
    private final String value;

    private EnumSearch(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}