package ru.otus.bookstoreweb.book.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

import java.util.Map;

@MessagingGateway
public interface BookIntegration {
    @Gateway(requestChannel = "inputChannel", replyChannel = "outputChannel")
    Map<Character, Long> getCharsCount(Message o);
}
