package de.alive.regexbuilder;

import de.alive.regexbuilder.utils.RegexAlphabet;
import de.alive.regexbuilder.utils.RegexOperationString;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        RegexAlphabet alphabet = new RegexAlphabet(true);
        alphabet.addCharacter('a', 'b');
        RegexOperationString operationString = RegexOperationString.create()
                .setBracketMode(RegexOperationString.BracketMode.NECESSARY)
                .build();

        RegexEnumerator enumerator = new RegexEnumerator(alphabet, operationString);

        List<String> result = enumerator.enumerate(40, RegexEnumerator.SortOrder.SMALL_FIRST);

        result.forEach(System.out::println);
    }
}
