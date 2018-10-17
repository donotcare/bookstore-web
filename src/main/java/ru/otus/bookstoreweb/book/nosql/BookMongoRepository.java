package ru.otus.bookstoreweb.book.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BookMongoRepository extends MongoRepository<MongoBook, String> {
}
