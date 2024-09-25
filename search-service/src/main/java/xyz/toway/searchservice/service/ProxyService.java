package xyz.toway.searchservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import xyz.toway.searchservice.proxy.BookServiceProxy;
import xyz.toway.searchservice.proxy.LibraryServiceProxy;
import xyz.toway.shared.exception.RateLimitExceededException;
import xyz.toway.shared.model.SharedBookModel;
import xyz.toway.shared.model.SharedLibraryModel;
import xyz.toway.shared.model.SharedLibraryStockModel;

import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class ProxyService {

    private final static String REQUEST_LIMIT = "Request limit exceeded. Please try again later.";

    private final BookServiceProxy bookServiceProxy;
    private final LibraryServiceProxy libraryServiceProxy;

    public ProxyService(BookServiceProxy bookServiceProxy, LibraryServiceProxy libraryServiceProxy) {
        this.bookServiceProxy = bookServiceProxy;
        this.libraryServiceProxy = libraryServiceProxy;
    }

    @RateLimiter(name = "remote-service", fallbackMethod = "rateLimiterBooksFallback")
    @Retry(name = "remote-service")
    @CircuitBreaker(name = "book-service", fallbackMethod = "bookFallbackMethod")
    public List<SharedBookModel> getBooksFromRemoteService(String q, String by) {
        return bookServiceProxy.searchBooks(q, by);
    }

    @SuppressWarnings("unused")
    public List<SharedBookModel> rateLimiterBooksFallback(String q, String by, Throwable throwable) {
        throw new RateLimitExceededException(REQUEST_LIMIT);
    }

    @SuppressWarnings("unused")
    public List<SharedBookModel> bookFallbackMethod(String q, String by, Throwable t) {
        log.error(t.getMessage());
        return Collections.emptyList();
    }

    @RateLimiter(name = "remote-service", fallbackMethod = "rateLimiterStockFallback")
    @Retry(name = "remote-service")
    @CircuitBreaker(name = "library-service", fallbackMethod = "stockFallbackMethod")
    public List<SharedLibraryStockModel> getStockFromRemoteService(List<Long> ids) {
        return libraryServiceProxy.searchStockByBookIds(ids);
    }

    @SuppressWarnings("unused")
    public List<SharedLibraryStockModel> stockFallbackMethod(List<Integer> ids, Throwable t) {
        log.error(t.getMessage());
        return Collections.emptyList();
    }

    @SuppressWarnings("unused")
    public List<SharedLibraryStockModel> rateLimiterStockFallback(List<Integer> ids, Throwable throwable) {
        throw new RateLimitExceededException(REQUEST_LIMIT);
    }

    @RateLimiter(name = "remote-service", fallbackMethod = "rateLimiterIdsFallback")
    @Retry(name = "remote-service")
    @CircuitBreaker(name = "book-service", fallbackMethod = "bookIdsFallbackMethod")
    public List<Long> getBookIdsFromRemoteService(String q, String by) {
        return bookServiceProxy.searchBookIds(q, by);
    }

    @SuppressWarnings("unused")
    public List<Long> bookIdsFallbackMethod(String q, String by, Throwable t) {
        log.error(t.getMessage());
        return Collections.emptyList();
    }

    @SuppressWarnings("unused")
    public List<Long> rateLimiterIdsFallback(String q, String by, Throwable throwable) {
        throw new RateLimitExceededException("Request limit exceeded. Please try again later.");
    }

    @RateLimiter(name = "remote-service", fallbackMethod = "rateLimiterLibsFallback")
    @Retry(name = "remote-service")
    @CircuitBreaker(name = "library-service", fallbackMethod = "librariesByBookIdsFallbackMethod")
    public List<SharedLibraryModel> getLibrariesByBookIdsFromRemoteService(List<Long> ids) {
        return libraryServiceProxy.searchByBookIds(ids);
    }

    @SuppressWarnings("unused")
    public List<SharedLibraryModel> librariesByBookIdsFallbackMethod(List<Long> ids, Throwable t) {
        log.error(t.getMessage());
        return Collections.emptyList();
    }

    @SuppressWarnings("unused")
    public List<SharedLibraryModel> rateLimiterLibsFallback(List<Long> ids, Throwable throwable) {
        throw new RateLimitExceededException(REQUEST_LIMIT);
    }
}
