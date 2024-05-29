package de.alive.regexbuilder;

import de.alive.regexbuilder.utils.RegexAlphabet;
import de.alive.regexbuilder.utils.RegexOperationString;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class RegexEnumerator {

    private final RegexAlphabet alphabet;
    private final RegexOperationString operationString;

    public RegexEnumerator(RegexAlphabet alphabet, RegexOperationString operationString) {
        this.alphabet = alphabet;
        this.operationString = operationString;
    }

    public List<String> enumerate(int limit) {
        List<Character> alphabetList = alphabet.getAlphabet();
        Set<String> regexes = new LinkedHashSet<>();

        // Basisoperationen hinzufügen
        regexes.add(String.valueOf(RegexAlphabet.EPSILON));
        regexes.add(String.valueOf(RegexAlphabet.EMPTY));
        for (char c : alphabetList) {
            regexes.add(String.valueOf(c));
        }

        // Alle Kombinationen erstellen
        while (regexes.size() < limit) {
            Set<String> newRegexes = new LinkedHashSet<>(regexes);

            for (String expr1 : regexes) {
                if (addCombinations(newRegexes, expr1, regexes, limit)) {
                    break;
                }
            }

            if (newRegexes.size() == regexes.size()) {
                break; // Keine neuen Kombinationen hinzugefügt
            }

            regexes = newRegexes;
        }

        return new ArrayList<>(regexes);
    }

    private boolean addCombinations(Set<String> newRegexes, String expr1, Set<String> regexes, int limit) {
        for (String expr2 : regexes) {
            // Konkatenation
            newRegexes.add(this.operationString.concat(expr1, expr2));
            if (newRegexes.size() >= limit) {
                return true;
            }

            // Alternative
            newRegexes.add(this.operationString.alternation(expr1, expr2));
            if (newRegexes.size() >= limit) {
                return true;
            }

            // Kleene-Stern
            newRegexes.add(this.operationString.kleene(expr1));
            if (newRegexes.size() >= limit) {
                return true;
            }
        }
        return false;
    }

}