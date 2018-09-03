package ru.otus.bookstoreweb.book.web;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.bookstoreweb.book.Book;
import ru.otus.bookstoreweb.book.BookService;

import java.util.Collection;

@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("book")
    public void create(@RequestBody Book book) {
        bookService.create(book);
    }

    @GetMapping("book/{id}")
    public Mono<Book> getById(@PathVariable long id) {
        return bookService.getById(id);
    }

    @PutMapping("book/{id}")
    public void update(@PathVariable String id, @RequestBody Book book) {
        if(!id.equals(book.getId())){
            throw new IllegalArgumentException("Wrong book id");
        }
        bookService.update(book);
    }

    @DeleteMapping("book/{id}")
    public void delete(@PathVariable long id) {
        bookService.delete(id);
    }

    @GetMapping("book")
    public Flux<Book> list() {
        return bookService.findAll();
    }
}
