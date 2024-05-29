package de.alive.regexbuilder.utils;

import java.util.*;

/**
 * Diese Klasse repräsentiert ein Alphabet für reguläre Ausdrücke.
 * Es kann spezielle Zeichen wie EPSILON und EMPTY enthalten.
 */
public class RegexAlphabet {

    public static final char EPSILON = '\u03B5';
    public static final char EMPTY = '\u2205';

    private LinkedHashSet<Character> alphabet;
    private boolean includeSpecialCharacters = false;

    /**
     * Erstellt ein neues RegexAlphabet.
     *
     * @param includeSpecialCharacters Wenn true, werden die speziellen Zeichen EPSILON und EMPTY zum Alphabet hinzugefügt.
     */
    public RegexAlphabet(boolean includeSpecialCharacters) {
        this.includeSpecialCharacters = includeSpecialCharacters;
        this.alphabet = new LinkedHashSet<>();
        if (includeSpecialCharacters) {
            alphabet.add(EPSILON);
            alphabet.add(EMPTY);
        }
    }

    /**
     * Fügt die gegebenen Zeichen zum Alphabet hinzu.
     *
     * @param c Die hinzuzufügenden Zeichen.
     */
    public void addCharacter(char... c) {
        for(char ch : c) {
            alphabet.add(ch);
        }
    }

    /**
     * Entfernt das gegebene Zeichen aus dem Alphabet.
     *
     * @param c Das zu entfernende Zeichen.
     */
    public void removeCharacter(char c) {
        if (c == EPSILON || c == EMPTY) {
            if (!includeSpecialCharacters) {
                alphabet.remove(c);
            }
        } else {
            alphabet.remove(c);
        }
    }

    /**
     * Überprüft, ob das gegebene Zeichen im Alphabet enthalten ist.
     *
     * @param c Das zu überprüfende Zeichen.
     * @return true, wenn das Zeichen im Alphabet enthalten ist, sonst false.
     */
    public boolean containsCharacter(char c) {
        return alphabet.contains(c);
    }

    /**
     * Gibt eine Liste der Zeichen im Alphabet zurück.
     *
     * @return Eine Liste der Zeichen im Alphabet.
     */
    public List<Character> getAlphabet() {
        return new ArrayList<>(alphabet);
    }

}
