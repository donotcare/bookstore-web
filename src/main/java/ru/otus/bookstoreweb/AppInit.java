package ru.otus.bookstoreweb;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.otus.bookstoreweb.book.Book;
import ru.otus.bookstoreweb.book.BookRepository;

@Component
public class AppInit {
    private final BookRepository repository;

    public AppInit(BookRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void init(ApplicationReadyEvent event) {
        repository.save(Book.of("Effective Java"));
        repository.save(Book.of("Spring in Action"));
    }
}
