package xyz.toway.bookservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "library-service")
public interface LibraryServiceProxy {

    @GetMapping("/stock/check-book-can-delete/{id}")
    boolean checkBookCanDelete(@PathVariable Long id);
}
