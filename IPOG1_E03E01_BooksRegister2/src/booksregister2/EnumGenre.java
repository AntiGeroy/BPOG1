package booksregister2;

/**
 * Výčet jednotlivých žánrů.
 * 
 * @author Tomáš Marný
 */

public enum EnumGenre {
    
    FICTION("beletrie"),
    POETRY("poezie"),
    EDUCATIONAL_LITERATURE("naučná literatura"),
    FACTS_LITERATURE("literatura faktů"),
    SCIFI("sci-fi"),
    OTHER("jiný");
    
    private final String value;

    private EnumGenre(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
    
}