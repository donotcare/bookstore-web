package ru.otus.bookstoreweb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.bookstoreweb.book.Book;
import ru.otus.bookstoreweb.book.BookService;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookstoreWebApplicationTests {
    @Autowired
    private BookService bookService;

    @BeforeEach
    public void init() {
        bookService.create(Book.of("Effective Java"));
        bookService.create(Book.of("Spring in Action"));
    }

    @Test
    @WithMockUser(username = "user", authorities = "ROLE_USER")
    public void testRoleUser() {
        Collection<Book> allBooks = bookService.findAll();
        assertThat(allBooks).extracting("name").containsOnly("Effective Java");
        Collection<Book> springBooks = bookService.findBookByName("Spring in Action");
        assertThat(springBooks).isEmpty();
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ROLE_ADMIN")
    public void testRoleAdmin() {
        Collection<Book> allBooks = bookService.findAll();
        assertThat(allBooks).extracting("name").contains("Effective Java", "Spring in Action");
        Collection<Book> springBooks = bookService.findBookByName("Spring in Action");
        assertThat(springBooks).isNotEmpty();
    }
}
