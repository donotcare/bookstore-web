package ru.otus.bookstoreweb.book.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MongoBook {
    @Id
    private String id;
    private String name;

    public MongoBook(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
