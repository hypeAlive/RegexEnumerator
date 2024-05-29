package main.java.de.alive.regexbuilder.utils;

import java.util.HashSet;
import java.util.Set;

public class RegexAlphabet {

    public static final char EPSILON = '\u03B5';
    public static final char EMPTY = '\u2205';

    private Set<Character> alphabet;
    private boolean includeSpecialCharacters = false;

    public RegexAlphabet(boolean includeSpecialCharacters) {
        this.includeSpecialCharacters = includeSpecialCharacters;
        this.alphabet = new HashSet<>();
        if (includeSpecialCharacters) {
            alphabet.add(EPSILON);
            alphabet.add(EMPTY);
        }
    }

    public void addCharacter(char c) {
        alphabet.add(c);
    }

    public void removeCharacter(char c) {
        if (c == EPSILON || c == EMPTY) {
            if (!includeSpecialCharacters) {
                alphabet.remove(c);
            }
        } else {
            alphabet.remove(c);
        }
    }

    public boolean containsCharacter(char c) {
        return alphabet.contains(c);
    }

    public Set<Character> getAlphabet() {
        return new HashSet<>(alphabet);
    }

}
