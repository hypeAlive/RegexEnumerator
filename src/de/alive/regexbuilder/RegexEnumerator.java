package de.alive.regexbuilder;

import de.alive.regexbuilder.utils.RegexAlphabet;
import de.alive.regexbuilder.utils.RegexOperationString;

import java.util.*;

/**
 * Diese Klasse dient zur Erzeugung von regulären Ausdrücken.
 * Sie verwendet ein Alphabet und eine Sammlung von Operationen, um eine Liste von regulären Ausdrücken zu erzeugen.
 */
public class RegexEnumerator {

    private final RegexAlphabet alphabet;
    private final RegexOperationString operationString;

    /**
     * Erstellt einen neuen RegexEnumerator.
     *
     * @param alphabet Das Alphabet, das für die Erzeugung der regulären Ausdrücke verwendet wird.
     * @param operationString Die Sammlung von Operationen, die für die Erzeugung der regulären Ausdrücke verwendet wird.
     */
    public RegexEnumerator(RegexAlphabet alphabet, RegexOperationString operationString) {
        this.alphabet = alphabet;
        this.operationString = operationString;
    }

    /**
     * Erzeugt eine Liste von regulären Ausdrücken.
     * Sie sind aufsteigend sortiert.
     *
     * @param limit Die maximale Anzahl von regulären Ausdrücken, die erzeugt werden sollen.
     * @return Eine Liste von regulären Ausdrücken.
     */
    public List<String> enumerate(int limit) {
        List<Character> alphabetList = alphabet.getAlphabet();
        Set<String> regexes = new LinkedHashSet<>();

        // Füge Basisoperationen hinzu. Diese sind Epsilon und Leerzeichen.
        regexes.add(String.valueOf(RegexAlphabet.EPSILON));
        regexes.add(String.valueOf(RegexAlphabet.EMPTY));

        // Füge alle Zeichen aus dem Alphabet hinzu.
        for (char c : alphabetList) {
            regexes.add(String.valueOf(c));
        }

        // Erstelle alle Kombinationen von regulären Ausdrücken, bis das Limit erreicht ist.
        while (regexes.size() < limit) {
            Set<String> newRegexes = new LinkedHashSet<>(regexes);

            // Für jeden regulären Ausdruck in der aktuellen Menge...
            for (String expr1 : regexes) {
                // ...füge Kombinationen mit allen anderen regulären Ausdrücken hinzu.
                if (addCombinations(newRegexes, expr1, regexes, limit)) {
                    // Wenn das Limit erreicht ist, breche die Schleife ab.
                    break;
                }
            }

            // Wenn keine neuen Kombinationen hinzugefügt wurden, breche die Schleife ab.
            if (newRegexes.size() == regexes.size()) {
                break;
            }

            // Setze die Menge der regulären Ausdrücke auf die neu erstellten Kombinationen.
            regexes = newRegexes;
        }

        // Gib die Liste der erstellten regulären Ausdrücke zurück.
        return new ArrayList<>(regexes);
    }

    /**
     * Fügt Kombinationen von regulären Ausdrücken zu einem Set hinzu.
     *
     * @param newRegexes Das Set, zu dem die Kombinationen hinzugefügt werden sollen.
     * @param expr1 Der erste reguläre Ausdruck für die Kombinationen.
     * @param regexes Das Set von regulären Ausdrücken, die für die Kombinationen verwendet werden sollen.
     * @param limit Die maximale Anzahl von Kombinationen, die hinzugefügt werden sollen.
     * @return true, wenn die maximale Anzahl von Kombinationen erreicht wurde, sonst false.
     */
    private boolean addCombinations(Set<String> newRegexes, String expr1, Set<String> regexes, int limit) {
        // Durchlaufe alle regulären Ausdrücke
        for (String expr2 : regexes) {
            // Füge die Konkatenation von expr1 und expr2 zu den neuen regulären Ausdrücken hinzu
            newRegexes.add(this.operationString.concat(expr1, expr2));
            // Wenn die Größe von newRegexes das Limit erreicht, beende die Methode und gebe true zurück
            if (newRegexes.size() >= limit) {
                return true;
            }

            // Füge die Alternative von expr1 und expr2 zu den neuen regulären Ausdrücken hinzu
            newRegexes.add(this.operationString.alternation(expr1, expr2));
            // Wenn die Größe von newRegexes das Limit erreicht, beende die Methode und gebe true zurück
            if (newRegexes.size() >= limit) {
                return true;
            }

            // Füge den Kleene-Stern von expr1 zu den neuen regulären Ausdrücken hinzu
            newRegexes.add(this.operationString.kleene(expr1));
            // Wenn die Größe von newRegexes das Limit erreicht, beende die Methode und gebe true zurück
            if (newRegexes.size() >= limit) {
                return true;
            }
        }
        // Wenn das Limit nicht erreicht wurde, gebe false zurück
        return false;
    }

}