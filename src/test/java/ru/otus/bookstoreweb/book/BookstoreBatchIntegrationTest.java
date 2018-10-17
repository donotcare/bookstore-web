package ru.otus.bookstoreweb.book;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.bookstoreweb.book.nosql.BookMongoRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookstoreBatchIntegrationTest {
    private BookRepository repository;
    private Job bookTransformationJob;
    private JobLauncher jobLauncher;
    private BookMongoRepository mongoRepository;

    @Autowired
    public BookstoreBatchIntegrationTest(BookRepository repository, Job bookTransformationJob, JobLauncher jobLauncher, BookMongoRepository mongoRepository) {
        this.repository = repository;
        this.bookTransformationJob = bookTransformationJob;
        this.jobLauncher = jobLauncher;
        this.mongoRepository = mongoRepository;
    }

    @Test
    public void contextLoads() throws Exception {
        repository.save(Book.of("Effective Java"));
        repository.save(Book.of("Spring in Action"));
        repository.save(Book.of("Spring in Action 2"));
        repository.save(Book.of("Spring in Action 3"));
        repository.save(Book.of("Spring in Action 4"));
        repository.save(Book.of("Spring in Action 5"));

        jobLauncher.run(bookTransformationJob, new JobParametersBuilder().toJobParameters());

        assertThat(mongoRepository.findAll().size()).isEqualTo(6);
        assertThat(mongoRepository.findAll()).extracting("name").contains("Effective Java", "Spring in Action 2", "Spring in Action 4", "Spring in Action 5");
    }

}
