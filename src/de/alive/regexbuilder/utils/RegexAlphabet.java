package de.alive.regexbuilder.utils;

import java.util.*;

public class RegexAlphabet {

    public static final char EPSILON = '\u03B5';
    public static final char EMPTY = '\u2205';

    private LinkedHashSet<Character> alphabet;
    private boolean includeSpecialCharacters = false;

    public RegexAlphabet(boolean includeSpecialCharacters) {
        this.includeSpecialCharacters = includeSpecialCharacters;
        this.alphabet = new LinkedHashSet<>();
        if (includeSpecialCharacters) {
            alphabet.add(EPSILON);
            alphabet.add(EMPTY);
        }
    }

    public void addCharacter(char... c) {
        for(char ch : c) {
            alphabet.add(ch);
        }
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

    public List<Character> getAlphabet() {
        return new ArrayList<>(alphabet);
    }

}
