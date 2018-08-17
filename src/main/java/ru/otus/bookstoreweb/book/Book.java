package ru.otus.bookstoreweb.book;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public static Book of(String name) {
        return new Book(name);
    }

}
