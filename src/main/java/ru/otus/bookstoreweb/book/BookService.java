package ru.otus.bookstoreweb.book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface BookService {
    Mono<Book> getById(long id);

    void update(Book book);

    Mono<Book> create(Book book);

    void delete(long id);

    Flux<Book> findAll();
}
