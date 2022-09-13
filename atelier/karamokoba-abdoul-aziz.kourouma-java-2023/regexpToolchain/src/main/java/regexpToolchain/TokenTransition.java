package regexpToolchain;

import java.util.HashSet;
import java.util.Set;

public class TokenTransition extends Transition {

    private Set<Character> acceptingCharacters;

    public TokenTransition(State src, State dst) {
        super(src, dst);
    }

    public TokenTransition(Character character, State src, State dst) {
        this(src, dst);
        this.acceptingCharacters = new HashSet<>();
        this.acceptingCharacters.add(character);
    }

    public TokenTransition(Set<Character> acceptingCharacters, State src, State dst) {
        this(src, dst);
        this.acceptingCharacters = acceptingCharacters;
    }

    @Override
    public boolean accept(Character character) {
        return acceptingCharacters.contains(character);
    }

    @Override
    public String label() {
        StringBuilder sb = new StringBuilder();
        acceptingCharacters.stream().forEach(sb::append);
        return sb.toString();
    }
}
