package ru.otus.bookstoreweb.book.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;
import ru.otus.bookstoreweb.book.BookRepository;

import java.util.Map;

@Configuration
@IntegrationComponentScan
@EnableIntegration
public class IntegrationConfig {
    @Bean
    public MessageChannel inputChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public PublishSubscribeChannel outputChannel() {
        return MessageChannels.publishSubscribe().datatype(Map.class).get();
    }

    @Bean
    public IntegrationFlow personIntegrationFlow(BookRepository bookRepository, BookToNameTransformer bookToNameTransformer,
                                                 BookNameSplitter bookNameSplitter, CharsCountAggregator charsAggregator) {
        return IntegrationFlows.from("inputChannel")
                .handle(bookRepository, "findAll")
                .log()
                .split()
                .transform(Transformers.converter(bookToNameTransformer))
                .aggregate()
                .split(bookNameSplitter, "split")
                .log()
                .aggregate(s -> s.processor(charsAggregator, "aggregate"))
                .channel("outputChannel")
                .get();
    }
}
