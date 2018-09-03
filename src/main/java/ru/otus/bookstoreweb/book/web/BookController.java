package ru.otus.bookstoreweb.book.web;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.bookstoreweb.book.Book;
import ru.otus.bookstoreweb.book.BookService;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("book")
    public Mono<Book> create(@RequestBody Book book) {
        return bookService.create(book);
    }

    @GetMapping("book/{id}")
    public Mono<Book> getById(@PathVariable String id) {
        return bookService.getById(id);
    }

    @PutMapping("book/{id}")
    public Mono<Book> update(@PathVariable String id, @RequestBody Book book) {
        if(!id.equals(book.getId())){
            throw new IllegalArgumentException("Wrong book id");
        }
        return bookService.update(book);
    }

    @DeleteMapping("book/{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return bookService.delete(id);
    }

    @GetMapping("book")
    public Flux<Book> list() {
        return bookService.findAll();
    }
}
