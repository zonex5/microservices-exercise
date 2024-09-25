package xyz.toway.sales.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.toway.sales.proxy.LibraryServiceProxy;
import xyz.toway.sales.repository.SaleRepository;
import xyz.toway.shared.model.SharedBookModel;
import xyz.toway.shared.model.SharedLibraryModel;
import xyz.toway.shared.model.SharedLibraryStockModel;

import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class ProxyService {

    private final LibraryServiceProxy libraryServiceProxy;

    public ProxyService(@Autowired LibraryServiceProxy libraryServiceProxy) {
        this.libraryServiceProxy = libraryServiceProxy;
    }

    @Retry(name = "remote-service")
    @CircuitBreaker(name = "library-service", fallbackMethod = "checkStockFallbackMethod")
    public boolean checkStockBeforeSale(Long libraryId, Long bookId, Integer quantity) {
        return libraryServiceProxy.checkStockBeforeSale(libraryId, bookId, quantity);
    }

    @SuppressWarnings("unused")
    public boolean checkStockFallbackMethod(Long libraryId, Long bookId, Integer quantity, Throwable t) {
        log.error(t.getMessage());
        return false;
    }
}
