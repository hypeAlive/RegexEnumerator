package de.alive.regexbuilder.utils;

import java.util.regex.Pattern;

/**
 Diese Klasse repräsentiert eine Sammlung von Operationen für reguläre Ausdrücke.
 Sie unterstützt Konkatenation, Alternation und Kleene-Stern-Operationen.
 Sie dient dazu, diese regulären Ausdrücke als String darzustellen.
 */
public class RegexOperationManager {

    /**
     * Enum zur Definition des Klammermodus.
     * Mit dem Klammermodus kann definiert werden, welche Klammern im String gesetzt werden sollen.
     */
    public enum BracketMode {
        NONE,
        NECESSARY,
        ALL
    }

    private static final char CONCAT = '·';
    private static final char ALTERNATION = '+';
    private static final char KLEENE = '*';

    //Regex um Klammern zu entfernen
    private static final Pattern bracketRemover = Pattern.compile("\\\\(([^()]+)\\\\)");

    private final char concatSymbol;
    private final char alternationSymbol;
    private final char kleeneSymbol;
    private final BracketMode bracketMode;

    /**
     * Privater Konstruktor für RegexOperationManager.
     * Nicht zur Nutzung gedacht. Benutze RegexOperationManager.create() oder direkten Builder
     *
     * @param concatSymbol Das Symbol für die Konkatenation.
     * @param alternationSymbol Das Symbol für die Alternation.
     * @param kleeneSymbol Das Symbol für den Kleene-Stern.
     * @param bracketMode Der Modus für die Verwendung von Klammern.
     */
    private RegexOperationManager(char concatSymbol, char alternationSymbol, char kleeneSymbol, BracketMode bracketMode) {
        this.concatSymbol = concatSymbol;
        this.alternationSymbol = alternationSymbol;
        this.kleeneSymbol = kleeneSymbol;
        this.bracketMode = bracketMode;
    }

    /**
     * Führt eine Konkatenation von zwei Strings durch.
     *
     * @param a Der erste String.
     * @param b Der zweite String.
     * @return Das Ergebnis der Konkatenation.
     */
    public String concat(String a, String b) {
        String result = a + concatSymbol + b;
        return bracketMode == BracketMode.ALL ? "(" + result + ")" : result;
    }

    /**
     * Führt eine Alternation von zwei Strings durch.
     *
     * @param a Der erste String.
     * @param b Der zweite String.
     * @return Das Ergebnis der Alternation.
     */
    public String alternation(String a, String b) {

        if(a.length() > 2)
            a = "(" + a + ")";

        if(b.length() > 2)
            b = "(" + b + ")";

        String result = a + alternationSymbol + b;
        result = bracketMode == BracketMode.ALL ? "(" + result + ")" : result;

        if(bracketMode == BracketMode.NECESSARY)
            return this.removeUnnecessaryBrackets(result);

        return result;
    }

    /**
     * Wendet die Kleene-Stern-Operation auf einen String an.
     *
     * @param a Der String.
     * @return Das Ergebnis der Kleene-Stern-Operation.
     */
    public String kleene(String a) {
        String result = bracketMode != BracketMode.NECESSARY ? "(" + a + ")" + kleeneSymbol : a + kleeneSymbol;
        return bracketMode == BracketMode.ALL ? "(" + result + ")" : result;
    }

    /**
     * Entfernt unnötige Klammern aus einem String.
     *
     * @param str Der String.
     * @return Der String ohne unnötige Klammern.
     */
    public String removeUnnecessaryBrackets(String str) {
        return bracketRemover.matcher(str).replaceAll("$1");
    }

    /**
     * Erstellt einen neuen Builder für RegexOperationManager.
     *
     * @return Ein neuer RegexOperationManager-Builder.
     */
    public static Builder create() {
        return new Builder();
    }

    /**
     * Builder-Klasse für RegexOperationManager.
     * Mit diesem Builder kann gesteuert werden wie der RegexOperationManager aussehen soll.
     */
    public static class Builder {

        private char concatSymbol = RegexOperationManager.CONCAT;
        private char alternationSymbol = RegexOperationManager.ALTERNATION;
        private char kleeneSymbol = RegexOperationManager.KLEENE;
        private BracketMode bracketMode = BracketMode.NONE;

        /**
         * Setzt das Symbol für die Konkatenation.
         *
         * @param concatSymbol Das Symbol für die Konkatenation.
         * @return Dieser Builder.
         */
        public Builder setConcatSymbol(char concatSymbol) {
            this.concatSymbol = concatSymbol;
            return this;
        }

        /**
         * Setzt das Symbol für die Alternation.
         *
         * @param alternationSymbol Das Symbol für die Alternation.
         * @return Dieser Builder.
         */
        public Builder setAlternationSymbol(char alternationSymbol) {
            this.alternationSymbol = alternationSymbol;
            return this;
        }

        /**
         * Setzt das Symbol für den Kleene-Stern.
         *
         * @param kleeneSymbol Das Symbol für den Kleene-Stern.
         * @return Dieser Builder.
         */
        public Builder setKleeneSymbol(char kleeneSymbol) {
            this.kleeneSymbol = kleeneSymbol;
            return this;
        }

        /**
         * Setzt den Modus für die Verwendung von Klammern.
         *
         * @param bracketMode Der Modus für die Verwendung von Klammern.
         * @return Dieser Builder.
         */
        public Builder setBracketMode(BracketMode bracketMode) {
            this.bracketMode = bracketMode;
            return this;
        }

        /**
         * Erstellt ein neues RegexOperationManager-Objekt aus dem Builder.
         *
         * @return Ein neues RegexOperationManager-Objekt.
         */
        public RegexOperationManager build() {
            return new RegexOperationManager(concatSymbol, alternationSymbol, kleeneSymbol, bracketMode);
        }
    }
}
