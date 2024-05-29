package main.java.de.alive.regexbuilder.utils;

public class RegexOperationString {

    public enum BracketMode {
        NONE,
        NECESSARY,
        ALL
    }

    private static final char CONCAT = '·';
    private static final char ALTERNATION = '+';
    private static final char KLEENE = '*';

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
        String result = a + alternationSymbol + b;
        return bracketMode != BracketMode.NONE ? "(" + result + ")" : result;
    }

    public String kleene(String a) {
        String result = bracketMode != BracketMode.NONE ? "(" + a + ")" + kleeneSymbol : a + kleeneSymbol;
        return bracketMode != BracketMode.ALL ? "(" + result + ")" : result;
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
