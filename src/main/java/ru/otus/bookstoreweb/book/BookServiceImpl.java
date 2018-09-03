package ru.otus.bookstoreweb.book;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Book> getById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Book> update(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        return repository.save(book);
    }

    @Override
    public Mono<Book> create(Book book) {
        if (book.getId() != null) {
            throw new IllegalArgumentException("Id must be null");
        }
        return repository.save(book);
    }

    @Override
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Flux<Book> findAll() {
        return repository.findAll();
    }
}
