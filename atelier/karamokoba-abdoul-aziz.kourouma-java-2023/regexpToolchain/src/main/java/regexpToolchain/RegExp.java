package regexpToolchain;

/**
 * Describe a regular expression.
 * Either use `RegExp.fromString` or a combination of its
 * subclasses to build one.
 */
public abstract class RegExp {

    public abstract String print();

    public abstract Automaton thompson();

    /**
     * Return whether this regular expression recognizes the word.
     *
     * @param word
     * @return whether this matches word
     */
    public final boolean matches(String word) {
        return thompson().acceptsWord(word);
    }
}
