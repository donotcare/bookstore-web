package ru.otus.bookstoreweb.book.actuator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import ru.otus.bookstoreweb.book.BookRepository;

@Component
public class BookRepositoryHealthCheck extends AbstractHealthIndicator {
    private static final Logger logger = LoggerFactory.getLogger(BookRepositoryHealthCheck.class);
    private BookRepository bookRepository;

    public BookRepositoryHealthCheck(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        try {
            checkRepository();
            builder.up().withDetail("app", "Book repository is ok");
        } catch (Exception e) {
            logger.error("Error", e);
            builder.down(e).build();
        }
    }

    private void checkRepository() {
        if(bookRepository.findAll().isEmpty()) {
            throw new IllegalStateException("Repository is empty");
        }
    }
}
