package ru.otus.bookstoreweb.book;

import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book getById(long id) {
        return repository.findById(id).orElse(null);
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
