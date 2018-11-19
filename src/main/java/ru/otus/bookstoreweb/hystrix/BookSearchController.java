package ru.otus.bookstoreweb.hystrix;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookSearchController {
    private final BookSearchService bookSearchService;

    public BookSearchController(BookSearchService bookSearchService) {
        this.bookSearchService = bookSearchService;
    }

    @GetMapping("book/search/{query}")
    public BookSearchResult getBookSearchResult(@PathVariable String query) {
        return bookSearchService.getBooksByQuery(query);
    }
}
