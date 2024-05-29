package main.java.de.alive.regexbuilder.utils;

public class OperationStringBuilder {

    public enum BracketMode {
        NONE,
        NECESSARY,
        ALL
    }

    private static final String CONCAT = "·";
    private static final String ALTERNATION = "+";
    private static final String KLEENE = "*";

    private String concatSymbol;
    private String alternationSymbol;
    private String kleeneSymbol;
    private BracketMode bracketMode;

    private OperationStringBuilder(String concatSymbol, String alternationSymbol, String kleeneSymbol, BracketMode bracketMode) {
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

    public static class Builder {

        private String concatSymbol = OperationStringBuilder.CONCAT;
        private String alternationSymbol = OperationStringBuilder.ALTERNATION;
        private String kleeneSymbol = OperationStringBuilder.KLEENE;
        private BracketMode bracketMode = BracketMode.NONE;

        public Builder setConcatSymbol(String concatSymbol) {
            this.concatSymbol = concatSymbol;
            return this;
        }

        public Builder setAlternationSymbol(String alternationSymbol) {
            this.alternationSymbol = alternationSymbol;
            return this;
        }

        public Builder setKleeneSymbol(String kleeneSymbol) {
            this.kleeneSymbol = kleeneSymbol;
            return this;
        }

        public Builder setBracketMode(BracketMode bracketMode) {
            this.bracketMode = bracketMode;
            return this;
        }

        public OperationStringBuilder build() {
            return new OperationStringBuilder(concatSymbol, alternationSymbol, kleeneSymbol, bracketMode);
        }
    }
}
