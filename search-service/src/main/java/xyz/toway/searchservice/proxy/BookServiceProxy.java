package xyz.toway.searchservice.proxy;

import xyz.toway.shared.model.SharedBookModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "book-service")
public interface BookServiceProxy {

    @GetMapping("/books/search")
    List<SharedBookModel> searchBooks(@RequestParam("q") String value, @RequestParam("type") String type);

    @GetMapping("/books/search-ids")
    List<Long> searchBookIds(@RequestParam("q") String value, @RequestParam("type") String type);
}
