package ru.otus.bookstoreweb.book.integration;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.otus.bookstoreweb.book.Book;

@Component
public class BookToNameTransformer implements Converter<Book, String> {
    @Override
    public String convert(Book book) {
        return book.getName();
    }
}
