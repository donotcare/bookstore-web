package ru.otus.bookstoreweb.book.integration;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookNameSplitter {
    public List<Character> split(String name) {
        return name.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
    }
}
