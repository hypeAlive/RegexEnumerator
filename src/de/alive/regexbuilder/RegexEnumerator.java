package de.alive.regexbuilder;

import de.alive.regexbuilder.utils.RegexAlphabet;
import de.alive.regexbuilder.utils.RegexOperationString;

import java.util.*;

public class RegexEnumerator {

    private final RegexAlphabet alphabet;
    private final RegexOperationString operationString;

    public RegexEnumerator(RegexAlphabet alphabet, RegexOperationString operationString) {
        this.alphabet = alphabet;
        this.operationString = operationString;
    }

    public List<String> enumerate(int limit) {
        Set<String> regexes = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        for (char c : alphabet.getAlphabet()) {
            queue.add(String.valueOf(c));
        }

        while (!queue.isEmpty() && regexes.size() < limit) {
            String current = queue.poll();
            if (regexes.add(current)) {
                for (String regex : new ArrayList<>(regexes)) {
                    for (char c : alphabet.getAlphabet()) {
                        String newRegex = operationString.concat(regex, String.valueOf(c));
                        if (!regexes.contains(newRegex)) {
                            queue.add(newRegex);
                        }

                        newRegex = operationString.alternation(regex, String.valueOf(c));
                        if (!regexes.contains(newRegex)) {
                            queue.add(newRegex);
                        }

                        newRegex = operationString.kleene(regex);
                        if (!regexes.contains(newRegex)) {
                            queue.add(newRegex);
                        }
                    }
                }
            }
        }

        return new ArrayList<>(regexes);
    }

}