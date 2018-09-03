package ru.otus.bookstoreweb.book;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Book> getById(long id) {
        return repository.findById(id);
    }

    @Override
    public void update(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        repository.save(book);
    }

    @Override
    public Mono<Book> create(Book book) {
        if (book.getId() != null) {
            throw new IllegalArgumentException("Id must be null");
        }
        return repository.save(book);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public Flux<Book> findAll() {
        return repository.findAll();
    }
}
