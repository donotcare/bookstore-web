package ru.otus.bookstoreweb.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;

import java.util.Collection;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    List<Book> findAll();

    @PostFilter("hasPermission(filterObject, 'READ')")
    Collection<Book> findBookByName(String name);
}

