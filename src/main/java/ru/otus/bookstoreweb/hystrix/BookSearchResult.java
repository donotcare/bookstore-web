package ru.otus.bookstoreweb.hystrix;

import java.util.List;

public class BookSearchResult {
    private List<FoundBook> books;
    private String error;

    public BookSearchResult() {
    }

    public BookSearchResult(List<FoundBook> books, String error) {
        this.books = books;
        this.error = error;
    }

    public List<FoundBook> getBooks() {
        return books;
    }

    public String getError() {
        return error;
    }
}
