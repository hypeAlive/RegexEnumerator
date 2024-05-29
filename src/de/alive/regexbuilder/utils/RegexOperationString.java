package de.alive.regexbuilder.utils;

import java.util.regex.Pattern;

public class RegexOperationString {

    public enum BracketMode {
        NONE,
        NECESSARY,
        ALL
    }

    private static final char CONCAT = '·';
    private static final char ALTERNATION = '+';
    private static final char KLEENE = '*';

    private static final Pattern bracketRemover = Pattern.compile("\\\\(([^()]+)\\\\)");

    private char concatSymbol;
    private char alternationSymbol;
    private char kleeneSymbol;
    private BracketMode bracketMode;

    private RegexOperationString(char concatSymbol, char alternationSymbol, char kleeneSymbol, BracketMode bracketMode) {
        this.concatSymbol = concatSymbol;
        this.alternationSymbol = alternationSymbol;
        this.kleeneSymbol = kleeneSymbol;
        this.bracketMode = bracketMode;
    }

    public String concat(String a, String b) {
        String result = a + concatSymbol + b;
        return bracketMode == BracketMode.ALL ? "(" + result + ")" : result;
    }

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

    public String kleene(String a) {
        String result = bracketMode != BracketMode.NECESSARY ? "(" + a + ")" + kleeneSymbol : a + kleeneSymbol;
        return bracketMode == BracketMode.ALL ? "(" + result + ")" : result;
    }

    public String removeUnnecessaryBrackets(String str) {
        return bracketRemover.matcher(str).replaceAll("$1");
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {

        private char concatSymbol = RegexOperationString.CONCAT;
        private char alternationSymbol = RegexOperationString.ALTERNATION;
        private char kleeneSymbol = RegexOperationString.KLEENE;
        private BracketMode bracketMode = BracketMode.NONE;

        public Builder setConcatSymbol(char concatSymbol) {
            this.concatSymbol = concatSymbol;
            return this;
        }

        public Builder setAlternationSymbol(char alternationSymbol) {
            this.alternationSymbol = alternationSymbol;
            return this;
        }

        public Builder setKleeneSymbol(char kleeneSymbol) {
            this.kleeneSymbol = kleeneSymbol;
            return this;
        }

        public Builder setBracketMode(BracketMode bracketMode) {
            this.bracketMode = bracketMode;
            return this;
        }

        public RegexOperationString build() {
            return new RegexOperationString(concatSymbol, alternationSymbol, kleeneSymbol, bracketMode);
        }
    }
}
