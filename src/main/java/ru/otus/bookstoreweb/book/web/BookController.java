package ru.otus.bookstoreweb.book.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.bookstoreweb.book.Book;
import ru.otus.bookstoreweb.book.BookService;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("book")
    public String create(Model model, @RequestParam("name") String name) {
        bookService.create(Book.of(name));
        model.addAttribute("list", bookService.findAll());
        return "books";
    }

    @GetMapping("book/{id}")
    public String getById(Model model, @PathVariable long id) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        return "bookEdit";
    }

    @PutMapping("book/{id}")
    public String update(Model model, @PathVariable long id, @ModelAttribute("book") Book book) {
        bookService.update(book);
        model.addAttribute("list", bookService.findAll());
        return "books";
    }

    @DeleteMapping("book/{id}")
    public String delete(Model model, @PathVariable(name = "id") long id) {
        bookService.delete(id);
        model.addAttribute("list", bookService.findAll());
        return "books";
    }

    @GetMapping("book")
    public String list(Model model) {
        model.addAttribute("list", bookService.findAll());
        return "books";
    }
}
