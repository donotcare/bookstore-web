package ru.otus.bookstoreweb.book;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;
    private final Counter counter;

    public BookServiceImpl(BookRepository repository, MeterRegistry registry) {
        this.repository = repository;
        this.counter = registry.counter("get.requests");
    }

    @Override
    public Book getById(long id) {
        counter.increment();
        return repository.getOne(id);
    }

    @Override
    public void update(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        repository.save(book);
    }

    @Override
    public Book create(Book book) {
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
    public Collection<Book> findAll() {
        return repository.findAll();
    }
}
