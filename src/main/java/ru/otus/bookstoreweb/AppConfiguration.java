package ru.otus.bookstoreweb;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfiguration {

    @Bean
    public MongoTemplate mongoTemplate(@Value("${mongo-db-url}") String mongoDbUrl, @Value("${mongo-db-name}") String mongodbName) throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(mongoDbUrl);
        MongoClient mongoClient = mongo.getObject();
        return new MongoTemplate(mongoClient, mongodbName);
    }
}
