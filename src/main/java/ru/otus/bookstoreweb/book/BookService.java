package ru.otus.bookstoreweb.book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Mono<Book> getById(String id);

    Mono<Book> update(Book book);

    Mono<Book> create(Book book);

    Mono<Void> delete(String id);

    Flux<Book> findAll();
}
