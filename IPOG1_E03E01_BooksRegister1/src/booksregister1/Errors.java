package booksregister1;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Třída spravuje seznam chybových hlášek.
 * 
 * @author Tomáš Marný
 */
public final class Errors {
    
    private final Map<String, String> mapErrors;

    public Errors() {
        this.mapErrors = new HashMap<>();
    }
    
    /**
     * Metoda vloží název chyby a jeho příslušnou zprávu. Dojde k tomu pouze,
     * když není ani jeden z prarametrů nenabývá hodnoty {@code null}
     * a oříznuté hodnoty obou parametrů nejsou prázdné.
     * 
     * @param name název
     * @param message chybová zpráva
     */
    public void putError(String name, String message) {
        Objects.requireNonNull(name, "name cannot be null");
        Objects.requireNonNull(message, "message cannot be null");
        final String trimmedName = name.trim();
        final String trimmedMessage = message.trim();
        if(trimmedName.isEmpty())
            throw new IllegalArgumentException("name cannot be empty");
        if(trimmedMessage.isEmpty())
            throw new IllegalArgumentException("message cannot be empty");
        this.mapErrors.put(trimmedName, trimmedMessage);
    }
    
    /**
     * Metoda zjistí, zda je název obsažen.
     * 
     * @param name název
     * 
     * @return boolean
     */
    public boolean containsName(String name) {
        Objects.requireNonNull(name, "name cannot be null");
        final String trimmedName = name.trim();
        if(trimmedName.isEmpty())
            throw new IllegalArgumentException("name cannot be empty");
        return this.mapErrors.containsKey(trimmedName);
    }
    
    /**
     * Metoda navrátí chybovou zprávu podle zadaného názvu.
     * 
     * @param name název
     * 
     * @return String
     */
    public String getMessage(String name) {
        Objects.requireNonNull(name, "name cannot be null");
        final String trimmedName = name.trim();
        if(trimmedName.isEmpty())
            throw new IllegalArgumentException("name cannot be empty");
        return this.mapErrors.get(trimmedName);
    }

    /**
     * Test prázdnosti.
     * 
     * @return boolean
     */
    public boolean isEmpty() {
        return this.mapErrors.isEmpty();
    }

    /**
     * Metoda provádí převod této instance na textový řetězec. Toho se dá využít
     * při mnoha příležitostí. Například při procesu ladění/debugování.
     *
     * @return String
     */
    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        this.mapErrors.forEach((String name, String message) ->
                stringBuilder
                        .append(stringBuilder.length() == 0 ? "" : "\n")
                        .append("\t")
                        .append(name)
                        .append(": ")
                        .append(message)
        );
        return stringBuilder.toString();
    }
    
}