package booksregister2;

/**
 * Výčet jednotlivých typů filtrů.
 * 
 * @author Tomáš Marný
 */

public enum EnumFilterBy {
    
    AUTHOR("autor"),
    GENRE("žánr"),
    NONE("žádný");
    
    private final String value;

    private EnumFilterBy(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
    
}