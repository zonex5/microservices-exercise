package xyz.toway.sales.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "library-service")
public interface LibraryServiceProxy {

    @GetMapping("/stock/check")
    boolean checkBeforeSale(@RequestParam("libraryId") Long libraryId, @RequestParam("bookId") Long bookId, @RequestParam("quantity") Integer quantity);
}
