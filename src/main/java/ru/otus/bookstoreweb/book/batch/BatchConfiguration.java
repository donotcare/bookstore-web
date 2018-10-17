package ru.otus.bookstoreweb.book.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import ru.otus.bookstoreweb.book.Book;
import ru.otus.bookstoreweb.book.BookRepository;
import ru.otus.bookstoreweb.book.nosql.BookMongoRepository;
import ru.otus.bookstoreweb.book.nosql.MongoBook;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job bookTransformerJob(Step bookTransformer) {
        return jobBuilderFactory.get("bookTransformJob")
                .incrementer(new RunIdIncrementer())
                .start(bookTransformer)
                .build();
    }

    @Bean
    public BookItemProcessor bookItemProcessor() {
        return new BookItemProcessor();
    }

    @Bean
    public RepositoryItemReader<Book> bookItemReader(BookRepository bookRepository) {
        Map<String, Sort.Direction> sortDirections = new HashMap<>();
        sortDirections.put("name", Sort.Direction.ASC);
        return new RepositoryItemReaderBuilder<Book>()
                .name("bookReader")
                .repository(bookRepository)
                .pageSize(5)
                .sorts(sortDirections)
                .methodName("findAll").build();
    }

    @Bean
    public RepositoryItemWriter<MongoBook> bookItemWriter(BookMongoRepository bookMongoRepository) {
        return new RepositoryItemWriterBuilder<MongoBook>()
                .repository(bookMongoRepository)
                .methodName("insert")
                .build();
    }

    @Bean
    public Step fromJpaToMongoBookTransformer(RepositoryItemReader<Book> bookItemReader, BookItemProcessor processor, RepositoryItemWriter<MongoBook> itemWriter) {
        return stepBuilderFactory.get("bookTransformer")
                .<Book, MongoBook>chunk(3)
                .reader(bookItemReader)
                .processor(processor)
                .writer(itemWriter)
                .build();
    }
}
