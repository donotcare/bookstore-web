package ru.otus.bookstoreweb.book;

import java.util.Collection;

public interface BookService {
    Book getById(long id);

    void update(Book book);

    Book create(Book book);

    void delete(long id);

    Collection<Book> findAll();
}
