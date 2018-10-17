package ru.otus.bookstoreweb.book.integration;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CharsCountAggregator {
    public Map<Character, Long> aggregate(Collection<Character> chars) {
        return chars.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}
