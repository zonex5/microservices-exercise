package xyz.toway.libraryservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.toway.libraryservice.proxy.BookServiceProxy;

@Log4j2
@Service
public class ProxyService {

    private final BookServiceProxy bookServiceProxy;

    public ProxyService(@Autowired BookServiceProxy bookServiceProxy) {
        this.bookServiceProxy = bookServiceProxy;
    }

    @CircuitBreaker(name = "book-service", fallbackMethod = "bookFallbackMethod")
    public boolean checkBookExists(Long id) {
        return bookServiceProxy.checkBookExists(id);
    }

    @SuppressWarnings("unused")
    public boolean bookFallbackMethod(Long id, Throwable t) {
        log.error(t.getMessage());
        return false;
    }
}
