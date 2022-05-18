package booksregister1;

import java.util.Objects;

/**
 * Třída <code>Book</code> slouží k uchování informací o danné knize.
 * 
 * @author Tomáš Marný
 */

public final class Book {

    /**
     * Deklarace proměnných
     */
    private String name;
    private String author;
    private EnumGenre genre;
    private String isbn;
    private int quantity;
    private int prize;

    /**
     * Konstruktor třídy <code>Book</code>.
     * 
     * @param name název knihy
     * @param author jméno autora
     * @param genre žánr knihy
     * @param isbn ISBN knihy
     * @param quantity počet kusů
     * @param prize cena knihy
     */
    public Book(
            String name, String author, EnumGenre genre, String isbn,
            int quantity, int prize
    ) {
        this.setName(name);
        this.setAuthor(author);
        this.setGenre(genre);
        this.setIsbn(isbn);
        this.setQuantity(quantity);
        this.setPrize(prize);
    }

    /**
     * Metoda navrací textový řetězec atributu třídy {@link #name}.
     * 
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Metoda nastaví atribut třídy {@link #name} na novou hodnotu parametru
     * <code>name</code>.
     * 
     * @param name nová hodnota názvu
     */
    public void setName(String name) {
        Objects.requireNonNull(name, "name is null");
        if(name.isEmpty())
            throw new IllegalArgumentException("name is empty");
        this.name = name;
    }

    /**
     * Metoda navrací textový řetězec atributu třídy {@link #author}.
     * 
     * @return String
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * Metoda nastaví atribut třídy {@link #author} na hodnotu parametru
     * <code>author</code>.
     * 
     * @param author nová hodnota autora
     */
    public void setAuthor(String author) {
        Objects.requireNonNull(author, "author is null");
        if(author.isEmpty())
            throw new IllegalArgumentException("author is empty");
        this.author = author;
    }

    /**
     * Methoda navrací výčtovou hodnotu atributu třídy {@link #genre}.
     * 
     * @return EnumGenre
     */
    public EnumGenre getGenre() {
        return this.genre;
    }

    /**
     * Metoda nastaví atribut třídy {@link #genre} na hodnotu atributu
     * <code>grenre</code>.
     * 
     * @param genre nová hodnota žánru
     */
    public void setGenre(EnumGenre genre) {
        Objects.requireNonNull(genre, "genre is null");
        this.genre = genre;
    }

    /**
     * Metoda navrací textový řetězec atributu třídy {@link #isbn}.
     * 
     * @return {@link String}
     */
    public String getIsbn() {
        return this.isbn;
    }

    /**
     * Metoda nastaví atribut třídy {@link #isbn} na hodnotu atributu
     * <code>isbn</code>.
     * 
     * @param isbn nová hodnota ISBN
     */
    public void setIsbn(String isbn) {
        Objects.requireNonNull(isbn, "ISBN is null");
        if(isbn.isEmpty())
            throw new IllegalArgumentException("ISBN is empty");
        this.isbn = isbn;
    }

    /**
     * Metoda navrací číselnou hodnotu atributu třídy {@link #quantity}.
     * 
     * @return int
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Metoda nastaví atribut třídy {@link #quantity} na hodnotu parametru
     * <code>quantity</code>.
     * 
     * @param quantity nová hodnota počtu knih
     */
    public void setQuantity(int quantity) {
        if(this.quantity < 0)
            throw new IllegalArgumentException("quantity cannot be negative");
        this.quantity = quantity;
    }

    /**
     * Metoda navrací číselnou hodnotu atributu třídy {@link #prize}.
     * 
     * @return int
     */
    public int getPrize() {
        return this.prize;
    }

    /**
     * Metoda nastaví atribut třídy {@link #prize} na hodnotu parametru
     * <code>prize</code>.
     * 
     * @param prize nová hodnota ceny knihy
     */
    public void setPrize(int prize) {
        if(prize < 0)
            throw new IllegalArgumentException("prize cannot be negative");
        this.prize = prize;
    }

    /**
     * Metoda provádí převod této instance na textový řetězec. Toho se dá využít
     * při mnoha příležitostí. Například při procesu ladění/debugování.
     *
     * @return String
     */
    @Override
    public String toString() {
        return this.name + ", " + this.isbn + ", " + this.author + ", "
                + this.genre + ", " + this.prize + ", " + this.quantity;
    }

    /**
     * Metoda generuje tzv. "hash code", který slouží k identifikaci
     * jedinečnosti dané instance.
     * 
     * @return int
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.name);
        hash = 71 * hash + Objects.hashCode(this.author);
        hash = 71 * hash + Objects.hashCode(this.genre);
        hash = 71 * hash + Objects.hashCode(this.isbn);
        hash = 71 * hash + this.quantity;
        hash = 71 * hash + this.prize;
        return hash;
    }

    /**
     * Metoda porovnává tuto instanci této třídy s jinou. Výsledkem je zjištění,
     * zda jsi jsou instance shodné.
     * 
     * @param obj porovnávaný objekt
     * 
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Book other = (Book) obj;
        if (this.quantity != other.quantity)
            return false;
        if (this.prize != other.prize)
            return false;
        if (!Objects.equals(this.name, other.name))
            return false;
        if (!Objects.equals(this.author, other.author))
            return false;
        if (!Objects.equals(this.isbn, other.isbn))
            return false;
        return this.genre == other.genre;
    }

}