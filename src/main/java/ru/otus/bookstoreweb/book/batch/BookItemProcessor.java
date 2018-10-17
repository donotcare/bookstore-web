package ru.otus.bookstoreweb.book.batch;

import org.springframework.batch.item.ItemProcessor;
import ru.otus.bookstoreweb.book.Book;
import ru.otus.bookstoreweb.book.nosql.MongoBook;

public class BookItemProcessor implements ItemProcessor<Book, MongoBook> {
    @Override
    public MongoBook process(Book book) {
        return new MongoBook(String.valueOf(book.getId()), book.getName());
    }
}
