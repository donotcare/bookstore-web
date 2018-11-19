package ru.otus.bookstoreweb.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookSearchService {
    private final RestTemplate template;

    public BookSearchService() {
        template = new RestTemplate();
    }

    @HystrixCommand(groupKey = "BookSearchService",
            commandKey = "getBooksByQuery",
            fallbackMethod = "getBookSearchFallback")
    public BookSearchResult getBooksByQuery(String query) {
        if (query.startsWith("error")) {
            throw new RuntimeException("Error query");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("User-agent", "Mozilla/5.0");
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<BookSearchResult> responseObj = template.exchange("https://api.itbook.store/1.0/search/" + query,
                HttpMethod.GET, entity, BookSearchResult.class);
        return responseObj.getBody();
    }

    public BookSearchResult getBookSearchFallback(String query) {
        return new BookSearchResult(null, "Book search service unavailable");
    }
}
