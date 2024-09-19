package xyz.toway.libraryservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface BookServiceProxy {

    @GetMapping("/books/exists/{id}")
    boolean checkBookExists(@PathVariable Long id);
}
