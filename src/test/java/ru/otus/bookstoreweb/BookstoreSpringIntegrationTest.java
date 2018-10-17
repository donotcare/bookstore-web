package ru.otus.bookstoreweb;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.bookstoreweb.book.Book;
import ru.otus.bookstoreweb.book.BookRepository;
import ru.otus.bookstoreweb.book.integration.BookIntegration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookstoreSpringIntegrationTest {
    private final BookRepository repository;
    private final BookIntegration bookIntegration;

    @Autowired
    public BookstoreSpringIntegrationTest(BookRepository repository, BookIntegration bookIntegration) {
        this.repository = repository;
        this.bookIntegration = bookIntegration;
    }

    @Test
    public void testIntegration() {
        repository.save(Book.of("Effective Java"));
        repository.save(Book.of("Spring in Action"));

        Message<String> message = MessageBuilder.withPayload("").build();
        assertThat(bookIntegration.getCharsCount(message).entrySet())
                .extracting("key", "value").contains(tuple('A', 1L), tuple('i', 4L), tuple('f', 2L));
    }

}
