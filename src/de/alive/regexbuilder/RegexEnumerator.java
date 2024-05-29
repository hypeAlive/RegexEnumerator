package de.alive.regexbuilder;

import de.alive.regexbuilder.utils.RegexAlphabet;
import de.alive.regexbuilder.utils.RegexOperationString;

import java.util.*;

public class RegexEnumerator {

    public enum SortOrder {
        NO_SORT,
        SMALL_FIRST,
        BIG_FIRST
    }

    private final RegexAlphabet alphabet;
    private final RegexOperationString operationString;

    public RegexEnumerator(RegexAlphabet alphabet, RegexOperationString operationString) {
        this.alphabet = alphabet;
        this.operationString = operationString;
    }

    public List<String> enumerate(int limit, SortOrder sortOrder) {
        Set<String> regexes;
        if (sortOrder == SortOrder.NO_SORT) {
            regexes = new HashSet<>();
        } else {
            regexes = new TreeSet<>((a, b) -> {
                int cmp = Integer.compare(a.length(), b.length());
                if (cmp != 0) {
                    return sortOrder == SortOrder.SMALL_FIRST ? cmp : -cmp;
                } else {
                    return a.compareTo(b);
                }
            });
        }

        Queue<String> queue = new LinkedList<>();

        for (char c : alphabet.getAlphabet()) {
            queue.add(String.valueOf(c));
        }

        while (!queue.isEmpty() && regexes.size() < limit) {
            String current = queue.poll();
            if (regexes.add(current)) {
                for (char c : alphabet.getAlphabet()) {
                    for (String regex : new ArrayList<>(regexes)) {
                        // Konkatenation
                        String newRegex = operationString.concat(current, String.valueOf(c));
                        if (!regexes.contains(newRegex)) {
                            queue.add(newRegex);
                        }

                        // Alternative
                        newRegex = operationString.alternation(current, String.valueOf(c));
                        if (!regexes.contains(newRegex)) {
                            queue.add(newRegex);
                        }

                        // Kleene-Stern
                        newRegex = operationString.kleene(current);
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