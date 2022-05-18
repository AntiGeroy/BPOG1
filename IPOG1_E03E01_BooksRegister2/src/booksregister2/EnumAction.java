package booksregister2;

/**
 * Výčet jednotlivých akcí.
 * 
 * @author Tomáš Marný
 */

public enum EnumAction {
    
    FILTER("Filtrovat podle"),
    SEARCH("Vyhledat"),
    NONE("Zrušit");
    
    private final String value;

    private EnumAction(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
    
}