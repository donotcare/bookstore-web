package ru.otus.bookstoreweb;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
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
        Mono<Book> firstBook = repository.save(Book.of("Effective Java"));
        Mono<Book> secondBook = repository.save(Book.of("Spring in Action"));
        Flux.zip(firstBook, secondBook).then().block();
    }
}
