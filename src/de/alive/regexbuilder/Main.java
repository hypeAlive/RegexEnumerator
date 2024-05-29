package de.alive.regexbuilder;

import de.alive.regexbuilder.utils.RegexAlphabet;
import de.alive.regexbuilder.utils.RegexOperationManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Erstelle ein Alphabet mit Empty und epsilon bereits drin
        RegexAlphabet alphabet = new RegexAlphabet(true);
        alphabet.addCharacter('a', 'b'); // füge weitere zeichen zum alphabet hinzu

        //Erstelle ein Objekt, um die RegexStrings zu generieren - mit Anzeige von nur nötigen Klammern
        RegexOperationManager operationString = RegexOperationManager.create()
                .setBracketMode(RegexOperationManager.BracketMode.NECESSARY)
                .build();

        //Enumerator Objekt um damit die liste zu erzeugen
        RegexEnumerator enumerator = new RegexEnumerator(alphabet, operationString);

        //erstelle eine liste an regex mit einem limit
        List<String> result = enumerator.enumerate(40);

        //printe die liste
        result.forEach(System.out::println);
    }
}
